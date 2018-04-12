package org.baade.rat.cp.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;

public class ClassUtils {

    public static boolean checkSetterAndGetterMethod(Class<?> clazz, String fieldName){

        try {
            // 检查get,set方法是否存在
            new PropertyDescriptor(fieldName, clazz);
            return true;
        } catch (IntrospectionException e) {
            // TODO: 2018/4/12
            //throw new NoGetterOrSetterMethod(clazz, fieldName, e);
            return false;
        }
    }
}
