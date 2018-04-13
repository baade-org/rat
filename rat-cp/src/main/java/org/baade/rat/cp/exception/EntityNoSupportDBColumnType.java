package org.baade.rat.cp.exception;

import java.lang.reflect.Field;

public class EntityNoSupportDBColumnType extends RuntimeException{

    public EntityNoSupportDBColumnType(Field field) {
        super("@Entity Class:[" + field.getDeclaringClass() + "] field:[" + field.getName() + "] not Support DB Column Type.");
    }
}
