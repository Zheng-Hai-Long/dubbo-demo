package com.guangde.common.page;

import java.util.List;

public class Page<T> {

	private int pageNo; // 当前页码
	private int pageSize; // 每页行数
	private int totalRecord; // 总记录数
	private int totalPage; // 总页数
	private String SortValue;
	private int sortType;//排序方式，0不排序，1升序，-1降序
	private List<T> resultData;
	
	public Page() {
	}
	
	public Page(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	public Page(int pageNo, int pageSize, String SortValue, int sortType) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.SortValue = SortValue;
		this.sortType = sortType;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	
	public void setPageNo(int pageNo) {
		if(pageNo <= 0)
		{
			this.pageNo = 1;
		}
		else
		{
			this.pageNo = pageNo;
		}
		
	}
	
	public int getPageSize() {
		return pageSize;
	}
	
	public void setPageSize(int pageSize) {
		if(pageSize <= 0)
		{
			this.pageSize = 10;
		}
		else
		{
			this.pageSize = pageSize;
		}
		
	}
	
	public int getTotalRecord() {
		return totalRecord;
	}
	
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}
	
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public String getSortValue() {
		return SortValue;
	}

	public void setSortValue(String sortValue) {
		SortValue = sortValue;
	}

	public int getSortType() {
		return sortType;
	}

	public void setSortType(int sortType) {
		this.sortType = sortType;
	}

	public List<T> getResultData() {
		return resultData;
	}

	public void setResultData(List<T> resultData) {
		this.resultData = resultData;
	}

	@Override
	public String toString() {
		return "Page [pageNo=" + pageNo + ", pageSize=" + pageSize + ", totalRecord=" + totalRecord + ", totalPage="
				+ totalPage + "]";
	}
	
}
