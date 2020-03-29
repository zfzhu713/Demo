package com.zfzhu.xxx.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfzhu.xxx.user.entity.User;
import com.zfzhu.xxx.user.mapper.db1.UserMapper;
import com.zfzhu.xxx.user.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;

	@Override
	public User findUser(Map<String, Object> params) {
		return userMapper.findUser(params);
	}

	@Override
	public List<User> listAll() {
		return userMapper.listAll();
	}

}
