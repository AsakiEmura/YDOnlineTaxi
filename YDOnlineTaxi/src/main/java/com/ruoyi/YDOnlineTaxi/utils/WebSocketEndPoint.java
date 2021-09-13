package com.ruoyi.YDOnlineTaxi.utils;


import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//对外公布的一个后端站点
//ws://localhost:8080/websocket/用户id
@ServerEndpoint(value = "/websocket/{userId}")
@Component
public class WebSocketEndPoint {
    //与某个客户端的连接会话，需要他来给客户端发送数据
    private Session session;

    //连接建立成功调用的方法
    @OnOpen
    public void onOpen(Session session, @PathParam("userId") String userId) {
        //把会话加入连接池中
        //userId通过用户传入，session是系统自动产生
        SessionPool.sessions.put(userId, session);
    }

    //关闭会话的时候
    @OnClose
    public void onClose(Session session) throws IOException {
        SessionPool.close(session.getId());
        session.close();
    }

    //接收客户端的消息后调用的方法
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(message);
        //心跳检测
        if (message.equalsIgnoreCase("ping")) {
            try {
                Map<String, Object> params = new HashMap<>();
                params.put("type", "pong");
                session.getBasicRemote().sendText(JSON.toJSONString(params));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return;
        }
        //其他情况
        //将Json字符串转为键值对
//        Map<String, Object> params = JSON.parseObject(message, new HashMap<String, Object>().getClass());
//        SessionPool.sendMessage(params);
        SessionPool.sendMessage(message);
    }
    //心跳检测：用于服务器断开后进行感知，感知是否存活
}
