package org.baade.rat.cp.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;

public class ClassUtils {

    public static boolean checkSetterAndGetterMethod(Field field){

        try {
            // 检查get,set方法是否存在
            String fieldName = field.getName();
            Class<?> clazz = field.getDeclaringClass();
            new PropertyDescriptor(fieldName, clazz);
            return true;
        } catch (IntrospectionException e) {
            // TODO: 2018/4/12
            //throw new NoGetterOrSetterMethod(clazz, fieldName, e);
            return false;
        }
    }
}
