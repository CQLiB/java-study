package com.libin.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class ProxyTest {

    public static void main(String[] args) {
        //需要被代理的对象实例
        Hello hello = new Hello();
        //实现了JDK动态代理接口
        InvocationHandler invocationHandler = new HelloProxy(hello);
        //利用JDK反射机制动态初始化代理对象
        HelloInterface helloInterface = (HelloInterface) Proxy.newProxyInstance(invocationHandler.getClass().getClassLoader(),hello.getClass().getInterfaces(),invocationHandler);
        //执行业务逻辑
        helloInterface.SayHello("");

    }
}
