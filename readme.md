# graceful shutdown template app

k8s:
https://kubernetes.io/zh/docs/concepts/workloads/pods/pod-lifecycle/#pod-termination

https://echo.labstack.com/cookbook/graceful-shutdown/

spring

https://docs.spring.io/spring-boot/docs/current/reference/html/features.html#features.graceful-shutdown

https://docs.spring.io/spring-boot/docs/current/reference/html/actuator.html#actuator.endpoints.enabling

spring with k8s

https://docs.spring.io/spring-boot/docs/current/reference/html/deployment.html#deployment.cloud.kubernetes

signal unix
https://zh.wikipedia.org/wiki/Unix%E4%BF%A1%E5%8F%B7

# 三种方式

## kill -2 pid

通过Runtime.addShutdownHook

## 自定义http System.exit()

System.exit()触发shutdown hook ，入口类 `ApplicationShutdownHooks`

其中使用springboot会注册`SpringApplicationShutdownHook`

如果配置`server.shutdown=graceful`  `SpringApplicationShutdownHook`会`先敬酒，敬酒不吃吃罚酒`



## actuator/shutdown

`curl -X POST http://localhost:8080/actuator/shutdown`


