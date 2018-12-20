package cn.nascent.dto;

import java.io.Serializable;

/**
 * @author wei
 * @date 12/19/18  10:18 AM
 * <p>
 * <p>
 * 功能：对店铺操作的结果进行描述
 */
public class ApplicationExecution implements Serializable {

    /**
     * 应用状态码
     */
    private Integer statusCode;
    /**
     * 应用状态
     */
    private String statusInfo;

    /**
     * 是否存活
     */
    private boolean alive;

    /**
     * 应用名称
     */
    private String appName;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusInfo() {
        return statusInfo;
    }

    public void setStatusInfo(String statusInfo) {
        this.statusInfo = statusInfo;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    @Override
    public String toString() {
        return "ApplicationExecution{" +
                "statusCode=" + statusCode +
                ", statusInfo='" + statusInfo + '\'' +
                ", alive=" + alive +
                ", appName='" + appName + '\'' +
                '}';
    }
}
