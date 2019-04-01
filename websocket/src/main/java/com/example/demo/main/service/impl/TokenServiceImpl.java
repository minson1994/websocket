package com.example.demo.main.service.impl;

import com.example.demo.main.entity.Token;
import com.example.demo.main.mapper.TokenMapper;
import com.example.demo.main.service.ITokenService;
import com.example.demo.util.ResultData;
import com.example.demo.util.UtilClass;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Minson.
 * @since 2019-03-21
 */
@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements ITokenService {
	@Resource
	private TokenMapper tokenMapper;
	@Resource
	private RedisTemplate<String, String> rt;//缓存
	
	/**
	 * 获取websocket连接密匙
	 * @param token 登陆密匙
	 * @param user_id  用户id
	 * @param login_type 登陆类型
	 * @return
	 */
	public  String getSocketToken(String token,Long user_id,Integer login_type) {
		String socket_token=UUID.randomUUID().toString();
		String key=UtilClass.getTokenName(login_type, user_id, 2);//拼接key
		rt.opsForValue().set(key, socket_token,60, TimeUnit.SECONDS);//密匙连接有效期60s
		ResultData resultData=new ResultData(1,"成功",socket_token);
		return JSON.toJSONString(resultData);
	}
}
