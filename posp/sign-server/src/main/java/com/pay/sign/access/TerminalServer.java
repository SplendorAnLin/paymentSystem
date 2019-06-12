package com.pay.sign.access;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.pay.common.util.DateUtil;
import com.pay.common.util.PropertyUtil;
import com.pay.sign.access.task.TaskManager;

/**
 * Title: 终端交易服务入口
 * @author zhongxiang.ren
 */
@Component
public class TerminalServer {
	private Log log = LogFactory.getLog(TerminalServer.class);

	// 服务器端口号
	private static int port ;
	private static int timeout ;

	private ServerSocket serverSocket = null;

	private TaskManager taskManager = new TaskManager();
	private TaskManager handleTaskManager = new TaskManager();

	private static int MAXLENGTH = 2048;
	@Resource
	private TerminalProcessService terminalProcessService;	

	private boolean stopFlag = false;
	
	private static PropertyUtil propertyUtil =null;
	static{
		propertyUtil = PropertyUtil.getInstance("signConfig");
		port=Integer.parseInt(propertyUtil.getProperty("TerminalServerPort"));
		timeout = Integer.parseInt(propertyUtil.getProperty("dataTimeout"));
	}

	public TerminalServer() {
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
					socket = serverSocket.accept();	
					log.info("#####  add task on: " + DateUtil.formatDate(new Date()));
					PosTask task = new PosTask(socket);					
					taskManager.addTask(task);	// 添加新任务
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
	
	private class SocketHandle implements Runnable{
		private byte[] io;
		private OutputStream out;
		private InputStream in;
		private Socket socket;
		
		public SocketHandle(byte[] io,Socket socket,InputStream in){
			this.io=io;
			this.in=in;
			this.socket=socket;
		}
		
		public void run() {
			if(io==null||socket==null){
				return;
			}
			try {
				out = socket.getOutputStream();
				log.info("#####   trx handle start [ " + DateUtil.formatDate(new Date()) + " ]  #####");
				long start = System.currentTimeMillis();				
				
				byte[] trxResult = terminalProcessService.execute(io);
				
				out.write(trxResult);	// 向POS返回报文 字节流
				out.flush();
				
				long useTime = (System.currentTimeMillis()-start);
				
				log.info("#####   trx handle end [ " +  DateUtil.formatDate(new Date()) + ", use time:" + useTime + "ms ] #####\n");
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				try {
					if (out != null){
						out.close();
					}	
					if (in != null){
						in.close();
					}	
					if (socket != null && ! socket.isClosed()){
						socket.close();
					}
					log.info("++++++++++++socket closed. +++++++");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}

	private class PosTask implements Runnable {
		private Socket socket;

		public PosTask(Socket socket) {
			super();
			this.socket = socket;
		}

		public void run() {
			if (this.socket == null) {
				return;
			}
			
			InputStream in = null;
			boolean flag = true ;
			
			while(flag){
				try {
					if(socket.isClosed()){
						if(in!=null){
							in.close();
						}
						break;
					}
					socket.setSoTimeout(timeout);
					in = socket.getInputStream();
					byte[] posInput = new byte[MAXLENGTH];
					int realLength = in.read(posInput);
					
					if(realLength==0){
						log.info("++++++++++++ No data received ++++++");
						continue;
					}else if(realLength<0){
						log.info("++++++++++++ all socket data has been received ++++++");
						if (in != null)   
							in.close();
						if (socket != null && ! socket.isClosed())
							socket.close();
						log.info("++++++++++++socket closed +++++++");
						break;
					}
					//认为数据已经读完
					else if(realLength<MAXLENGTH){
						flag = false ;
					}
					
					byte[] realInput = new byte[realLength];
					System.arraycopy(posInput, 0, realInput, 0, realLength);
					
					log.info("+++++++++++  add socketRequest on: " + DateUtil.formatDate(new Date()));
					SocketHandle sh = new SocketHandle(realInput,socket,in);
					handleTaskManager.addTask(sh);
					
				}catch (Exception e2){
					try {
						in.close();
						socket.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					e2.printStackTrace();
					break;
				}
			}
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
