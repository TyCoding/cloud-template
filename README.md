# SpringCloud分布式微服务项目

 <p align="center">
  <a href="https://github.com/TyCoding/cloud-template/" target="_blank">
    <img src="https://img.shields.io/badge/SpringCloudTemplate-分布式项目脚手架-green.svg" alt="Build Status">
  </a>
  <img src="https://img.shields.io/badge/Spring%20Boot-2.1.5.RELEASE-yellowgreen.svg" alt="Downloads">
  <img src="https://img.shields.io/badge/Spring%20Cloud-Greenwich.RELEASE-blue.svg" alt="Coverage Status">
 </p>

## 介绍

`cloud-template`: 一套极简的SpringCloud微服务项目模板，没有具体的业务，提供最详细的SpringCloud搭建流程

`sct-api`: 分布式微服务项目后端接口

`sct-app`: Vue + Node.js 前端项目

## 写在前面

在开始此项目前，请先学习这个基础项目模板 [cloud-template](https://github.com/TyCoding/cloud-template/tree/master/cloud-template) ，并仔细阅读以下开发文档：

[从零开始搭建Spring Cloud脚手架](https://github.com/TyCoding/cloud-template/blob/master/cloud-template/doc/env-1.md)

## Spring Cloud Template

* 一套极简的Spring Cloud微服务项目模板，开箱即用，方便扩展

* 基于 Spring Cloud Greenwich、Spring Boot 的微服务项目

* 基于 vue-admin-template。使用Vue框架，快速入门前后端分离式开发模式

* 详细的开发文档

## 核心依赖

| 依赖 | 版本 |
| --- | --- |
| Spring Boot | 2.1.5.RELEASE |
| Spring Cloud | Greenwich.SR1 |
| Spring Security OAuth2 | 2.3.4.RELEASE |
| tk.mybatis | 4.1.5 |
| vue-admin-template | 4.1.0 |

## 模块说明

```
sct-app -- 前端工程[8100]
sct-api 
├── sct-admin -- 系统管理模块
    ├── sct-admin-api -- 系统管理的公共api模块
    ├── sct-admin-biz -- 系统管理的业务实现模块 [4100]
├── sct-auth -- 授权模块 [4000]
├── sct-common -- 系统公共类模块
├── sct-config -- 配置中心 [8888]
├── sct-eureka -- Eureka服务注册与发现 [8761]
├── sct-gateway -- Zuul网关 [9999]
├── sct-monitor -- Spring Boot Admin监控 [3000]
├── sct-zipkin -- Zipkin链路监控 [3001]

```

## 启动说明

1. 修改本地`hosts`文件，添加如下内容（可以通过 [SwitchHosts](http://oldj.github.io/SwitchHosts/) 快速修改）。

```
127.0.0.1 sct-eureka
127.0.0.1 sct-mysql
127.0.0.1 sct-auth
```

2. 修改`sct-config/src/main/resources/`下配置文件中的数据库连接信息，主要涉及`sct-admin-biz-dev.yml`和`sct-auth-dev.yml`两个文件

3. 导入项目根目录下的`base.sql`，建立数据库

4. 严格按照如下顺序依次启动服务模块

```
EurekaApplication.java -- 服务注册中心
ConfigApplication.java -- 服务配置中心
MonitorApplication.java -- Spring Boot Admin监控
ZipkinApplication.java -- Zipkin链路监控
AdminBizApplication.java -- 系统管理模块
AuthApplication.java -- 授权模块
GatewayApplication.java -- Zuul网关
```

5. 启动前端项目

```shell
$ cd sct-app
$ npm install
$ npm run dev
```

## 预览

![](doc/2019052814147.png)

![](doc/2019052972549.png)

![](doc/2019052821354.png)

![](doc/2019052982515.png)

![](doc/2019052983430.png)

![](doc/2019052983452.png)

![](doc/2019052983458.png)

## 联系

QQ交流群：671017003

- [Blog@TyCoding's blog](http://www.tycoding.cn)
- [GitHub@TyCoding](https://github.com/TyCoding)
- [ZhiHu@TyCoding](https://www.zhihu.com/people/tomo-83-82/activities)
