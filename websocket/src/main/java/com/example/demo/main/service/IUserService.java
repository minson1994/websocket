package com.example.demo.main.service;

import com.example.demo.main.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Minson.
 * @since 2019-03-21
 */
public interface IUserService extends IService<User> {
	
	/**
	 * 登陆操作
	 * @param user 
	 * @param login_type
	 * @return
	 */
	public String login(User user,Integer login_type) ;
}
