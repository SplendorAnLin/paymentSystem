package com.yl.pay.pos.access;

import com.pay.common.util.DateUtil;
import com.pay.common.util.PropertyUtil;
import com.yl.pay.pos.access.task.TaskManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Title: POS交易服务入口
 * Description: 
 * Copyright: Copyright (c)2011
 * Company: com.yl.pay
 * @author jun.yu
 */

public class PosServer {
	private Log log = LogFactory.getLog(PosServer.class);

	// 服务器端口号
	private static int port ;

	private ServerSocket serverSocket = null;

	private TaskManager taskManager = new TaskManager();

	private static int MAXLENGTH = 1024;

	private TransactionProcessService transactionProcessService;	

	private boolean stopFlag = false;
	private static AtomicInteger currentConcurrentCount = new AtomicInteger(0);
	private static PropertyUtil propertyUtil =null;
	static{
		propertyUtil = PropertyUtil.getInstance("transConfig");
		port=Integer.parseInt(propertyUtil.getProperty("PosServerPort"));
	}


	public PosServer(TransactionProcessService transactionProcessService) {
		
		this.transactionProcessService = transactionProcessService;
		Thread monitor = new Thread(new Runnable() {
			public void run() {
				log.info("############## pos server auto start on(" + port + "): " + DateUtil.formatDate(new Date()) + "  ##############");
				startService();
			}
		});
		this.stopFlag = true;
		monitor.start();		
	}

	private void startService() {
		
		Socket socket = null;

		if (serverSocket == null) {
			try {
				serverSocket = new ServerSocket(port);
				log.info("#####  PosServer starting ....");
				
				while (this.stopFlag) {
					try {
						socket = serverSocket.accept();					
						log.info("#####  add task on: " + DateUtil.formatDate(new Date()));
						PosTask task = new PosTask(socket);					
						taskManager.addTask(task);	// 添加新任务
					} catch (Exception e) {
						log.info("create new socket fail",e);
					}
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (serverSocket != null) {
					try {
						serverSocket.close();
					} catch (IOException e) {
						log.info("PosServer socketServer close failed! ", e);
					}
				}

			}
		}
	}

	private class PosTask implements Runnable {
		private Socket socket;
		
		public PosTask(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			if (this.socket == null) {
				return;
			}
			int count = currentConcurrentCount.incrementAndGet();
			log.info("current concurrent count:"+count);
			Thread.currentThread().setName("pos-"+Thread.currentThread().getId()+"-"+Long.toString(System.currentTimeMillis()).substring(4));
			
			InputStream io = null;
			OutputStream out = null;
			
			while(true){
				try {
					if(socket.isClosed()){
						if(io!=null){
							io.close();
						}
						break;
					}
					io = socket.getInputStream();
					byte[] posInput = new byte[MAXLENGTH];
					int realLength = io.read(posInput);
					
					if(realLength==0){
						log.info("++++++++++++ No data received ++++++");
						continue;
					}else if(realLength<0){
						log.info("++++++++++++ receive data too little ++++++");
						break;
					}
					
					byte[] realInput = new byte[realLength];
					System.arraycopy(posInput, 0, realInput, 0, realLength);
					
					out = socket.getOutputStream();
					if(io==null||out==null){
						break;
					}
					
					try {
						log.info("#####   trx handle start [ " + DateUtil.formatDate(new Date()) + " ]  #####");
						long start = System.currentTimeMillis();				
						
						byte[] trxResult = transactionProcessService.execute(realInput);
						
						out.write(trxResult);	// 向POS返回报文 字节流
						out.flush();

						long useTime = (System.currentTimeMillis()-start);
						
						log.info("#####   trx handle end [ " +  DateUtil.formatDate(new Date()) + ", use time:" + useTime + "ms ] #####\n");
					} catch (Exception e) {
						e.printStackTrace();
					} 
				} catch (SocketException e1){
					e1.printStackTrace();
					break;
				}catch (Exception e2){
					e2.printStackTrace();
					break;
				}
			}
			
			try {
				if (io != null){io.close();}	
				if (out != null){out.close();}	
				if (socket != null){socket.close();}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			currentConcurrentCount.decrementAndGet();
			log.info("++++++++++++socket closed +++++++");

		}
	}

	public void startServer() {
		if (!stopFlag) {
			this.stopFlag = true;
			startService();
		}
	}

	public void stopServer() {
		this.stopFlag = false;
	}


}
