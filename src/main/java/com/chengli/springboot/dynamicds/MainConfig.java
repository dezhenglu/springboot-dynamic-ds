package com.chengli.springboot.dynamicds;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chengli.springboot.dynamicds.pojo.User;
import com.chengli.springboot.dynamicds.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@RestController
@SpringBootApplication
public class MainConfig {
	@Autowired
	@Qualifier("UserService")
	private UserService userService;
	@Autowired
	@Qualifier("UserService1")
	private UserService userService1;

	public static void main(String[] args) {
		SpringApplication.run(MainConfig.class, args);
	}

	@RequestMapping("/get/user/{id}")
	public User getUserById(@PathVariable Long id) {
		return userService.selectById(id);
	}
	@RequestMapping("/get/user1/{id}")
	public User getUser1ById(@PathVariable Long id) {
		return userService1.selectById(id);
	}
	
	@RequestMapping("/query/{page}/{pageSize}")  
    public PageInfo query(@PathVariable Integer page, @PathVariable Integer pageSize) {  
        if(page!= null && pageSize!= null){  
            PageHelper.startPage(page, pageSize);  
        }  
        List<User> users = userService.list();  
        return new PageInfo(users);  
    }
	
}
