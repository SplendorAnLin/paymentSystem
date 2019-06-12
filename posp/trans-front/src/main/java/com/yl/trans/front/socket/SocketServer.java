package com.yl.trans.front.socket;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketServer {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void bind(int port) throws Exception{
		//创建两个线程组
		EventLoopGroup bossGroup = new NioEventLoopGroup(); //用于服务端接收客户端的连接
		EventLoopGroup workerGroup = new NioEventLoopGroup();//用于进行SocketChannel的网络读写

		try {
			ServerBootstrap b = new ServerBootstrap(); //Netty用于启动Nio服务器的辅助启动类,目的是降低服务器端的开发复杂度

			b.group(bossGroup, workerGroup)
					.channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024) //连接数
					.option(ChannelOption.TCP_NODELAY, true)  //不延迟，消息立即发送
					.childHandler(new ChildChannelHandler());
			//绑定端口，同步等待成功
			ChannelFuture cf = b.bind(port).sync();
			logger.info("socket server start success! port : " + port);

			//等待服务器端监听端口关闭
			cf.channel().closeFuture().sync();

		} catch (Exception e) {
			logger.error("", e);
		} finally {
			//优雅退出，释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
	private class ChildChannelHandler extends ChannelInitializer<SocketChannel> {
		
		@Override
		protected void initChannel(SocketChannel arg0) throws Exception {
			arg0.pipeline().addLast(new SocketServerHandler());
		}
		
	}
}
