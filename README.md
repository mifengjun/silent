

## Silent Task Handler Kit
![GitHub](https://img.shields.io/github/license/lvgocc/silent)
![GitHub](https://img.shields.io/badge/build-passing-brightgreen)
![GitHub](https://img.shields.io/badge/JDK-1.8-brightgreen)
![GitHub](https://img.shields.io/badge/version-1.0-orange)

## What's Silent Task Handler Kit

静默任务处理器组件可以赋予你的应用一键式多线程处理任务的能力, 它基于jdk1.8中concurrent包内容进行封装简化, 无任何第三方代码,
它提供了更简单的多线程任务处理方法,  其中你可以通过插拔式配置来满足你的需求
目前提供了以下配置

1. 启动线程大小
2. 自定义线程池
3. 同异步处理机制
4. 任务结束异步处理机制


## 快速开始

在项目中引入maven

```
<dependency>
  <groupId>org.lvgo</groupId>
  <artifactId>silent</artifactId>
  <version>1.0</version>
</dependency>
```

当你有一组任务数据需要处理, 只需像这样

```java
// 待处理任务数据, 比如是一组待更新的每天凌晨需要处理的数据
List<String> testData = new ArrayList<>();
// 多线程操作
new TaskHandler<String>(testData) {
    @Override
    public void run(String s) {
        //TODO 你的业务代码 根据每个数据的内容进行后续的业务操作
    }
}.execute();
```

它就可以帮你完成多线程任务处理. 并且它还支持同异步处理机制, 例如这样

```java
// 待处理任务数据, 比如是一组待更新的每天凌晨需要处理的数据
List<String> testData = new ArrayList<>();
// 多线程操作
new TaskHandler<String>(testData) {
    @Override
    public void run(String s) {
        //TODO 你的业务代码 根据每个数据的内容进行后续的业务操作
    }
// 设置 sync 同异步处理, 默认为异步
}.sync(true).execute();

// 方法 fun1() 需要等待所有任务处理结束后才能执行
fun1();

```

又或者说你的 fun1() 后面还有其他事情, 比如 fun2(), 但你想让任务处理结束后执行fun1(), 但fun2()不想同fun1()一起去等待任务结束, 此时你可以这样

```java
// 待处理任务数据, 比如是一组待更新的每天凌晨需要处理的数据
List<String> testData = new ArrayList<>();
// 多线程操作
new TaskHandler<String>(testData) {
    @Override
    public void run(String s) {
        //TODO 你的业务代码 根据每个数据的内容进行后续的业务操作
    }
// athend来实现异步执行结束后执行指定方法
}.athend(() -> {
            fund1();
         }.execute();

// 方法 fun2() 不需要等待所有任务处理结束后执行, 将与任务并行
fun2();

```
