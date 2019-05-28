# Spring Cloud 分布式微服务系统 -- 前端

**注意**

请仔细阅读`vue-admin-template`项目官方文档：[传送门](https://panjiachen.gitee.io/vue-element-admin-site/zh/guide/)

如何食用 `vue-admin-template` 前端模板？

## 环境准备

> 因为该模板用mock模拟了前端所需数据，首先要删除mock相关的配置

- 删除`mock`文件夹
- 删除`/src/main.js`下这段代码：

```javascript
import { mockXHR } from '../mock'
if (process.env.NODE_ENV === 'production') {
  mockXHR()
}
```

- 删除`vue-config.js`下这段代码：

```javascript
after: require('./mock/mock-server.js') 
```

全局配置后端接口URL，以下配置文件中都存在一个`VUE_APP_BASE_API`配置，他指定了后端请求的URL根路径。比如我们后端的请求都是`http://localhost:9999/api/xx`的，所以可设置`VUE_APP_BASE_API='http://localhost:9999/api/'`，其中`/xx`具体的接口请求放在`/src/api/*.js`中。

  * `.env.development`: 即`dev`开发环境
  * `.env.production`: 即`prod`生产环境
  * `.env.staging`: 即`mock`模拟环境

注意：在SpringCloud微服务项目中，前端的所有请求都应该走Gateway网关服务的URL地址。

### 取消ESLint校验

在你开发项目中可能遇到前端莫名其妙报错语法不对，比如多一个空格、冒号啥的，这都是因为`vue-admin-template`这个模板启用了ESLint最严格模式，其实我们关闭ESLint检查即可：

修改`vue.config.js`中如下代码：

```
修改前：
  lintOnSave: process.env.NODE_ENV === 'development',
  
修改后：
  lintOnSave: false,
```

## 登录功能

拿到的项目模板，首先需要解决的就是登录功能。按照 [vue-admin-template](https://panjiachen.gitee.io/vue-element-admin-site/zh/guide/) 官方文档的描述，所有的请求都将经过如下流程：

	1. `.vue` 首先是Vue组件内部发送了请求
 	2. `src/utils/request.js` 作者对 axios 请求全局的封装
 	3. `src/api/xx.js` vue组件使用的接口地址，配合`request.js`完成axios请求与相应
 	4. `src/store/modules/user.js` 这个尤为重要，登录接口不同于其他接口，当登录成功后，需要使用vuex将登录接口响应的数据保存，以便维持与后端的会话通信。

那么，登录功能不同于其他的CRUD业务流程，在`vue-admin-template`中登录需要后台提供：

1.  登录接口
2.  获取用户信息接口

这刚好符合了我们使用的Security-OAuth2框架。在Security-OAuth2框架汇总，默认提供了获取Token的接口(登录接口)，我们仅需要调用这个接口即可实现登录。

### 登录接口

如果你对Security-OAuth2还不熟悉，建议看下我之前写的文档：

1.  [Spring Security OAuth2概念引入](https://www.tycoding.cn/2019/04/22/spring-boot-security-oauth2-start/)
2.  [Spring Security OAuth2实战](https://tycoding.cn/2019/04/22/spring-boot-security-oauth2/)
3.  [Spring Security OAuth2数据持久化](https://www.tycoding.cn/2019/04/23/spring-boot-security-oauth2-db/)

Security-OAuth2中提供的默认获取Token的接口：`/oauth/token`，下面是使用Postman工具模拟请求的示例图：

![](doc/2019052872704.png)

![](doc/2019052872808.png)

![](doc/2019052872828.png)

>   `/oauth/token`接口是谁提供的？

​		切记，`/oauth/token`接口是Security-OAuth2内部提供的获取Token的接口，这个接口不需要我们手动定义，并且即使使用了Spring Security，`/oauth`开头的接口也应为是内置的不会被拦截，所以我们也无需特殊配置Spring Security 不拦截这个接口。

>   `/oauth/token`登录请求需要传入什么参数？

​		关于这点可以看下我的 [博客](https://tycoding.cn/) 中之前介绍OAuth2的文章。我们需要手动提供：

1.  `username` 登录账户
2.  `password` 登录密码。这并不是必须的，但由于我们使用的OAuth的**密码**模式，所以需要定义
3.  `grant_type` 因为我们使用的OAuth2的密码模式，可直接定义为`grant_type=password`
4.  Request Headers >> `Authorization` 注意这个是客户端账户密码信息，对应了后端`ClientDetails`中查询的数据

>   `/oauth/token` 响应什么数据？

​		如上图，请求一般响应如下信息：

```json
{
    "access_token": "f59359c1-86c0-48a3-b060-ff97e5163bb2",
    "token_type": "bearer",
    "expires_in": 22531,
    "scope": "app"
}
```

其中`access_token` 尤为重要，后面所有的请求都需要携带这个Token值才能正常访问，否则就403拒绝。所以，在`vue-admin-template`项目中，一旦登录接口响应成功，会将返回的Token信息全局设置再请求头中，这样以后所有的请求中都携带这个请求都信息。具体可以看：`src/utils/request.js`中这段代码：

```javasc
config.headers['Authorization'] = getToken()
```

这是全局配置axios实例，因为所有的API请求都需要经过这个`request.js`文件，所以其中的配置项对所有的请求都有效。

>   `vue-admin-template`中如何处理登录接口响应的数据？

​		看完上面的配置，你觉得已经能完成前端的登录功能了？那你就错了。上面仅仅介绍了使用Postman工具模拟测试，而在`vue-admin-template`项目中，如果请求`/oauth/token`接口正常响应数据，需要将响应的数据储存到vuex中。那么主要涉及`src/store/modules/user.js`中的代码：

```javasc
const actions = {
  // user login
  login({ commit }, userInfo) {
    const { username, password } = userInfo
    return new Promise((resolve, reject) => {
      login({ username: username.trim(), password: password }).then(response => {
        commit('SET_TOKEN', response.access_token)
        setToken(response.token_type + ' ' + response.access_token)
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // get user info
  getInfo({ commit, state }) {
    return new Promise((resolve, reject) => {
      getInfo(state.token).then(response => {
        const { data } = response

        if (!data) {
          reject('Verification failed, please Login again.')
        }

        const { name, avatar } = data

        commit('SET_NAME', name)
        commit('SET_AVATAR', avatar)
        resolve(data)
      }).catch(error => {
        reject(error)
      })
    })
  },

  // user logout
  logout({ commit, state }) {
    return new Promise((resolve, reject) => {
      logout(state.token).then(() => {
        commit('SET_TOKEN', '')
        removeToken()
        resetRouter()
        resolve()
      }).catch(error => {
        reject(error)
      })
    })
  },

  // remove token
  resetToken({ commit }) {
    return new Promise(resolve => {
      commit('SET_TOKEN', '')
      removeToken()
      resolve()
    })
  }
}
```

这段代码才是登录、获取用户信息、注销功能实现的核心代码。其实就是利用`src/api/xx.js`中定义的接口方法发送axios请求。然后将响应response数据处理一下。

比如上面代码中，`login()`方法就是登录接口放他，它实际请求了`/oauth/token`接口，前面提到每次请求都需要携带`access_token`，所以需要vuex储存token信息(`setToken(token)`)，以便在`request.js`中使用`config.headers[]`全局定义请求头信息。

>   在Postman中设置的`Basic Auth`，在此项目中在哪体现呢？

​		当然，按照OAuth2协议的规定，想要获取应用信息必须先请求`/oauth/token`获取令牌Token值，而想要获取令牌Token除了`username` `password` `grant_type`信息，还要告诉OAuth2这是哪个客户端的请求，所以在请求`/oauth/token`接口时需要携带客户端信息。

​		这一点在 [Spring Security OAuth2实战](https://tycoding.cn/2019/04/22/spring-boot-security-oauth2/) 一文中我有详细介绍过。所以在`vue-admin-template`前端项目中，想要实现所有请求都携带客户端信息，就需要全局设置请求头参数，所以，我们直接在 `src/main.js` 中全局设置Axios 默认请求头参数：

```javascript
import axios from 'axios'
axios.defaults.headers.post['Authorization'] = 'Basic Y2xpZW50OnNlY3JldA==';
```

上面设置了一个请求头参数`Authorization`，他的值是对`username: client, password: 123456`即`client:123456`按照Base64加密后的值。因为整个项目仅仅是个人使用的，所以这个写死也并无大碍，毕竟数据库中写死了客户端信息。

### 测试

上面基本介绍了登录请求的流程和注意事项，下面使用浏览器F12看一下实际的请求信息：

![](doc/2019052880003.png)

![](doc/2019052880030.png)

如果登录请求响应成功，想要进入系统的第二关就是调用获取用户信息的接口，全局设置用户信息(用户名、头像…) 。所以，`vue-admin-template`会立即再请求获取用户信息的接口：

![](doc/2019052880107.png)

可看到，如果登录成功，可携带`access_token`访问应用的其他接口，只需要在请求时将请求头`Authorization`设置为`access_token`信息即可。

## CRUD业务

一旦解决了登录功能，相信你对`vue-admin-template`这个前端模板项目有一定理解了，后端其他的业务也相对简单很多了。涉及Axios请求部分，只需要关注：

1.  `src/views` 下定义 `.vue` 组件
2.  `src/api` 下定义API接口信息

### 栗子

举例：根据ID获取用户信息的功能

1.  在`src/api/user.js`中定义根据ID获取用户信息的API接口

```javasc
export function findById(id) {
  return request({
    url: '/admin/user/' + id,
    method: 'get'
  })
}
```

2.  在`.vue`组件中使用这个API接口

```vue
<el-table-column align="center" label="Actions">
	<template slot-scope="scope">
		<el-button type="danger" @click="handleDel(scope.row.id)" icon="el-icon-delete" size="mini">删除</el-button>
	</template>
</el-table-column>
<script>
  import { findById } from '@/api/user'

  export default {
    components: {Pagination, Save},
    data() {
      return {
        form: null,
      }
    },
    methods: {
      handleEdit(id) {
        findById(id).then(response => {
          this.form = response.data;
        })
      },
    }
  }
</script>
```

以上即可实现根据ID查询用户信息的功能，是不是很简单呢？

## 分页查询

`vue-admin-template`的作者其实提供好了一个`pagination`分页组件，是对Element-UI的`<el-pagination>`控件的封装。作者封装的这个组件是通用的，可以在项目的任何需要分页的位置使用，非常方便。如何食用呢？

1.  在`src/components`下引入该组件

![](doc/20190528211740.png)

2.  在`src/api/user.js`中定义分页查询接口

```javascript
export function getList(data) {
  return request({
    url: '/admin/user/list',
    method: 'post',
    data
  })
}
```

2.  在需要使用分页的组件中引入该分页组件

```vue
<pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit"
            @pagination="fetchData"></pagination>

<script>
  import {getList} from '@/api/user'
  import Pagination from '@/components/Pagination'

  export default {
    components: {Pagination},
    data() {
      return {
        list: null,
        search: {},
        listQuery: {
          page: 1,
          limit: 20,
          importance: undefined,
          title: undefined,
          type: undefined,
          sort: '+id'
        },
        total: 0,
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        getList(this.search).then(response => {
          this.list = response.data.rows
          this.total = response.data.total
        })
      },
    }
  }
</script>
```



## 组件传值

实际项目中，经常使用组件传值。比如：用户管理模块中，编辑功能通常需要一个弹出窗，而这个弹出窗通常是抽取在另外一个组件中，这样就涉及到了父组件 ( 用户管理组件 ) 和子组件 ( 编辑功能组件 ) 的通信；简单来说，在用户管理组件中需要控制编辑功能弹出框的弹出和关闭等操作。

### 概念引入

#### 父组件向子组件传值

比如在父组件中定义子组件：

```
<div id="app">
    <son :info="msg"></son>
</div>
```

那么在组件中获取到这个`info`中的数据：

```
data() {
    return {}
},
methods: {},
props: ['info']
```

即可获取到父组件传递来的数据，注意：这个`props`属于`new vue()`根路径下的属性，不属于`data`。如果获取父组件传进来的多个擦书，使用逗号隔开即可获取。

#### 子组件向父组件传值

在父组件中定义：

```
<son @func="getMsg"></son>

//Vue实例
methods: {
    //父组件注册的方法，子组件通过`this.$emit()`的方式调用这个方法将参数传递给父组件的val。
    getMsg(val) {
        console.log("这是子组件传递来的数据：" + val);
    }
}
```

那么在子组件中通过`this.$emit('方法名', 要传递的数据)`的方式调用父组件中的方法，传递数据。

```
<input type="button" value="向父组件传值" @click="sendMsg">

//Vue实例
methods: {
    sendMsg() {
        this.$emit('func', '我是来自子组件的数据');
    }
}
```

#### $refs

`this.$refs`可以获取元素和组件（以及组件中的元素）。

-   如果在HTML中定义了 `ref="xx"` 那么在Vue实例中通过`this.$refs.xx`就能获取到当前定义`ref="xx"`的DOM元素。
-   如果在组件引用上（比如`<son ref="xx">`）上使用了`ref`，那么在父组件Vue实例中通过`this.$refs`获取到的是整个子组件的对象，可以通过`.`的方式调用子组件`data`和`methods`中绑定数据。

### 栗子

1.  除了创建用户管理的组件，新增一个封装了用户信息编辑框的组件：

![](doc/20190528213839.png)

其中的`save.vue`就是封装了用户信息编辑框的组件。

2.  在用户管理组件中引入用户信息编辑框的组件

```vue
<save :sonData="form" @sonStatus="status"></save>
<script>
  import Save from './save'

  export default {
    components: {Save},
    data() {
      return {
        form: null,
      }
    },
    created() {
      this.fetchData()
    },
    methods: {
      fetchData() {
        getList(this.search).then(response => {
        })
      },
      handleEdit(id) {
        findById(id).then(response => {
          this.form = response.data;
        })
      },

      //子组件的状态Flag，子组件通过`this.$emit('sonStatus', val)`给父组件传值
      //父组件通过`@sonStatus`的方法`status`监听到子组件传递的值
      status(data) {
        if (data) {
          this.fetchData();
        }
      },
    }
  }
</script>
```

