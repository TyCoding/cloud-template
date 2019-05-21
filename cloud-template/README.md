# 一个最基础的Spring-Cloud分布式项目搭建模板

## 启动

请按照如下顺序依次启动服务：

- 1.`template-eureka`: 分布式服务注册中心
- 2.`template-config`: 分布式配置中心
- 3.`template-zipkin`: 分布式服务链路追踪
- 4.`template-boot-admin`: 分布式服务监控中心
- 5.`template-admin`: 服务提供者
- 6.`template-auth`: 服务消费者
- 7.`template-zuul`: 分布式API网关

## 初衷

学习了SpringCloud相关的教学视频后（或者文档），感觉仍是囫囵吞枣。先不谈使用SpringCloud分布式写多么复杂的项目，仅仅是SpringCloud最基础的环境搭建就很耗时。

看过很多基于SpringCloud的分布式微服务项目，star越多项目代码写的越高级、代码封装性太高，直接看源码就显得很头疼，特别是基本的项目配置都搞不清除，更别提看项目的业务代码了。

很多视频中使用的SpringCloud版本都是很老的，一些配置都改变了，按照老版本的文档配置高版本肯定会报错。

于是，顾写下此模板项目，用最通俗的文字让你了解并完整的搭建一个SpringCloud分布式微服务项目。

## 特点

1. 详细的搭建文档

2. 通俗的文字介绍

3. 采用 `SpringBoot-2.1.5.RELEASE`、`SpringCloud-Greenwich.SR1` 是目前最新的依赖版本

## 文档

最详细的搭建文档：

[从零开始搭建SpringCloud脚手架](https://github.com/TyCoding/cloud-template/blob/master/cloud-template/doc/env-1.md)

## 计划

此项目模板仅仅是最基础的SpringCloud项目搭建，没有具体的业务，只提供几个了测试用的接口。

后续会写基于Vue + Node.js、SpringCloud分布式前后端分离的CRUD项目，敬请期待。

如果对你的学习有帮助，希望大家能点亮右上角star支持作者

## 联系

QQ交流群：671017003

- [Blog@TyCoding's blog](http://www.tycoding.cn)
- [GitHub@TyCoding](https://github.com/TyCoding)
- [ZhiHu@TyCoding](https://www.zhihu.com/people/tomo-83-82/activities)
