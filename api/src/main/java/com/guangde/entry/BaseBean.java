package com.guangde.entry;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class BaseBean
{
    /**
     * 排序字段
     */
    private String orderBy;
    
    /**
     * 倒序 desc
     * 顺序 asc
     */
    private String orderDirection;
    
    private Date queryStartDate;
    
    private Date queryEndDate;
     
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    
    public String getOrderBy()
    {
        return orderBy;
    }
    
    public void setOrderBy(String orderBy)
    {
        this.orderBy = orderBy;
    }
    
    public String getOrderDirection()
    {
        return orderDirection;
    }
    
    public void setOrderDirection(String orderDirection)
    {
        this.orderDirection = orderDirection;
    }
    
    public Date getQueryStartDate()
    {
        return queryStartDate;
    }
    
    public void setQueryStartDate(Date queryStartDate)
    {
        this.queryStartDate = queryStartDate;
    }
    
    public Date getQueryEndDate()
    {
        return queryEndDate;
    }
    
    public void setQueryEndDate(Date queryEndDate)
    {
        this.queryEndDate = queryEndDate;
    }
    
    
    /******************************************缓存统一处理*****************************************************************************
     * @author px
     * @date 2015-8-24
     * 
     * 缓存思路：
     * 基于对象的缓存，将缓存范围分为，类范围，整个对象范围，静态属性范围，动态属性范围
     * 获取和更新对象值，都将依据缓存范围（获取和更新都要手动指定范围）
     * 
     * 缓存更新策略：
     * 1.设置缓存有效时间
     * 2.更新对象内容时，如果范围时整个对象，整个对象范围，静态属性范围，动态属性范围，的缓存查询无效
     *   如果是静态属性范围，整个对象范围，静态属性范围，的缓存查询无效
     *   如果是动态属性范围，整个对象范围，动态属性范围，的缓存查询无效
     *   如果是类范围，类范围的缓存查询无效
     * 3.对于查询方法的返回结果，同步或异步更新查询范围
     * 4.对于增删改方法，异步删除相关对象范围的缓存
     * 
     * 缓存键值key:  默认，对象名+查询属性+分页条件
     * 缓存范围key:  默认，对象名+对象范围+对象主键
     *   
     */
    public static List<String> DEFAULT_RANGE_KEYS = null;
    public static CacheObjId[]DEFAULT_CACHEOBJIDS = null;
     
    /**
     * 该值代表类范围
     */
    public static final int RANGE_ALL = 0;
    /**
     * 该值代表整个对象范围
     */
    public static final int RANGE_WHOLE = 200;
    /**
     * 该值代表静态内容范围
     */
    public static final int RANGE_STATIC = 100;
    /**
     * 该值代表动态内容范围
     */
    public static final int RANGE_DYNAMIC = 300;
       
    protected static String getClassName(){
    	  String clazzName = new Object()    {
          public String getClassName() 
          {
              String clazzName = this.getClass().getName();
              return clazzName.substring(0, clazzName.lastIndexOf('$'));
          }
          }.getClassName();
          return clazzName;
    }
    
    /**
     * 是否开启缓存,默认不开启(只有当isCache==true,key不为空才开启缓存)
     */
    private transient boolean isCache = false;
    /**
     * 缓存键值
     */
    protected transient StringBuilder key;
    /**
     * 缓存有效时间
     */
    private transient Long validTime;
    /**
     * 缓存范围
     */
    private transient int range_level;
    /**
     * 固定缓存范围的key
     */
    protected transient List<String> range_key;
    /**
     * 缓存对象和对应的id属性
     */
    protected transient CacheObjId[]objIds;
    
    public boolean isCache() {
		return isCache;
	}

	public void setCache(boolean isCache) {
		this.isCache = isCache;
	}

	public Long getValidTime() {
		return validTime;
	}

	public void setValidTime(Long validTime) {
		this.validTime = validTime;
	}

	public int getRange_level() {
		return range_level;
	}

	public void setRange_level(int range_level) {
		this.range_level = range_level;
	}
	
	public StringBuilder getKey() {
		return key;
	}
	
	public List<String> getRange_key() {
		return range_key;
	}

	public void setRange_key(List<String> range_key) {
		this.range_key = range_key;
	}

	public void clearKey(String prefix){
		if(key!=null)
			key.delete(0, key.length());
		key = new StringBuilder(50);
		if(!StringUtils.isBlank(prefix)){
			key.append(prefix);
		}
	}
	/**
	 * 子类可以覆盖，实现自己的key值设定
	 */
	public void setKey(Object key) {
		if(key==null){
			this.key = null;
		}else{
			this.key = new StringBuilder(key.toString());
		}
	}
	public static String getCacheRange(String className,int range_level,Object id){
		return className+"_"+range_level+"_"+id;
	}
	/**
	 * 对查询结果分配缓存范围，以便更新操作时，将对应缓存范围的缓存置为无效
	 *
	 * @param result   查询结果
	 * @return 对应的缓存键值
	 */
    public List<String> allotCacheRange(Object result){
		if(result==null)
			return null;
		//是否已经指定固定缓存范围
		if(range_key!=null&&range_key.size()>0){
			return range_key;
		}
		if(objIds==null||objIds.length==0){
			return null;
		}
		List<String> rangeKeys = null;
		if(result.getClass().isArray()){
			int size = Array.getLength(result);
			if(size==0)
				return null;
			rangeKeys = new ArrayList<String>(objIds.length*size);
			for(int i=0;i<size;i++){
				getResultCacheRange(objIds,Array.get(result, i),rangeKeys);
			}
		}else if(result instanceof Collection){
			Collection c = (Collection)result;
			rangeKeys = new ArrayList<String>(objIds.length*c.size());
			for(Object obj:c){
				getResultCacheRange(objIds,obj,rangeKeys);
			}
		}else if(result instanceof Map){
			rangeKeys = new ArrayList<String>(objIds.length);
			Map map = (Map)result;
			 for(CacheObjId objId:objIds){
				 Object v = map.get(objId.getObjId());
				 if(v!=null){
					 rangeKeys.add(getCacheRange(objId.getClassName(),objId.getLevel(),v));
				 }
			 }
		}else if(!result.getClass().isPrimitive()){
			rangeKeys = new ArrayList<String>(objIds.length);
			getResultCacheRange(objIds,result,rangeKeys);
		}
		return rangeKeys;
	}
    private void getResultCacheRange(CacheObjId[]objIds,Object obj,List<String> rangeKeys){
    	     Object v = null;
    	     for(CacheObjId objId:objIds){
    	    	 v = getFieldValue(obj, objId.getObjId());
    	    	 if(v!=null){
    	    		 rangeKeys.add(getCacheRange(objId.getClassName(),objId.getLevel(),v));
    	    	 }
    	     }                                                                                                                                                                                                                                                                                                                                                     
    }
    /**
     * 通过反射获取指定值
     * @param obj
     * @param field
     * @return
     */
    public Object getFieldValue(Object obj,String field){
    	if(obj==null)
    	 return null;
    	Class c = obj.getClass();
    	if(c.isPrimitive())
    	 return null;
    	try {
    		Field f = c.getDeclaredField(field);
    	    f.setAccessible(true);
			return f.get(obj);
		} catch (Exception e) {
			return null;
		}
    }
    /**
     * 通过制定的属性值生成缓存key
     * @param keyField
     */
    private void generateKey(String...keyField){
    	Object obj = null;
    	for(String field:keyField){
   		   obj = getFieldValue(this,field);
   		   if(obj!=null){
   			   key.append("_");
   			   key.append(obj.toString());
   		   }
    	}
    }
    class CacheObjId{
    	private String className;//对应的缓存对象名称
    	private String objId;//对应的缓存对象id在返回结果中对应的属性值
    	private int level;
	
    	public CacheObjId(String className, String objId, int level) {
			super();
			this.className = className;
			this.objId = objId;
			this.level = level;
		}
		
    	public String getClassName() {
			return className;
		}
		
    	public String getObjId() {
			return objId;
		}
		
    	public int getLevel() {
			return level;
		}
    }
  
    /****************************开启缓存的接口start*******************/
    
    public void initNormalCache(boolean isCache,long time,String keyField){
    	initNormalCache(isCache,time,null,keyField);
    }
    public void initNormalCache(boolean isCache,long time,List<String> range_list,String keyField){
    	this.isCache = isCache;
    	if(isCache){
    		range_key = range_list;//缓存"查询和修改"关系的key（缓存范围key）
    		validTime = time;//缓存有效时间
        	//初始化缓存key
    		clearKey(keyField);
    	}
    }
    
    
    public void initCache(boolean isCache,long time,String...keyField){
    	initCache(isCache,time,DEFAULT_RANGE_KEYS,keyField);
    }
    /**
     * 指定固定缓存范围，适用于类范围
     * @param isCache
     * @param range_list
     */
    public void initCache(boolean isCache,long time,List<String> range_list,String...keyField){
    	this.isCache = isCache;
    	if(isCache){
    		range_key = range_list;//缓存"查询和修改"关系的key（缓存范围key）
    		validTime = time;//缓存有效时间
        	//初始化缓存key
    		clearKey(this.getClass().getName());
    		//按字段生成缓存key
        	generateKey(keyField);
    	}
    }
    
    
    /**
     * 根据查询结果生成缓存范围，适用非类范围
     * @param isCache
     * @param objIds
     */
    public void initCache(boolean isCache,long time,CacheObjId[]objIds,String...keyField){
    	this.isCache = isCache;
    	if(isCache){
    	    this.objIds = objIds;
    	    validTime = time;
        	clearKey(this.getClass().getName());
        	generateKey(keyField);
    	}
    }
    /****************************开启缓存的接口end*******************/
   
  
    /******************************************缓存统一处理*****************************************************************************/
    public  static void main(String[]args){
    	ApiProjectFeedback feedBack = new ApiProjectFeedback();
    	feedBack.setProjectId(123);
    	System.out.println(feedBack.getFieldValue(feedBack,"projectId"));
    }
}

