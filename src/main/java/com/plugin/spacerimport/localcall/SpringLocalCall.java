package com.plugin.spacerimport.localcall;


import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;
import com.plugin.json.Json;
import com.plugin.spacerimport.SpacerImport;
import com.plugin.spacerimport.contrast.ReturnTypeConstraint;
import com.plugin.spacerimport.returns.SpaceReaponseModel;

import com.plugin.spacerimport.contrast.TypeContrast;
import com.plugin.spacerimport.error.ErrorType;
import com.plugin.spacerimport.spring.SpaceSpringContextUtil;
import org.springframework.aop.support.AopUtils;

import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * 加载Bean
 */
public class SpringLocalCall {

    private static SpaceSpringContextUtil spaceSpringContextUtil = new SpaceSpringContextUtil();

    //数据转换工具
    private class json extends Json {
        @Override
        protected String toJson(Object src) {
            return super.toJson(src);
        }

        @Override
        protected <T> T fromJson(String json, Type type) {
            return super.fromJson(json, type);
        }

        @Override
        protected  <T> TypeAdapter<T> getAdapter(TypeToken<T> type) {
            return super.getAdapter(type);
        }

        @Override
        protected JsonWriter newJsonWriter(Writer writer) throws IOException {
            return super.newJsonWriter(writer);
        }
    }

    /**
     * 注解值
     */
    private SpacerImport SpacerImport;

    /**
     * 容器中对象
     */
    private Object bean;

    /**
     * 方法
     */
    private Method method;

    /**
     * class 名称
     */
    private String classname;
    /**
     * 方法名称
     */
    private String methodname;
    /**
     * bean名称
     */
    private String beanname;

    /**
     * 是否强制报错
     */
    private boolean exception;

    /**
     * 初始化
     *
     * @param SpacerImport
     */
    public SpringLocalCall(SpacerImport SpacerImport) {
        this.SpacerImport = SpacerImport;
        parsing();
    }

    /**
     * 解析注解
     */
    private void parsing() {
        //#{ServiceDemoBeCall.Demo}
        String methods = this.SpacerImport.ServiceClassMethods();
        this.beanname = this.SpacerImport.BeanName();
        this.exception = this.SpacerImport.Exception();
        String[] methodss = methods.split("\\.");
        this.classname = methodss[0].replace("#", "").replace("{", "");
        this.methodname = methodss[1].replace("}", "");
    }

    /**
     * 判断是否能获取到bean
     *
     * @return
     */
    private boolean getNotEmptyBean() {
        try {
            String name;
            if (beanname != null && beanname.length() > 0) {
                name = beanname;
            } else {
                name = classname.substring(0, 1).toLowerCase() + classname.substring(1);
            }
            bean = spaceSpringContextUtil.getBean(name);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取远程相对应的方法的所以参数
     *
     * @return
     */
    public Class<?>[] getParameterTypes() {
        Method[] methods = bean.getClass().getMethods();
        for (Method m : methods) {
            /**
             * #{ServiceDemoBeCall.Demo}
             */
            if (m.getName().equals(methodname)) {
                method = m;
                //获取参数并且打印
                Class<?>[] parameterTypes = m.getParameterTypes();
                for (Class<?> clas : parameterTypes) {
                    String parameterName = clas.getName();
                    System.out.println("参数名称:" + parameterName);
                }
                return parameterTypes;
            }
        }
        return null;
    }


    /**
     * 执行远程方法
     *
     * @param m
     * @param args
     * @return
     */
    public Object getinvoke(Object proxy,Method m, Object... args) throws Exception{
        SpaceReaponseModel spaceReaponseModel = new SpaceReaponseModel();
        if(getNotEmptyBean()){
            Class<?>[] classes = getParameterTypes();
            if (classes != null) {
                TypeContrast typeContrast = new TypeContrast(classes, args);
                if (typeContrast.Contrast()) {
                    Object o = null;
                    ReturnTypeConstraint returnTypeConstraint = new ReturnTypeConstraint(m);
                    if (returnTypeConstraint.isRPCResponse()) {
                        try {
                            o = method.invoke(bean, typeContrast.getconversionargs().toArray());
                            final json json1 = new json();
                            String json = json1.toJson(o);
                            spaceReaponseModel.setSuccessful(true)
                                    .setModel(json1.fromJson(json, returnTypeConstraint.getGenericReturnType()))
                                    .setJsonstring(json);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if(exception){
                                throw e;
                            }
                            spaceReaponseModel.setSuccessful(false).setErrortype(ErrorType.Exception).setException(e);
                            e.printStackTrace();
                            return spaceReaponseModel;
                        }
                    } else {
                        spaceReaponseModel.setSuccessful(false).setErrortype(ErrorType.ReturnNotRPCResponse);
                    }
                }else {
                    spaceReaponseModel.setSuccessful(false).setErrortype(ErrorType.ContrastError);
                }
            }else{
                spaceReaponseModel.setSuccessful(false).setErrortype(ErrorType.NotMethod);
            }
        }else {
            spaceReaponseModel.setSuccessful(false).setErrortype(ErrorType.NotBean);
        }
        return spaceReaponseModel;
    }
}
