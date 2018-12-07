package com.guangde.business.util;

import java.util.Collection;
import java.util.Map;

public class CollectionUtil
{
    public static boolean isEmpty(Collection<?> collection)
    {
        return collection == null || collection.isEmpty();
    }
    
    public static boolean isNotEmpty(Collection<?> collection)
    {
        return !isEmpty(collection);
    }
    
    public static boolean isMapEmpty(Map<?, ?> map)
    {
        return map == null || map.isEmpty();
    }
    
    public static boolean isMapNotEmpty(Map<?, ?> map)
    {
        return !isMapEmpty(map);
    }
}

