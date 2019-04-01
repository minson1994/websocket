package com.example.demo.websocket;

import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.util.ResultData;
import com.example.demo.util.UtilClass;
import com.google.gson.Gson;

/**
 * websocket消息处理
 * @author Minson.
 * @date 2019年1月31日下午9:24:53
 */
@ServerEndpoint(value = "/ISsocket/{user_id}/{socket_token}/{login_type}",configurator=GetHttpSessionConfigurator.class)
@Component
public class WebSocketServer {
	static Log log=LogFactory.getLog(WebSocketServer.class);
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<WebSocketServer>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //接收user_id
    private Long uid=null;
    
    
    /**
     * 连接建立成功调用的方法
     * @param session
     * @param config 可以获取当前的httpsession，
     * @param user_id 用户id
     * @param socket_token 连接密匙
     * @param login_type 登陆类型
     * @throws Exception
     */
    @OnOpen
    public void onOpen(Session session,EndpointConfig config,
    		@PathParam("user_id") Long user_id,
    		@PathParam("socket_token")String socket_token,
    		@PathParam("login_type")Integer login_type
    		) throws Exception {
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1
        log.info("有新窗口开始监听:"+user_id+",当前在线人数为" + getOnlineCount());  
        ResultData resultData=UtilClass.cheToken(socket_token, user_id, login_type, 2);//校验秘钥
        if(resultData.getFlag()==0) {
        	throw new Exception("未授权连接");
        }else {
        	this.session = session;
        	this.uid=user_id;
            try {
               Message message=new Message();
               message.setMsg("连接成功");
               message.setTo_user_id(user_id);
               message.setMsg_secne("none");
               message.setMsg_type("text");
           	   sendMessage(JSON.toJSONString(message));
            } catch (IOException e) {
               log.error("websocket IO异常");
            }  
        }
        
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
		 webSocketSet.remove(this);  //从set中删除
	     subOnlineCount();           //在线数减1
	     log.info("有一连接关闭！当前在线人数为" + getOnlineCount());	  	
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session) {
    	log.info("收到来自窗口"+uid+"的信息:"+message);
    	Gson gson = new Gson();
    	Message message2=gson.fromJson(message, Message.class);
    	message2.setMsg_secne("p2p");
    	//Message message2=JSONObject.toJavaObject(JSONObject.parseObject(message),Message.class);
        for (WebSocketServer item : webSocketSet) {
        	try {
	        	if(message2.getTo_user_id()==null) {
	        		item.sendMessage(JSON.toJSONString(message2));
	        	}else {
                	if(item.uid.equals(message2.getTo_user_id())) {
                		item.sendMessage(JSON.toJSONString(message2));
                		break;
                	}    
	        	}
        	} catch (IOException e) {
                e.printStackTrace();
            }
            
        }
    }

	/**
	 * 
	 * @param session
	 * @param error
	 */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误");
        error.printStackTrace();
    }
	/**
	 * 实现服务器主动推送
	 */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 群发自定义消息
     * */
    public static int sendInfo(String message,@PathParam("sid") String sid) throws IOException {
    	log.info("推送消息到窗口"+sid+"，推送内容:"+message);
    	int n=0;
    	
        for (WebSocketServer item : webSocketSet) {
            try {
            	//这里可以设定只推送给这个sid的，为null则全部推送
            	if(sid==null) {
            		item.sendMessage(message);
            		n=1;
            	}else if(item.uid.equals(sid)){
            		item.sendMessage(message);
            		n=1;
            		break;
            	}
            } catch (IOException e) {
                continue;
            }
        }
        return n;
    }
    

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
