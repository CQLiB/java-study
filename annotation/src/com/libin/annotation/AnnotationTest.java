package com.libin.annotation;

import java.lang.reflect.Method;

public class AnnotationTest {
    @AnnotationR("Test")
    public static void main(String[] args) throws NoSuchMethodException {
        Class T = AnnotationTest.class;
        Method method = T.getMethod("main", String[].class);
        System.out.println("test----------");
        AnnotationR annotationR = method.getAnnotation(AnnotationR.class);

    }
}
