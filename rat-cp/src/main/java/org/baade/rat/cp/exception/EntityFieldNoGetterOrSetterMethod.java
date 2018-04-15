package org.baade.rat.cp.exception;

import java.lang.reflect.Field;

public class EntityFieldNoGetterOrSetterMethod extends RuntimeException{

    public EntityFieldNoGetterOrSetterMethod(Class<?> clazz, String fieldName, Throwable cause) {
        super("@Entity Class:[" + clazz + "] field:[" + fieldName + "] not found getter or setter method.", cause);
    }

    public EntityFieldNoGetterOrSetterMethod(Field field) {
        super("@Entity Class:[" + field.getDeclaringClass() + "] field:[" + field.getName() + "] not found getter or setter method.");
    }
}
