package com.example.demo.main.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.demo.main.entity.User;
import com.example.demo.main.mapper.UserMapper;
import com.example.demo.main.service.IUserService;
import com.example.demo.util.ResultData;
import com.example.demo.util.UtilClass;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Minson.
 * @since 2019-03-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
	@Resource
	private UserMapper userMapper;
	@Resource
	private RedisTemplate<String, String> rt;//缓存
	
	/**
	 * 登陆操作
	 * @param user 
	 * @param login_type
	 * @return
	 */
	public String login(User user,Integer login_type) {
		ResultData resultData=new ResultData(0);
		QueryWrapper<User> qw=new QueryWrapper<User>();
		qw.eq("user_code", user.getUser_code());
		User u=userMapper.selectOne(qw);
		if(u!=null) {
			System.out.println(u);
			if(u.getUser_password().equals(UtilClass.getMD5String(user.getUser_password()))) {
				u.setUser_password(null);
				String token=UUID.randomUUID().toString();//生成token
				String key=UtilClass.getTokenName(login_type, u.getUser_id(),1);
				rt.opsForValue().set(key, token,login_type==1?2592000:43200, TimeUnit.SECONDS);//app登陆缓存一个月，web为12小时
	            JSONObject jo=new JSONObject();
	            jo.put("token", token);
	            jo.put("type", login_type==1?1:2);
	            jo.put("user", u);
	            resultData.setFlag(1);
	            resultData.setMsg("成功");
	            resultData.setData(jo);
			}else resultData.setMsg("密码错误");
		}else resultData.setMsg("用户不存在");
		return JSON.toJSONString(resultData);
	}
}
