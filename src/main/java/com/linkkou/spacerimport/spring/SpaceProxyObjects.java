package com.linkkou.spacerimport.spring;

import com.linkkou.spacerimport.SpacerImport;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import com.linkkou.spacerimport.localcall.SpringLocalCall;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Bean 注入代理类型
 *
 * @author LK
 * @date 2017/4/22
 */
public class SpaceProxyObjects<T> implements FactoryBean<T>, InitializingBean, DisposableBean {
    /**
     * 接口类
     */
    private Class<?> serviceClass;


    /**
     * 接口类
     *
     * @param serviceClass
     */
    public void setServiceClass(Class<?> serviceClass) {
        this.serviceClass = serviceClass;
    }


    /**
     * 生成的代理对象
     */
    private Object proxyObj;

    /**
     * 初始化Bean执行
     *
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        proxyObj = Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass}, new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object... args) throws Throwable {
                if (method.getDeclaringClass() == Object.class) {
                    return method.invoke(this, args);
                } else {
                    SpacerImport spacerImport = method.getAnnotation(SpacerImport.class);
                    SpringLocalCall springLocalCall = new SpringLocalCall(spacerImport);
                    return springLocalCall.getinvoke(proxy, method, args);
                }
            }

        });
    }

    /**
     * 返回代理实现
     *
     * @return T
     * @throws Exception
     */
    @Override
    public T getObject() throws Exception {
        return (T) proxyObj;
    }

    /**
     * 返回对象，防止Spring无法识别类，能用却报类型不确定
     *
     * @return Class<?>
     */
    @Override
    public Class<?> getObjectType() {
        return serviceClass;
    }

    /**
     * 缓存
     * @return boolean
     */
    @Override
    public boolean isSingleton() {
        return true;
    }

    /**
     * 释放
     *
     * @throws Exception
     */
    @Override
    public void destroy() throws Exception {

    }


}
