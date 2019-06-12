package com.yl.trans.front.socket;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.yl.trans.front.utils.ConfigProperties;
import com.yl.trans.front.utils.TcpShortSocket;
import fthink.utils.ByteUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketServerHandler extends ChannelHandlerAdapter {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()]; // 获取缓冲区可读的字节数
		buf.readBytes(req); // 将缓冲区的字节复制到新的字节数组中
		logger.info("请求报文："+ByteUtil.bcd2str(req));
		String host = ConfigProperties.getProperty("trans.core.ip");
		int port = Integer.valueOf(ConfigProperties.getProperty("trans.core.port"));
		int timeout = Integer.valueOf(ConfigProperties.getProperty("trans.core.connect.timeout"));
		TcpShortSocket socket = new TcpShortSocket(host, port, timeout, 1, 2);
		
		byte[] recMsg = null;
		socket.connection();
		socket.send(req);
		recMsg = socket.recv();
		logger.info("响应报文："+ByteUtil.bcd2str(recMsg));
		ByteBuf resp = Unpooled.copiedBuffer(recMsg);
		
		ctx.write(resp);  //异步发送应答消息给客户端
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		//当发生异常时，关闭ChannelHandlerContext,释放与ChannelHandlerContext相关联的句柄等资源
		ctx.close(); 
	}

	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();  //将消息发送队列中的消息写入到SocketChannel中给发送方
	}
	
}
