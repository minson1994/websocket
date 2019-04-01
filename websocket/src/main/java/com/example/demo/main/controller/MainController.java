package com.example.demo.main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
public class MainController {
	/**
	 * 聊天页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/")
	public String index(Model model) {
		model.addAttribute("u_body", "/pro/main/u_body.html");
		model.addAttribute("f_body", "/pro/main/f_body.html");
		model.addAttribute("chat", "/pro/main/chat.html");
		model.addAttribute("js", "/pro/main/js.html");
		model.addAttribute("css", "/pro/main/css.html");
		return "main.html";
	}

	
	/**
	 * 进入异常页面
	 * @param msg
	 * @param model
	 * @return
	 */
	@RequestMapping("/main/error")
	public String errorPage(String msg,Model model) {
		model.addAttribute("msg",msg);
		return "error.html";
	}
}
