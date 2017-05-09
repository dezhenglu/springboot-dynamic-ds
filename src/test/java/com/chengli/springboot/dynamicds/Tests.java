package com.chengli.springboot.dynamicds;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.chengli.springboot.dynamicds.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={MainConfig.class})
public class Tests {
	@Autowired
	@Qualifier("UserService")
	private UserService userService;

	@Test
	public void selectById() throws Exception {
		System.out.println(userService.selectById(1L));
	}
	
	@Test
	//@Rollback
	public void delete() throws Exception {
		userService.deleteById(2L);
	}
}