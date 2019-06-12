package com.yl.receive.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * SFTP 工具栏
 * @author 聚合支付有限公司
 * @since 2016年4月12日
 * @version V1.0.0
 */
public class Sftp {
	
	/**
	 * 连接sftp服务器
	 * @param host 主机
	 * @param port 端口
	 * @param username 用户名
	 * @param password 密码
	 */
	public ChannelSftp connect(String host, int port, String username,
			String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			System.out.println("连接中...");
			sshSession.setPassword(password);
			Properties pro = new Properties();
			pro.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(pro);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
			System.out.println("Connected to " + host + ".");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sftp;
	}
	
	/**
	 * 上传文件
	 * @param directory 上传的目录
	 * @param uploadFile 要上传的文件
	 */
	public void upload(String directory, String uploadFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 下载文件
	 * @param directory 下载目录
	 * @param downloadFile 下载的文件
	 * @param saveFile 存在本地的路径
	 */
	public void download(String directory, String downloadFile,
			String saveFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			File file = new File(saveFile);
			sftp.get(downloadFile, new FileOutputStream(file));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * @param directory 要删除文件所在目录
	 * @param deleteFile 要删除的文件
	 */
	public void delete(String directory, String deleteFile, ChannelSftp sftp) {
		try {
			sftp.cd(directory);
			sftp.rm(deleteFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 * @param directory 要列出的目录
	 */
	@SuppressWarnings("rawtypes")
	public Vector listFiles(String directory, ChannelSftp sftp)
			throws SftpException {
		return sftp.ls(directory);
	}
}