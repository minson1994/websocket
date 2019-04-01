package com.example.demo.main.controller;


import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.demo.main.entity.User;
import com.example.demo.main.service.IUserService;
import com.example.demo.main.service.impl.UserServiceImpl;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author Minson.
 * @since 2019-03-21
 */
@RestController
public class UserController {
	@Resource
	private IUserService userService;
	
	/**
	 * 登陆操作
	 * @param user 
	 * @param login_type
	 * @return
	 */
	@RequestMapping("log/login")
	public String login(User user,Integer login_type) {		
		return userService.login(user, login_type);
	}
	
	
	
}
