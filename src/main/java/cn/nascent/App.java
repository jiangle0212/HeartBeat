package cn.nascent;

import cn.nascent.message.DealWithMessage;
import cn.nascent.quartz.QuartzManager;
import cn.nascent.quartz.job.CheckAppStatusJob;
import cn.nascent.util.KafkaUtils;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * @author wei
 * @date 12/18/18  6:06 PM
 * <p>
 * 作为一个kafka消费者，轮询进行消费消息，将获取到的消息交给线程池去处理
 */
public class App {
    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.put("bootstrap.servers", KafkaUtils.BROKER_LIST);
        properties.put("group.id", "HeartBeatGroup");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "latest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Arrays.asList(KafkaUtils.HEARTBEAT_TOPIC));

        //开始应用状态检测的定时任务
        QuartzManager.addJob("Status_Check", CheckAppStatusJob.class, KafkaUtils.APPLICATION_STATUS_CKECK_INTERVAL);

        while (true) {

            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofSeconds(1));

            //在这里交由线程池去处理,因为consumer在以此轮询中要发送心跳，群组协调，分区再均衡，获取数据，
            // 所以要确保在轮询期间的工作要尽快完成
            DealWithMessage.dealWithRecords(records);

        }
    }
}
