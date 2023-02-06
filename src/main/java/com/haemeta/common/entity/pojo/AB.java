package com.haemeta.common.entity.pojo;

public class AB<A,B> {
    private A a;
    private B b;

    public A getA() {
        return a;
    }

    public void setA(A a) {
        this.a = a;
    }

    public B getB() {
        return b;
    }

    public void setB(B b) {
        this.b = b;
    }

    private AB(A a,B b){
        this.a = a;
        this.b = b;
    }

    public static <A,B> AB create(A a ,B b){
        return new AB(a,b);
    }

}
