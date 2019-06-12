package com.pay.sign.access.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 
 * Title: 
 * Description:   
 * Copyright: 2015年6月12日下午2:47:04
 * Company: SDJ
 * @author zhongxiang.ren
 */
public class TaskSchedule extends Thread {
    /**
     * Logger for this class
     */
    private static final Log logger = LogFactory.getLog(TaskSchedule.class);

    private TaskManager taskManager;

    public TaskSchedule() {

    }

    public TaskSchedule(TaskManager tm) {
        taskManager = tm;
    }

    public void run() {
        try {
            while (true) {
                Runnable task = taskManager.getTask();
                if (task != null) {
                    logger.info("++++ current task size:" + taskManager.getTaskNum() );
                    taskManager.runTask(task);
                } else {
                    sleep(1000);
                }
            }
        } catch (Exception e) {
            logger.error("Task schedule error!", e);
        }
    }
}
