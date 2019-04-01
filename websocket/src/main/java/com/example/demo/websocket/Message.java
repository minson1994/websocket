package com.example.demo.websocket;

import java.util.Date;

import com.example.demo.main.entity.User;

import lombok.Data;

@Data
public class Message {
	/**
	 * 消息
	 */
	private String msg;
	/**
	 * 时间
	 */
	private Date time;
	
	/**
	 * 接收用户
	 */
	private Long to_user_id;
	
	/**
	 * 发送用户
	 */
	private Long send_user_id;
	
	/**
	 * 消息类型：文字、文件、图片
	 */
	private String msg_type;
	
	/**
	 * 消息场景：个人、群聊、系统、添加好友、群、或其他通知、隐形通知
	 */
	private String msg_secne;
	
	/**
	 * 群聊Id
	 */
	private Long chatId;
	
	/**
	 * 发送用户对象
	 */
	private User send_user;
	
	
}
