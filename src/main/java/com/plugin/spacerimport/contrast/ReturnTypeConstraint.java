package com.plugin.spacerimport.contrast;

import com.plugin.spacerimport.returns.RPCResponse;

import java.lang.reflect.*;

/**
 * 返回对象是否符合要求
 */
public class ReturnTypeConstraint {

    /**
     * 泛型类型
     */
    private Type type;

    /**
     * 方法
     */
    private Method method;

    private boolean rpcresponse;

    /**
     * 获取的实际Class
     * @param type
     * @return
     */
    private Class<?> getRawType(Type type) {
        if (type instanceof Class) {
            return (Class)type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType)type;
            Type rawType = parameterizedType.getRawType();
            if (!(rawType instanceof Class)) {
                throw new IllegalArgumentException();
            } else {
                return (Class)rawType;
            }
        } else if (type instanceof GenericArrayType) {
            Type componentType = ((GenericArrayType)type).getGenericComponentType();
            return Array.newInstance(getRawType(componentType), 0).getClass();
        } else if (type instanceof TypeVariable) {
            return Object.class;
        } else if (type instanceof WildcardType) {
            return getRawType(((WildcardType)type).getUpperBounds()[0]);
        } else {
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
        }
    }

    public ReturnTypeConstraint(Method method){
        // 获取返回值类型
        Type type = method.getGenericReturnType();
        // 判断获取的类型是否是参数类型
        if (type instanceof ParameterizedType) {
            // 强制转型为带参数的泛型类型，
            Type[] typesto = ((ParameterizedType) type).getActualTypeArguments();
            Class returntypesto = getRawType(type);
            if(returntypesto == RPCResponse.class){
                this.rpcresponse = true;
                if(typesto.length == 1 ){
                    this.type = typesto[0];
                }
            }
        }
    }

    /**
     * 返回对象是否合法
     * @return
     */
    public boolean isRPCResponse(){
        return this.rpcresponse;
    }

    /**
     * 获取对象返回值
     */
    public Type getGenericReturnType(){
        return this.type;
    }

}
