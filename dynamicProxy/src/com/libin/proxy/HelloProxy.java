package com.libin.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 */
@SuppressWarnings("all")
public class HelloProxy implements InvocationHandler {
    private Object object;

    public HelloProxy(Object object){
        this.object = object;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //代理类 处理业务前的额外操作
        System.out.println("proxy-hello-before");
        //执行被代理类(hello)的具体业务
        method.invoke(proxy,method,args);
        //代理类 处理业务后的额外操作
        System.out.println("proxy-hello-after");

        return object;
    }
}
