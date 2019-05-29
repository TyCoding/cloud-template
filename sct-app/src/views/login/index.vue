<template>
  <div class="login-container">
    <div class="login-weaper  animated bounceInDown">
      <el-card class="login-left">
        <div class="login-time">
          {{date}}
        </div>
        <div style="text-align: center">
          <h1>Spring Cloud Template</h1>
          <h3>一套极简的Spring Cloud分布式微服务项目模板</h3>
          <h3>By <a href="https://github.com/TyCoding/cloud-template">TyCoding</a></h3>
        </div>
      </el-card>
      <el-card class="login-right">
        <el-form ref="loginForm" :model="loginForm" :rules="loginRules" status-icon class="login-form"
                 auto-complete="on" label-position="left">

          <div class="title-container">
            <h3 class="title">Login SCT</h3>
          </div>

          <el-form-item prop="username">
             <span class="svg-container">
              <svg-icon icon-class="user"/>
            </span>
            <el-input prefix-icon="el-icon-user" ref="username" v-model="loginForm.username"
                      placeholder="Username" name="username" type="text" tabindex="1" auto-complete="on"></el-input>
          </el-form-item>

          <el-form-item prop="password">
            <span class="svg-container">
              <svg-icon icon-class="password"/>
            </span>
            <el-input :key="passwordType" ref="password" v-model="loginForm.password"
                      :type="passwordType" placeholder="Password" name="password" tabindex="2" auto-complete="on"
                      @keyup.enter.native="handleLogin"></el-input>
            <span class="show-pwd" @click="showPwd">
              <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'"/>
            </span>
          </el-form-item>

          <el-button :loading="loading" type="primary" style="width:100%;margin-bottom:30px;"
                     @click.native.prevent="handleLogin">Login
          </el-button>
        </el-form>
      </el-card>
    </div>
  </div>
</template>

<script>
  import {parseTime} from '@/utils/index'

  export default {
    name: 'Login',
    data() {
      return {
        date: parseTime(new Date().getTime(), ''),
        loginForm: {
          username: 'tycoding',
          password: 'tycoding'
        },
        loginRules: {
          username: [{required: true, trigger: 'blur'}],
          password: [{required: true, trigger: 'blur'}]
        },
        loading: false,
        passwordType: 'password',
        redirect: undefined
      }
    },
    watch: {
      $route: {
        handler: function (route) {
          this.redirect = route.query && route.query.redirect
        },
        immediate: true
      }
    },
    beforeDestroy() {
      if (this.timer) {
        clearInterval(this.timer);
      }
    },
    mounted() {
      let _this = this;
      this.timer = setInterval(() => {
        _this.date = parseTime(new Date().getTime(), '');
      }, 1000)
    },
    methods: {
      showPwd() {
        if (this.passwordType === 'password') {
          this.passwordType = ''
        } else {
          this.passwordType = 'password'
        }
        this.$nextTick(() => {
          this.$refs.password.focus()
        })
      },
      handleLogin() {
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            this.loading = true
            this.$store.dispatch('user/login', this.loginForm).then(() => {
              this.$router.push({path: this.redirect || '/'})
              this.loading = false
            }).catch(() => {
              this.loading = false
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
    }
  }
</script>
<style lang="scss">
  .el-input__inner {
    padding-left: 35px !important;
  }
</style>
<style lang="scss" scoped>
  $bg: #2d3a4b;
  $dark_gray: #889aa4;
  $light_gray: #eee;
  @-webkit-keyframes animate-cloud {
    0% {
      background-position: 600px 100%
    }
    to {
      background-position: 0 100%
    }
  }

  .el-card {
    border: none !important;
  }

  .login-container {
    display: -webkit-box;
    display: -ms-flexbox;
    display: flex;
    -webkit-box-align: center;
    -ms-flex-align: center;
    align-items: center;
    position: relative;
    width: 100%;
    height: 100%;
    margin: 0 auto;
    background: url(../../assets/bg_cloud.jpg) 0 bottom repeat-x #049ec4;
    -webkit-animation: animate-cloud 20s linear infinite;
    animation: animate-cloud 20s linear infinite;

    .login-weaper {
      margin: 0 auto;
      width: 1000px;
      -webkit-box-shadow: -4px 5px 10px rgba(0, 0, 0, .4);
      box-shadow: -4px 5px 10px rgba(0, 0, 0, .4);
    }

    .bounceInDown {
      -webkit-animation-name: bounceInDown;
      animation-name: bounceInDown;
    }

    .animated {
      -webkit-animation-duration: 1s;
      animation-duration: 1s;
      -webkit-animation-fill-mode: both;
      animation-fill-mode: both;
    }

    .login-time {
      position: absolute;
      left: 25px;
      top: 25px;
      width: 100%;
      color: #fff;
      font-weight: 400;
      opacity: .9;
      font-size: 20px;
      overflow: hidden;
    }

    .login-left {
      border-top-left-radius: 5px;
      border-bottom-left-radius: 5px;
      -webkit-box-pack: center;
      -ms-flex-pack: center;
      justify-content: center;
      -webkit-box-orient: vertical;
      -webkit-box-direction: normal;
      -ms-flex-direction: column;
      flex-direction: column;
      background-color: #409eff;
      color: #fff;
      float: left;
      width: 50%;
      position: relative;
      min-height: 500px;
      -webkit-box-align: center;
      -ms-flex-align: center;
      align-items: center;
      display: -webkit-box;
      display: -ms-flexbox;
      display: flex;
    }

    .login-right {
      -webkit-box-sizing: border-box;
      box-sizing: border-box;
      border-left: none;
      border-top-right-radius: 5px;
      border-bottom-right-radius: 5px;
      color: #fff;
      background-color: #fff;
      width: 50%;
      float: left;
      min-height: 500px;
    }

    .login-form {
      position: relative;
      width: 520px;
      max-width: 100%;
      margin: 0 auto;
      overflow: hidden;
      padding: 0 56px;
    }

    .svg-container {
      position: absolute;
      top: -5px;
      font-size: 14px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
      padding: 6px 5px 6px 15px;
      vertical-align: middle;
      width: 30px;
      display: inline-block;
    }

    .el-input {
      position: initial !important;
    }

    .title-container {
      position: relative;

      .title {
        font-size: 26px;
        color: #606266;
        margin: 50px auto 40px auto;
        text-align: center;
        font-weight: bold;
      }
    }

    .el-icon-user::before {
      content: "\e6e3";
    }

    .show-pwd {
      position: absolute;
      right: 33px;
      top: 0px;
      font-size: 16px;
      color: $dark_gray;
      cursor: pointer;
      user-select: none;
    }
  }
</style>
