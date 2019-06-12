package com.yl.trans.front.socket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketThread extends Thread{
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private int port;

	public SocketThread() {
		super();
	}

	public SocketThread(int port) {
		this.port = port;
	}
	@Override
	public void run() {
		SocketServer ss = new SocketServer();
		try {
			ss.bind(port);
		} catch (Exception e) {
			logger.error("系统异常：",e);
		}
	}
}
