package com.haemeta.common.utils.lang;

import com.haemeta.common.entity.pojo.AB;
import com.haemeta.common.lambda.FunctionPlus;
import com.haemeta.common.utils.lang.StringUtil;

import java.lang.annotation.Annotation;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class JavaBeanUtils {

    /**
     * 获取类的变量名 (包括所有父类)
     *
     * @param clazz 类加载器
     * @return 获取类的变量名 (包括所有父类)
     */
    public static List<String> getVariableName(Class<?> clazz) {
        List<String> varNameList = new ArrayList<>();
        // 遍历所有父类字节码对象
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            String[] varName = new String[declaredFields.length];
            for (int i = 0; i < varName.length; i++) {
                varName[i] = declaredFields[i].getName();
            }
            // 将`String[]`数组转换为`List<>`然后再将其拼接至`ArrayList`上
            varNameList.addAll(Arrays.asList(varName));
            // 获得父类的字节码对象
            clazz = clazz.getSuperclass();
        }
        return varNameList;
    }

    /**
     * 获取类的变量名 (包括所有父类)
     *
     * @param clazz 类加载器
     * @return 获取类的变量名 (包括所有父类)
     */
    public static List<String> getVariableNameExcludeAnnotation(Class<?> clazz, Class<Annotation>...annotations) {
        List<String> varNameList = new ArrayList<>();
        List<Class<Annotation>> annotationList = ListUtil.asList(annotations);
        // 遍历所有父类字节码对象
        while (clazz != null) {
            Field[] declaredFields = clazz.getDeclaredFields();
            fieldEach: for (int i = 0; i < declaredFields.length; i++) {
                Field field = declaredFields[i];
                for (Annotation annotation : field.getAnnotations()) {
                    if(annotationList.contains(annotation)){
                        continue fieldEach;
                    }
                }
                varNameList.add(field.getName());
            }
            // 获得父类的字节码对象
            clazz = clazz.getSuperclass();
        }
        return varNameList;
    }

    /**
     * 通过字段名获取 get 方法
     * @param tClass
     * @param names
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> AB<Map<String,Method>,Map<String,Method>> getGetSetMethod(Class<T> tClass, List<String> names){
        Map<String,Method> getMap = new HashMap<>();
        Map<String,Method> setMap = new HashMap<>();

        List<Method> methods = new ArrayList<>(ListUtil.asList(tClass.getMethods()));
        names = new ArrayList<>(names);

        for (int j = 0; j < names.size(); j++) {
            String name = names.get(j);
            String nameUpperFirstCause = StringUtil.upperFirstCase(name);
            boolean get = false;
            boolean set = false;
            inner: for (int i = 0; i < methods.size(); i++) {
                Method method = methods.get(i);
                if(method.getName().equals("get"+nameUpperFirstCause)){
                    getMap.put(name,method);
                    methods.remove(i);
                    i --;
                    get = true;
                }
                if(method.getName().equals("set"+nameUpperFirstCause)){
                    setMap.put(name,method);
                    methods.remove(i);
                    i --;
                    set = true;
                }
                if(get && set){
                    names.remove(j);
                    j --;
                    break inner;
                }
            }

        }



        return AB.create(getMap,setMap);
    }

    /**
     * 获取 class 非私有方法
     * @param targetClass
     * @param methodName
     * @param <Target>
     * @return
     * @throws NoSuchMethodException
     */
    public static <Target> Method getPublicMethod(Class<Target> targetClass, String methodName) throws NoSuchMethodException {
        return targetClass.getMethod(methodName);
    }

    /**
     * 获取 class 私有方法
     * @param targetClass
     * @param methodName
     * @param <Target>
     * @return
     * @throws NoSuchMethodException
     */
    public static <Target> Method getPrivateMethod(Class<Target> targetClass, String methodName) throws NoSuchMethodException {
        return targetClass.getDeclaredMethod(methodName);
    }

    /**
     * 获取指定类型的指定方法映射
     * @param targetClass
     * @param methodName
     * @param <Target>
     * @return
     * @throws NoSuchMethodException
     */
    public static <Target> Method getMethod(Class<Target> targetClass,String methodName) throws NoSuchMethodException {
        for (Method method:targetClass.getMethods()){
            if (method.getName().equals(methodName)){
                return method;
            }
        }
        for (Method method:targetClass.getDeclaredMethods()){
            if (method.getName().equals(methodName)){
                return method;
            }
        }
        throw new NoSuchMethodException(targetClass.getName() + "." + methodName);
    }

    /**
     * 获取方法 所需要的参数类型
     * @param method
     * @return
     */
    public static Class[] getMethodParameterTypes(Method method){
        return method.getParameterTypes();
    }

    /**
     * 获取方法 所需要的参数类型
     * @param method
     * @param parameterIndex  指定第几个参数
     * @return
     */
    public static Class getMethodParameterType(Method method,int parameterIndex) throws Exception {
        Class[] rs = getMethodParameterTypes(method);
        if (rs.length == 0)
            throw new Exception(method.getName()+"不需要传递参数");
        return rs[0];
    }

    /**
     * 通过 序列化版：FunctionPlus，获取当前 get 方法所 get的属性名
     */
    public static <Target,Return> String getGetFunctionFiledName(FunctionPlus<? super Target,? extends Return> target){
        return StringUtil.lowerFirstCase(getSerializedLambda(target).getImplMethodName().substring("get".length()));
    }

    /**
     * 通过序列化版：FunctionPlus 获取Lambda的相关信息
     * @param fn
     * @param <T>
     * @return
     */
    private static <T> SerializedLambda getSerializedLambda(FunctionPlus<T, ?> fn) {
        // 从function取出序列化方法
        Method writeReplaceMethod;
        try {
            writeReplaceMethod = fn.getClass().getDeclaredMethod("writeReplace");
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

        // 从序列化方法取出序列化的lambda信息
        boolean isAccessible = writeReplaceMethod.isAccessible();
        writeReplaceMethod.setAccessible(true);
        SerializedLambda serializedLambda;
        try {
            serializedLambda = (SerializedLambda) writeReplaceMethod.invoke(fn);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
        writeReplaceMethod.setAccessible(isAccessible);
        return serializedLambda;
    }


}
