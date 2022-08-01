package com.lx.beans;

public class GifParamBean {
    private int delayTime;

    private String gifName;

    private String gifFilePath;

    public GifParamBean(int delayTime, String gifName, String gifFilePath) {
        this.delayTime = delayTime;
        this.gifName = gifName;
        this.gifFilePath = gifFilePath;
    }

    public int getDelayTime() {
        return delayTime;
    }

    public void setDelayTime(int delayTime) {
        this.delayTime = delayTime;
    }

    public String getGifName() {
        return gifName;
    }

    public void setGifName(String gifName) {
        this.gifName = gifName;
    }

    public String getGifFilePath() {
        return gifFilePath;
    }

    public void setGifFilePath(String gifFilePath) {
        this.gifFilePath = gifFilePath;
    }

    @Override
    public String toString() {
        return "GifParamBean{" +
                "delayTime=" + delayTime +
                ", gifName='" + gifName + '\'' +
                ", gifFilePath='" + gifFilePath + '\'' +
                '}';
    }
}
