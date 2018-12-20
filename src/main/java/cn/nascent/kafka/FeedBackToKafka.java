package cn.nascent.kafka;

import cn.nascent.util.KafkaUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * @author wei
 * @date 12/19/18  3:16 PM
 * <p>
 * 功能：将挂掉的应用的信息发送给kafka，再发送邮件
 */
public class FeedBackToKafka {

    private static KafkaProducer<String, String> KAFKA_PRODUCER;

    /**
     * 构造出Kafka Producer
     */
    static {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", KafkaUtils.BROKER_LIST);
        properties.put("acks", "1");
        properties.put("retries", 3);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KAFKA_PRODUCER = new KafkaProducer<String, String>(properties);
    }

    /**
     * 将挂掉的应用发送给kafka
     *
     * @param appName 应用名
     */
    public static void sendMessage(String appName) {

        String sendMess = "AppDeadError:" + appName;

        Future<RecordMetadata> response = FeedBackToKafka.KAFKA_PRODUCER.send(new ProducerRecord<String, String>(KafkaUtils.DEAD_APP_TOPIC, sendMess));

        try {

            //当服务器返回错误，get方法会抛出异常，否则返回RecordMetadata对象，可以来获取消息的偏移量
            response.get();

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("服务器返回错误");
            throw new RuntimeException(e.getMessage());
        }


    }
}
