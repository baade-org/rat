package org.baade.rat.cp.anno.parse;

import java.util.Collection;

public interface IParser<K, T> {

    public T parse(K k, Class<? extends T> clazz) throws ClassNotFoundException;

    public Collection<T> parse(Collection<K> ks, Class<? extends T> clazz) throws ClassNotFoundException;

}
