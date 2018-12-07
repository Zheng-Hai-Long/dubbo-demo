package com.guangde.home.vo.file;

/**
 * Created by Administrator on 2018/8/30.
 */
public class FileVO {

    private Integer fileId;

    private String fileUrl;

    public FileVO(){

    }

    public FileVO(Integer fileId, String fileUrl) {
        this.fileId = fileId;
        this.fileUrl = fileUrl;
    }

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
}
