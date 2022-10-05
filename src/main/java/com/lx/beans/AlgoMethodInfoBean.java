package com.lx.beans;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * AlgoMethod方法的详细信息
 */
public class AlgoMethodInfoBean {

    /**
     * algoMethod
     */
    private Method algoMethod;

    /**
     * algoMethod调用时所传入的参数类型及参数
     */
    private Map<Class<?>, ?> paramType2paramValueMap;

    public AlgoMethodInfoBean() {
    }

    public AlgoMethodInfoBean(Method algoMethod, Class<?> algoClass, Map<Class<?>, ?> paramType2paramValueMap) {
        this.algoMethod = algoMethod;
        this.paramType2paramValueMap = paramType2paramValueMap;
    }

    public Method getAlgoMethod() {
        return algoMethod;
    }

    public void setAlgoMethod(Method algoMethod) {
        this.algoMethod = algoMethod;
    }

    public Map<Class<?>, ?> getParamType2paramValueMap() {
        return paramType2paramValueMap;
    }

    public void setParamType2paramValueMap(Map<Class<?>, ?> paramType2paramValueMap) {
        this.paramType2paramValueMap = paramType2paramValueMap;
    }
}
