package com.chengli.springboot.dynamicds.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.chengli.springboot.dynamicds.dao.UserMapper;
import com.chengli.springboot.dynamicds.dynmic.UseDataSource;
import com.chengli.springboot.dynamicds.pojo.User;
import com.chengli.springboot.dynamicds.service.UserService;

@Service("UserService1")
public class User1ServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	@UseDataSource("springbootDataSource1")
	public User selectById(Long id) {
		return userMapper.selectById(id);
	}

	@Override
	@Transactional
	public void deleteById(Long id) {
		userMapper.deleteById(id);
	}

	@Override
	public List<User> list() {
		return userMapper.list();
	}

}
