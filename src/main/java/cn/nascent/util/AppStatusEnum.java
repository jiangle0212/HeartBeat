package cn.nascent.util;

/**
 * @author wei
 * @date 12/18/18  5:43 PM
 * <p>
 * <p>
 * 应用状态
 */
public enum AppStatusEnum {
    APP_ALIVE(1, "应用正在运行"), APP_PLANT(0, "存在一次没有按时发送心跳信息"), APP_DEAD(-1, "应用已经挂掉");

    /**
     * 应用状态码
     */
    private Integer statusCode;
    /**
     * 应用状态信息
     */
    private String statusMessage;

    private AppStatusEnum(Integer statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    /**
     * 根据应用状态码来获取应用状态
     *
     * @param statusCode 应用状态码
     * @return 应用状态
     */
    public static AppStatusEnum getAppStatusEnumByCode(Integer statusCode) {

        for (AppStatusEnum appStatusEnum : AppStatusEnum.values()) {
            if (appStatusEnum.getStatusCode().equals(statusCode)) {
                return appStatusEnum;
            }
        }

        return null;

    }
}
