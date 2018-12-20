package cn.nascent.application;

import cn.nascent.entity.Application;
import cn.nascent.util.AppStatusEnum;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wei
 * @date 12/19/18  10:04 AM
 * <p>
 * 功能：保存Application，更新Application的状态
 * <p>
 * 在这里，将所有的应用放在ConcurrentHashMap(高并发容器)中，以应用名为键，以Application类实例为值，因为键是唯一的，
 * 那么就避免了店铺需要注册，因为注册需要遍历所有的店铺。
 */
public class ApplicationManager {


    /**
     * 用于保存所有的应用信息
     */
    private static final ConcurrentHashMap<String, Application> ALL_APPLICATION = new ConcurrentHashMap<String, Application>(10);

    public static ConcurrentHashMap getAllApplication() {
        return ALL_APPLICATION;
    }

    /**
     * 一个个地处理心跳消息
     *
     * @param record 一条信息
     * @return 更新是否成功
     */
    public static boolean updateApplication(ConsumerRecord record) {

        String mess = (String) record.value();
        //切割消息，获得项目名(0),其他信息(1)
        String[] result = mess.split(":");

        Application application = ApplicationManager.ALL_APPLICATION.get(result[0]);

        //在这里不处理应用的状态，只是更新应用，应用状态的处理放到定时任务中去
        if (application == null) {
            application = new Application();
            application.setAppName(result[0]);
            application.setRegisterTime(System.currentTimeMillis());
            application.setAppStatus(AppStatusEnum.APP_ALIVE);

            ApplicationManager.ALL_APPLICATION.put(result[0], application);
        }

        application.setUpdateTime(System.currentTimeMillis());

        System.err.println("项目名： " + result[0] + "信息: " + result[1] + "线程名:" + Thread.currentThread().getName());
        return true;
    }


    /**
     * 移除一个应用
     *
     * @param appName 应用名
     * @return 移除是否成功
     */
    public static boolean removeApplication(String appName) {

        ApplicationManager.ALL_APPLICATION.remove(appName);

        return true;
    }
}
