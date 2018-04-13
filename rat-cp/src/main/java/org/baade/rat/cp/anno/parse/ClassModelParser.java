package org.baade.rat.cp.anno.parse;

import org.baade.rat.core.scan.model.ClassModel;
import org.baade.rat.cp.anno.Entity;
import org.baade.rat.cp.table.Table;

public class ClassModelParser extends AbstractParser<ClassModel, Table> implements IParser<ClassModel, Table> {

    @Override
    public Table parse(ClassModel classModel, Class<? extends Table> clazz) throws ClassNotFoundException {
        Class<?> clazzOfModel = Class.forName(classModel.getClassFullName());
        if (!clazzOfModel.isAnnotationPresent(Entity.class)) {
            return null;
        }
        return (Table) ParseType.ENTITY.getParser().parse(clazzOfModel, clazz);
    }
}
