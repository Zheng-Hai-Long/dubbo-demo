package com.guangde.entry;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目相关人员信息
 * @author Administrator
 *
 */
public class ApiProjectUserInfo extends BaseBean implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    
    private Integer projectId;
    
    private String realName;
    
    private String sex;
    
    private Integer age;
    
    private String familyAddress;
    
    /**
     * 工作单位
     */
    private String workUnit;
    
    /**
     * 联系人
     */
    private String linkMan;
    
    /**
     * 联系号码
     */
    private String linkMobile;
    
    /**
     * 与受助人关系
     */
    private String relation;
    
    /**
     * 职务
     */
    private String persition;
    
    /**
     * 人员类型   ： 0 ：受助人信息  1 ： 证明人信息  2:发布人
     */
    private Integer personType;
    
    private Date createTime;
    
    /**
     * 职业
     */
    private String vocation;
    
    /**
     * qq/微信
     */
    private String qqOrWx;
    
    /**
     * 身份证
     */
    private String indetity;
    
    private String headImageId;
    
    private String headImageUrl ;
    
    private String litterImageUrl;
    /**
     * 用户信息
     */
    private String info ;
    /**
     * 个人和机构的项目分类：1（为本人），2（为家人），3（为其他亲戚），4（机构为本人），5（机构为公益项目）
     */
    private Integer helpType;
    /**
     * 审核受助人信息状态: 201:未审核; 202:审核不通过; 203:审核通过
     */
    private Integer auditState;
    /**
     * 受助人图片
     */
    private String contentImageId;
    public Integer getAuditState() {
		return auditState;
	}

	public void setAuditState(Integer auditState) {
		this.auditState = auditState;
	}

	public String getContentImageId() {
		return contentImageId;
	}

	public void setContentImageId(String contentImageId) {
		this.contentImageId = contentImageId;
	}

	public Integer getId()
    {
        return id;
    }
    
    public void setId(Integer id)
    {
        this.id = id;
    }
    
    public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getIndetity()
    {
        return indetity;
    }
    
    public void setIndetity(String indetity)
    {
        this.indetity = indetity;
    }
    
    public String getHeadImageId() {
		return headImageId;
	}

	public void setHeadImageId(String headImageId) {
		this.headImageId = headImageId;
	}

	public String getHeadImageUrl() {
		return headImageUrl;
	}

	public void setHeadImageUrl(String headImageUrl) {
		this.headImageUrl = headImageUrl;
	}

	public String getLitterImageUrl() {
		return litterImageUrl;
	}

	public void setLitterImageUrl(String litterImageUrl) {
		this.litterImageUrl = litterImageUrl;
	}

	public String getRelation()
    {
        return relation;
    }
    
    public void setRelation(String relation)
    {
        this.relation = relation;
    }
    
    public String getVocation()
    {
        return vocation;
    }
    
    public void setVocation(String vocation)
    {
        this.vocation = vocation;
    }
    
    public String getQqOrWx()
    {
        return qqOrWx;
    }
    
    public void setQqOrWx(String qqOrWx)
    {
        this.qqOrWx = qqOrWx;
    }
    
    public Integer getProjectId()
    {
        return projectId;
    }
    
    public void setProjectId(Integer projectId)
    {
        this.projectId = projectId;
    }
    
    public String getRealName()
    {
        return realName;
    }
    
    public void setRealName(String realName)
    {
        this.realName = realName;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public Integer getAge()
    {
        return age;
    }
    
    public void setAge(Integer age)
    {
        this.age = age;
    }
    
    public String getFamilyAddress()
    {
        return familyAddress;
    }
    
    public void setFamilyAddress(String familyAddress)
    {
        this.familyAddress = familyAddress;
    }
    
    public String getWorkUnit()
    {
        return workUnit;
    }
    
    public void setWorkUnit(String workUnit)
    {
        this.workUnit = workUnit;
    }
    
    public String getLinkMan()
    {
        return linkMan;
    }
    
    public void setLinkMan(String linkMan)
    {
        this.linkMan = linkMan;
    }
    
    public String getLinkMobile()
    {
        return linkMobile;
    }
    
    public void setLinkMobile(String linkMobile)
    {
        this.linkMobile = linkMobile;
    }
    
    public String getPersition()
    {
        return persition;
    }
    
    public void setPersition(String persition)
    {
        this.persition = persition;
    }
    
    public Integer getPersonType()
    {
        return personType;
    }
    
    public void setPersonType(Integer personType)
    {
        this.personType = personType;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Integer getHelpType() {
		return helpType;
	}

	public void setHelpType(Integer helpType) {
		this.helpType = helpType;
	}

	@Override
    public String toString()
    {
        return "ProjectUserInfo [age=" + age + ", createTime=" + createTime + ", familyAddress=" + familyAddress + ", id=" + id + ", linkMan=" + linkMan + ", linkMobile=" + linkMobile
            + ", linkRelation=" + relation + ", persition=" + persition + ", personType=" + personType + ", projectId=" + projectId + ", realName=" + realName + ", sex=" + sex + ", wordUnit="
            + workUnit + "]";
    }
    
}
