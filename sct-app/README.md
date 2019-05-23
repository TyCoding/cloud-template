# Spring Cloud 分布式微服务系统 -- 前端

# 如何食用 `vue-admin-template` 前端模板？

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

### 

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

