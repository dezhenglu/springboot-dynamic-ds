package com.chengli.springboot.dynamicds.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.chengli.springboot.dynamicds.pojo.User;

@Mapper
public interface UserMapper {
	public List<User> list(); 
	User selectById(Long id);
	int deleteById(Long id);
}