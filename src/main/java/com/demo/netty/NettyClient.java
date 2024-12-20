package com.demo.netty;


import com.demo.netty.param.RequestParam;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.AttributeKey;

import java.util.Scanner;

public class NettyClient {
	public static EventLoopGroup group=null;
	public static Bootstrap boostrap=null;
	static{
		 group = new NioEventLoopGroup();
		 boostrap = new Bootstrap();
		boostrap.channel(NioSocketChannel.class);
		boostrap.group(group);
		boostrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);
		boostrap.option(ChannelOption.SO_KEEPALIVE, true)
		             .handler(new ChannelInitializer<NioSocketChannel>() {
						@Override
						protected void initChannel(NioSocketChannel ch)
								throws Exception {
							ch.pipeline().addLast(new DelimiterBasedFrameDecoder
									(Integer.MAX_VALUE,Delimiters.lineDelimiter()[0]));
							ch.pipeline().addLast(new StringDecoder());
							ch.pipeline().addLast(new ClientHandler());
							ch.pipeline().addLast(new StringEncoder());
							
							
						}
					});
//		boostrap.option(ChannelOption.RCVBUF_ALLOCATOR,AdaptiveRecvByteBufAllocator.DEFAULT);
	}
	public static void main(String[] args) {
		
		try{
			ChannelFuture future = boostrap.connect("localhost", 8080).sync();

			future.channel().writeAndFlush(RequestParam.message("chat started."));
			future.channel().writeAndFlush("\r\n");
			Scanner scanner = new Scanner(System.in);
			while(scanner.hasNext()){
				String msg = scanner.nextLine();
				future.channel().writeAndFlush(RequestParam.message(msg));
				future.channel().writeAndFlush("\r\n");
			}
			
			future.channel().closeFuture().sync();
			
			Object result = future.channel().attr(AttributeKey.valueOf("ChannelKey")).get();
			System.out.println(result);
			
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			group.shutdownGracefully();
		}
		
		
		
	}

}
