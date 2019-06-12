package com.yl.pay.pos.access.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * Title: 任务执行与调度 
 * Description: 
 * Copyright: Copyright (c)20111
 * Company: afc
 * @author Administrator
 *
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
                    logger.debug("Ready to run task [" + task + "].");
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
