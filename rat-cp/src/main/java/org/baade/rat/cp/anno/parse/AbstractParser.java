package org.baade.rat.cp.anno.parse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractParser<K, T> implements IParser<K, T> {

    @Override
    public Collection<T> parse(Collection<K> ks, Class<? extends T> clazz) throws ClassNotFoundException {
        List<T> list = new ArrayList<>();
        if (ks != null && ks.size() != 0) {
            for (K k : ks) {
                T t = parse(k, clazz);
                if (t != null) {
                    list.add(t);
                }
            }
            return list;
        }
        return null;
    }
}
