package com.example.demo.main.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.demo.main.entity.Token;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Minson.
 * @since 2019-03-21
 */
public interface ITokenService extends IService<Token> {
	/**
	 * 获取websocket连接密匙
	 * @param token 登陆密匙
	 * @param user_id  用户id
	 * @param login_type 登陆类型
	 * @return
	 */
	public  String getSocketToken(String token,Long user_id,Integer login_type);
}
