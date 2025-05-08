package com.itheima.reggie.common;

public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal();

    public static Long getEId(){
        return threadLocal.get();
    }
    public static void setEId(Long id)
    {
        threadLocal.set(id);
    }
}
