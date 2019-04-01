package com.example.demo.main.controller;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.main.service.ITokenService;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Minson.
 * @since 2019-03-21
 */
@RestController
public class TokenController {
	@Resource
	private ITokenService tokenService;
	
	/**
	 * 获取websocket连接密匙
	 * @param token 登陆密匙
	 * @param user_id  用户id
	 * @param login_type 登陆类型
	 * @return
	 */
	@RequestMapping("/user/getSocketToken")
	public  String getSocketToken(String token,Long user_id,Integer login_type) {
		return tokenService.getSocketToken(token, user_id, login_type);
	}
}
