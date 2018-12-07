package com.guangde.entry;

import java.io.Serializable;

public class ApiTypeConfig extends BaseBean implements Serializable
{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private Integer sort;
    
    private String typeName;
    
    private String typeName_e;
    
    private String needdata;
    
    private String model;
    
    private Integer coverImageId;
    
    private String coverImageUrl;
    
    private String helpType;
   
    private String explain;
	
	private String uploadImageDirection;
	
	private String tagName;
	
	

	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	public String getUploadImageDirection() {
		return uploadImageDirection;
	}

	public void setUploadImageDirection(String uploadImageDirection) {
		this.uploadImageDirection = uploadImageDirection;
	}

	public String getHelpType() {
		return helpType;
	}

	public void setHelpType(String helpType) {
		this.helpType = helpType;
	}

	public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public Integer getSort()
    {
        return sort;
    }
    
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }
    
    public String getTypeName_e()
    {
        return typeName_e;
    }
    
    public void setTypeName_e(String typeNameE)
    {
        typeName_e = typeNameE;
    }
    
    public String getTypeName()
    {
        return typeName;
    }
    
    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }
    
    public String getNeeddata()
    {
        return needdata;
    }
    
    public void setNeeddata(String needdata)
    {
        this.needdata = needdata;
    }
    
    public String getModel()
    {
        return model;
    }
    
    public void setModel(String model)
    {
        this.model = model;
    }
    
    public Integer getCoverImageId() {
		return coverImageId;
	}

	public void setCoverImageId(Integer coverImageId) {
		this.coverImageId = coverImageId;
	}
	
	public String getCoverImageUrl() {
		return coverImageUrl;
	}

	public void setCoverImageUrl(String coverImageUrl) {
		this.coverImageUrl = coverImageUrl;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	@Override
	public String toString() {
		return "ApiTypeConfig [id=" + id + ", sort=" + sort + ", typeName="
				+ typeName + ", typeName_e=" + typeName_e + ", needdata="
				+ needdata + ", model=" + model + ", coverImageId="
				+ coverImageId + ", coverImageUrl=" + coverImageUrl
				+ ", helpType=" + helpType + ", explain=" + explain
				+ ", uploadImageDirection=" + uploadImageDirection
				+ ", tagName=" + tagName + "]";
	}
    
}
