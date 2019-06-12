package com.yl.trans.front.utils;


import fthink.utils.ByteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class TcpShortSocket {

	public static Logger logger = LoggerFactory.getLogger(TcpShortSocket.class);

	private String host;

	private int port;

	private int timeout;

	protected int headLength = 0;

	protected int recvBuffSize = 8192;

	protected Socket socket = null;

	protected InputStream is = null;

	protected OutputStream os = null;

	private int dataType;
	
	public int getHeadLength() {
		return headLength;
	}

	public void setHeadLength(int headLength) {
		this.headLength = headLength;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public TcpShortSocket(String host, int port, int timeout,int dataType,int headLength) {
		// TODO Auto-generated constructor stub
		this.host = host;
		this.port = port;
		this.timeout = timeout;
		this.dataType = dataType;
		this.headLength = headLength;
	}

	/**
	 * 获取socket连接
	 * 
	 * @return
	 * @throws Exception
	 */
	public void connection() throws Exception {

//		String localHost = ConfigProperties.getLocalHost();
		socket = new Socket();
//		socket.bind(new InetSocketAddress(localHost, localPort));
		socket.connect(new InetSocketAddress(host, port));
		socket.setSoTimeout(timeout * 1000);
		os = socket.getOutputStream();
		is = socket.getInputStream();
	}

	/**
	 * 发送数据
	 * @param msg
	 * @throws IOException
	 */
	public void send(byte[] msg) throws IOException {
		os.write(msg);
		os.flush();
	}
	
	/**
	 * 接收指定长度的报文头数据
	 * @param is
	 * @param length
	 * @return
	 * @throws IOException
	 */
	private byte[] recvBytes(InputStream is, int length) throws IOException {
		int tmpLength = 1024; // 每次读取最大缓冲区大小
		byte[] ret = new byte[length];
		int readed = 0, offset = 0, left = length;
		while (left > 0) {
			try {
				readed = is.read(ret, offset, Math.min(tmpLength, left));
				if (readed == -1)
					break;
			} finally {
				offset += readed;
				left -= readed;
			}
		}
		return ret;
	}

	/**
	 * 接收数据
	 * @return
	 * @throws IOException
	 */
	public byte[] recv() throws IOException {
		int iLen = 0;
		byte[] len = recvBytes(is, headLength);

		if (len == null) {
			logger.error("读取报文长度失败");
			return null;
		}
		if (dataType == 0) {
			//ASCII
			iLen = Integer.valueOf(new String(len));
		}if (dataType == 1) {
			//BCD
			iLen = Integer.valueOf(ByteUtil.bcd2str(len), 16);
		} else {
			logger.error("数据类型非法");
			return null;
		}
		byte[] bodyLen = null;
		bodyLen = recvBytes(is, iLen);
		byte[] retByte = new byte[iLen + headLength];
		System.arraycopy(len, 0, retByte, 0, len.length);
		System.arraycopy(bodyLen, 0, retByte, len.length, bodyLen.length);
		return retByte;
	}
	
	/**
	 * 关闭连接
	 * @throws IOException
	 */
	public void closeSocket() {
		if(is!=null){
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(os!=null){
			try {
				os.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(socket!=null){
			try {
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}


	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	public static void main(String[] args)  {
		try {
			TcpShortSocket shortSocket = new TcpShortSocket("127.0.0.1", 13800, 30, 1, 12);
			shortSocket.connection();
			shortSocket.send("test".getBytes());
			shortSocket.recv();
		} catch (SocketTimeoutException e) {
			// TODO: handle exception
			System.out.println("SocketTimeoutException");
		}catch (Exception e) {
			System.out.println("Exception");
		}
		
	}

}