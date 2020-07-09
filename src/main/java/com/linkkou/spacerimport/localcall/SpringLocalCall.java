package com.linkkou.spacerimport.localcall;

import com.linkkou.spacerimport.SpacerImport;
import com.linkkou.spacerimport.spring.SpaceSpringContextUtil;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * 加载Bean
 */
public class SpringLocalCall {

    private static final SpaceSpringContextUtil SPACE_SPRING_CONTEXT_UTIL = new SpaceSpringContextUtil();

    /**
     * 注解值
     */
    private final com.linkkou.spacerimport.SpacerImport SpacerImport;

    /**
     * 容器中对象
     */
    private Object bean;

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
     * 初始化
     *
     * @param spacerImport
     */
    public SpringLocalCall(SpacerImport spacerImport) {
        this.SpacerImport = spacerImport;
        parsing();
    }

    /**
     * 解析注解
     */
    private void parsing() {
        String methods = this.SpacerImport.value();
        this.beanname = this.SpacerImport.BeanName();
        //#{ServiceDemoBeCall.Demo}
        String[] methodss = methods.split("\\.");
        this.classname = methodss[0].replace("#", "").replace("{", "");
        this.methodname = methodss[1].replace("}", "");
    }

    /**
     * 判断是否能获取到bean
     *
     * @return boolean
     */
    private boolean getNotEmptyBean() {
        try {
            String name;
            if (beanname != null && beanname.length() > 0) {
                name = beanname;
            } else {
                name = classname.substring(0, 1).toLowerCase() + classname.substring(1);
            }
            bean = SPACE_SPRING_CONTEXT_UTIL.getBean(name);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取调用的Bean相对应的方法的所以参数
     *
     * @return Class<?>[]
     */
    public Method[] getBeanMethod() {
        Method[] methods = bean.getClass().getMethods();
        List<Method> methodlist = new ArrayList<>();
        for (Method m : methods) {
            /**
             * #{ServiceDemoBeCall.Demo}
             */
            if (m.getName().equals(methodname)) {
                methodlist.add(m);
            }
        }
        return methodlist.toArray(new Method[0]);
    }

    /**
     * 获取到对应的方法
     *
     * @param methods
     * @param methodcall
     */
    private Method methodoverload(Method[] methods, Method methodcall) {
        for (Method m : methods) {
            if (m.getName().equals(methodcall.getName())) {
                if (m.getParameters().length == methodcall.getParameters().length) {
                    boolean el = false;
                    for (int i = 0; i < m.getParameters().length; i++) {
                        Type parameterizedType = methodcall.getParameters()[i].getParameterizedType();
                        //泛型判断
                        if (parameterizedType instanceof ParameterizedTypeImpl) {
                            Class<?> rawType = ((ParameterizedTypeImpl) methodcall.getParameters()[i].getParameterizedType()).getRawType();
                            parameterizedType = rawType;
                        }
                        if (!m.getParameters()[i].getParameterizedType().equals(parameterizedType)) {
                            el = true;
                            break;
                        }
                    }
                    if (!el) {
                        return m;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 执行Bean方法
     *
     * @param m
     * @param args
     * @return
     */
    public Object getinvoke(Object proxy, Method m, Object... args) throws Exception {
        if (getNotEmptyBean()) {
            final Method[] beanMethod = getBeanMethod();
            final Method methodoverload = methodoverload(beanMethod, m);
            if (Optional.ofNullable(methodoverload).isPresent()) {
                methodoverload.invoke(bean, args);
            }
        }
        throw new IllegalAccessException();
    }
}
