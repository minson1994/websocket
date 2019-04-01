package com.example.demo.util;

import java.math.BigInteger;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.redis.core.RedisTemplate;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;

public class UtilClass {
	/**
	 * 用户token名称公共部分
	 */
	public final static String USER_TOKEN="utoken_";
	/**
	 * 用户token名称公共部分
	 */
	public final static String SOCKET_TOKEN="stoken_";

	/**
	 * 获取请求用户ip
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ipAddress = null;
		try {
			ipAddress = request.getHeader("x-forwarded-for");
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
				ipAddress = request.getRemoteAddr();
				if (ipAddress.equals("127.0.0.1")) {
					// 根据网卡取本机配置的IP
					InetAddress inet = null;
					try {
						inet = InetAddress.getLocalHost();
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					ipAddress = inet.getHostAddress();
				}
			}
			// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
			if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
																// = 15
				if (ipAddress.indexOf(",") > 0) {
					ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
				}
			}
		} catch (Exception e) {
			ipAddress = "";
		}
		// ipAddress = this.getRequest().getRemoteAddr();

		return ipAddress;
	}

	/**
	 * md5加密
	 * 
	 * @param str
	 * @return
	 */
	public static String getMD5String(String str) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("md5");
			md.update(str.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * md5加密 16位
	 * 
	 * @param str 需要加密的字符串
	 * @return 已加密的字符串
	 */
	public static String getMD5String16(String str) {
		return getMD5String(str).substring(8, 24);
	}
	
	/**
	 * 拼接密匙名称
	 * @param type 登陆类型
	 * @param user_id 用户id
	 * @param flag 场景标记
	 * @return
	 */
	public static String getTokenName(int type,Long user_id,int flag) {
		String name=null;
		switch (flag) {
		case 1:
			name=USER_TOKEN;
			break;
		case 2:
			name=SOCKET_TOKEN;
			break;
		default:
			name=USER_TOKEN;
			break;
		}
		StringBuffer user_token_name=new StringBuffer(name);
		user_token_name.append(type==1?"app_":"web_");
		user_token_name.append(user_id);
		return user_token_name.toString();
	}
	
	/**
	 * 校验token
	 * @param rt 缓存模板
	 * @param token token
	 * @param user_id 用户id
	 * @param login_type 登陆类型
	 * @param flag 校验类型
	 * @return
	 */
	public static ResultData cheToken(String token,Long user_id,Integer login_type,Integer flag) {
		ResultData resultData=new ResultData(0,"token已过期");
		if(token==null||user_id==null||login_type==null) {
			resultData.setMsg("参数缺失");
			return resultData;
		}
		Jedis jedis=getJedis();
		String key=getTokenName(login_type, user_id, flag);
		String r_token=jedis.get(key);
		if(r_token!=null) {
			if (token.equals(r_token)) {
				resultData.setFlag(1);
				resultData.setMsg("校验通过");
			}else resultData.setMsg("token不匹配");
		}
		jedis.close();
		return resultData;
	}
	
	/** 
	 * 返回缓存操作对象
	 * @return
	 */
	public static Jedis getJedis() {
		Jedis jedis = new Jedis("127.0.0.1", 6379);
		//jedis.auth("");//密码
		return jedis;
	}
}
