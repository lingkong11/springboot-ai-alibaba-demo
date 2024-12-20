package com.demo.netty;

import com.alibaba.fastjson.JSONObject;
import com.demo.netty.param.Response;
import com.demo.netty.core.DefaultFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class ClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
	
		if(msg.toString().equals("ping")){
			ctx.channel().writeAndFlush("ping\r\n");
			return ;
		}
//		ctx.channel().attr(AttributeKey.valueOf("ChannelKey")).set(msg.toString());
	
		System.out.println("客户端返回数据==="+msg.toString());
		Response res = JSONObject.parseObject(msg.toString(), Response.class);
		DefaultFuture.recive(res);
//		ctx.channel().close();
	}
	
	

}
