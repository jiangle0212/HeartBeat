package cn.nascent.message;

import cn.nascent.application.ApplicationManager;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.util.concurrent.*;

/**
 * @author wei
 * @date 12/19/18  8:55 AM
 * <p>
 * 使用线程池来处理消息，更新应用的状态
 */
public class DealWithMessage {

    /**
     * 线程池的基本大小
     */
    private static int CORE_POOL_SIZE = 10;
    /**
     * 线程池最大数量
     */
    private static int MAXIMUM_POOL_SIZE = 20;
    /**
     * 线程池工作线程空闲之后，保持存活的时间
     */
    private static int KEEP_ALIVE_TIME = 1;
    /**
     * 线程活动时间单位
     */
    private static TimeUnit UNIT = TimeUnit.MILLISECONDS;
    /**
     * 任务队列,保存等待执行的任务的阻塞队列，ArrayBlockingQueue(FIFO)
     */
    private static BlockingQueue BLOCKING_QUEUE = new ArrayBlockingQueue(DealWithMessage.CORE_POOL_SIZE);

    /**
     * 线程池
     */
    private static ExecutorService EXECUTOR_SERVICE = new ThreadPoolExecutor(DealWithMessage.CORE_POOL_SIZE,
            DealWithMessage.MAXIMUM_POOL_SIZE, DealWithMessage.KEEP_ALIVE_TIME, DealWithMessage.UNIT,
            DealWithMessage.BLOCKING_QUEUE);


    /**
     * 在这里利用线程池来处理获取到的消息
     *
     * @param records 一次轮询获取(APP作为一个消费者获取到的)到的消息
     */
    public static void dealWithRecords(final ConsumerRecords<String, String> records) {

        DealWithMessage.EXECUTOR_SERVICE.execute(() -> {
            for (ConsumerRecord<String, String> record : records) {
                ApplicationManager.updateApplication(record);
            }
        });

    }


}
