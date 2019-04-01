package com.example.demo.util;

import java.io.Serializable;

import lombok.Data;

@Data
public class ResultData implements Serializable{
	private static final long serialVersionUID = 4144739726803459152L;
	private String msg;
	private int flag;
	private Object data;
	
	public ResultData() {}
	
	public ResultData(int flag){
		this.flag=flag;
	}
	
	public ResultData(int flag,String msg){
		this.flag=flag;
		this.msg=msg;
	}

	public ResultData(int flag,String msg,Object data){
		this.flag=flag;
		this.msg=msg;
		this.data=data;
	}
	
}
