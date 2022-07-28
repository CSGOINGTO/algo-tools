package com.lx.utils.CollectionUtils;

import java.util.List;

public class BaseUtils {

    public static <T> void swap(List<T> list, int before, int after) {
        T t = list.get(before);
        list.add(before, list.get(after));
        list.add(after, t);
    }

    public static <T> void deleteElement(List<T> list, int index) {
        list.remove(index);
    }

//    public static

}
