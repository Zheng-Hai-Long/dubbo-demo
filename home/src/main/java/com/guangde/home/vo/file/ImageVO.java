package com.guangde.home.vo.file;

import java.util.List;

/**
 * Created by Administrator on 2018/8/30.
 */
public class ImageVO {

    private Integer imageId;

    private String imageUrl;

    private List<String> imageIds;

    private List<String> imageUrls;

    public ImageVO(){

    }

    public ImageVO(Integer imageId, String imageUrl) {
        this.imageId = imageId;
        this.imageUrl = imageUrl;
    }

    public ImageVO(List<String> imageIds, List<String> imageUrls) {
        this.imageIds = imageIds;
        this.imageUrls = imageUrls;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public List<String> getImageIds() {
        return imageIds;
    }

    public void setImageIds(List<String> imageIds) {
        this.imageIds = imageIds;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public void setImageUrls(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @Override
    public String toString() {
        return "ImageVO{" +
                "imageId=" + imageId +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageIds=" + imageIds +
                ", imageUrls=" + imageUrls +
                '}';
    }
}
