package cn.nascent.quartz.job;

import cn.nascent.application.ApplicationManager;
import cn.nascent.entity.Application;
import cn.nascent.kafka.FeedBackToKafka;
import cn.nascent.util.AppStatusEnum;
import cn.nascent.util.KafkaUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wei
 * @date 12/19/18  11:42 AM
 * <p>
 * 功能：定时去检测应用的状态，根据状态来进行操作
 */
public class CheckAppStatusJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        //状态校验的开始时间，因为应用里的更新时间是固定的，故所有的应用的参照时间也应该是不变的
        long checkStartTime = System.currentTimeMillis();

        ConcurrentHashMap concurrentHashMap = ApplicationManager.getAllApplication();
        Set<Map.Entry<String, Application>> entrySet = concurrentHashMap.entrySet();
        List<String> deadAppName = new ArrayList<>(10);

        Iterator<Map.Entry<String, Application>> iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Application> entry = iterator.next();
            Application application = entry.getValue();

            //@TODO 可能这里的判断不够准确,需要改进
            if (application.getUpdateTime() + KafkaUtils.HEARTBEAT_PACKET_SEND_INTERVAL < checkStartTime) {

                if (application.getAppStatus().getStatusCode().equals(AppStatusEnum.APP_ALIVE.getStatusCode())) {
                    application.setAppStatus(AppStatusEnum.APP_PLANT);
                    System.err.println("应用状态： 应用: " + application.getAppName() + " 已经变为植物状态");
                } else if (application.getAppStatus().getStatusCode().equals(AppStatusEnum.APP_PLANT.getStatusCode())) {
                    application.setAppStatus(AppStatusEnum.APP_DEAD);
                    System.err.println("应用: " + application.getAppName() + " 已经挂掉了");
                    deadAppName.add(application.getAppName());
                }
            }
        }

        //将已经宕机的应用从系统中清除
        for (String appName : deadAppName) {
            ApplicationManager.removeApplication(appName);
            FeedBackToKafka.sendMessage(appName);
            System.err.println("应用状态：应用: " + appName + " 已经从系统清除了");
        }

    }
}
