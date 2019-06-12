package com.yl.pay.pos.access.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.threads.ThreadPool;

import java.util.concurrent.LinkedBlockingQueue;

public class TaskManager {
    /** Logger for this class */
    private static final Log logger = LogFactory.getLog(TaskManager.class);
 
    private LinkedBlockingQueue<Runnable> taskQueue;
    private boolean init = false;
    private ThreadPool tp;
    private int taskQueueSize;
    private int threadPoolMaxSize;
    
    private void init(int listSize,int threadSize,String managerName) {
        if (!init) {
         this.taskQueueSize = listSize;
         this.threadPoolMaxSize = threadSize;
            taskQueue = new LinkedBlockingQueue<Runnable>(taskQueueSize);
 
            tp = ThreadPool.createThreadPool(false);
            tp.setMaxSpareThreads(10);
            tp.setMinSpareThreads(10);
            tp.setMaxThreads(threadPoolMaxSize);
            tp.start();
 
            TaskSchedule ts = new TaskSchedule(this);
            ts.start();
            init = true;
            if(managerName!=null){
             logger.info("managerName="+managerName+" TaskManager init done.");
            }else{
             logger.info("TaskManager init done.");
            }
        }
    }
    //默认情况下，缓存1000个任务，线程池最多同时处理100个线程
    private void init() {
        init(1000,400,null);
    }
 
    public TaskManager() {
     init();
    }
    
    public TaskManager(int poolSize,int threadSize,String managerName) {
     init(poolSize,threadSize,managerName);
    }
 
    public void addTask(Runnable task) {
        int size = taskQueue.size();
        try {
            if (size > threadPoolMaxSize) {
                logger.info("Task list capacity is " + size + ",overload threadPoolMaxSize="+threadPoolMaxSize+", sleep 500ms. ");
                Thread.sleep(500);
            }
            taskQueue.put(task);
        } catch (InterruptedException e) {
            logger.error("Thread sleep error!", e);
        }
    }
 
    public int getTaskNum() {
        int size = taskQueue.size();
        return size;
    }
 
	public Runnable getTask() {
		Runnable task;
		try {
			task = (Runnable) taskQueue.take();
		} catch (InterruptedException e) {
			logger.info("", e);
			return null;
		}
        return task;
    }
 
    public void runTask(Runnable task) {
        tp.run(task);
    }
}
