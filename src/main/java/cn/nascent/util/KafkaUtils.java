package cn.nascent.util;

/**
 * @author wei
 * @date 12/18/18  6:12 PM
 * <p>
 * kafka相关配置
 */
public class KafkaUtils {

    /**
     * kafka Broker地址
     */
    public static final String BROKER_LIST = "192.168.80.176:9092";


    /**
     * 消费哪个主题下的消息
     */
    public static final String HEARTBEAT_TOPIC = "heartbeat-kafka-topic";

    /**
     * 只接收已经挂掉的应用名
     */
    public static final String DEAD_APP_TOPIC = "deadApp-kafka-topic";

    /**
     * 发送心跳信息的时间间隔(毫秒)
     */
    public static final Integer HEARTBEAT_PACKET_SEND_INTERVAL = 5000;

    /**
     * 检测应用是否挂掉的定时任务的执行间隔(秒)
     */
    public static final Integer APPLICATION_STATUS_CKECK_INTERVAL = 3;
}
