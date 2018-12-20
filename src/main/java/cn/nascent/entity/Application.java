package cn.nascent.entity;

import cn.nascent.util.AppStatusEnum;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wei
 * @date 12/18/18 5:33 PM
 * <p>
 * 一个Application实例对应一个应用
 */
public class Application implements Serializable {

    /**
     * appid 应用id，唯一标识
     */
    private Integer appId;

    /**
     * 应用名
     */
    private String appName;

    /**
     * 应用状态
     */
    private AppStatusEnum appStatus;

    /**
     * 注册时间(即：第一次收到它的心跳日志的时间)
     */
    private Long registerTime;

    /**
     * 上一次的更新时间
     */
    private Long updateTime;


    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public AppStatusEnum getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(AppStatusEnum appStatus) {
        this.appStatus = appStatus;
    }

    public Long getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Long registerTime) {
        this.registerTime = registerTime;
    }

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }


    @Override
    public String toString() {
        return "Application{" +
                "appId=" + appId +
                ", appName='" + appName + '\'' +
                ", appStatus=" + appStatus +
                ", registerTime=" + registerTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
