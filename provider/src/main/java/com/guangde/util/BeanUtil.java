package com.guangde.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;

import com.github.pagehelper.Page;
import com.guangde.business.util.CollectionUtil;
import com.guangde.pojo.ApiPage;

public class BeanUtil {
	public static <T> T copy(Object obj, Class<T> cla) {
		if (null == obj) {
			return null;
		}

		T t = null;

		try {
			t = cla.newInstance();
		} catch (InstantiationException e) {

		} catch (IllegalAccessException e) {

		}

		if (null == t) {
			return null;
		}

		try {
			BeanUtils.copyProperties(obj, t);
		} catch (BeansException e) {
			return null;
		}

		return t;
	}

    public static boolean isNull(Object... object)
    {
        boolean ret = false;
        for (Object obj : object)
        {
            if (obj == null)
            {
                ret = true;
                break;
            }
        }
        
        return ret;
    }
    
    public static <T> List<T> copyList(List<?> list, Class<T> clazz)
    {
        List<T> ret = new ArrayList<T>();

        if (CollectionUtil.isEmpty(list))
        {
            return ret;
        }

        
        for (Object obj : list)
        {
            T t = copy(obj, clazz);
            
            if (t != null)
            {
                ret.add(t);
            }
        }
        
        return ret;
    }
    
    @SuppressWarnings("unchecked")
    public static <T> ApiPage<T> copyPage(Page<?> page, Class<T> clazz)
    {
        ApiPage<T> p = copy(page, ApiPage.class);
        
        if (p != null)
        {
            List<T> list = copyList(page, clazz);
            p.setResultData(list);
        }

        return p;
    }

}
