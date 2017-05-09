package com.chengli.springboot.dynamicds.service;

import java.util.List;

import com.chengli.springboot.dynamicds.pojo.User;

public interface UserService {
	public List<User> list(); 
	User selectById(Long id);
	void deleteById(Long id);
}
