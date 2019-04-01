package com.example.demo.websocket;

import javax.servlet.http.HttpSession;
import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;
import javax.websocket.server.ServerEndpointConfig.Configurator;


/**
 * websocket带上httpsession
 * @author Minson.
 * @date 2019年1月31日下午9:24:04
 */
public class GetHttpSessionConfigurator extends Configurator{
	
	 /* 修改握手,就是在握手协议建立之前修改其中携带的内容 */
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        super.modifyHandshake(sec, request, response);
        HttpSession httpSession = (HttpSession) request.getHttpSession();
        //sec.getUserProperties().put("sessionId", httpSession);
    }
}
