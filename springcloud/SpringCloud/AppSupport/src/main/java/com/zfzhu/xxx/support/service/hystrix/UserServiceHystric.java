package com.zfzhu.xxx.support.service.hystrix;

import org.springframework.stereotype.Component;

import com.zfzhu.xxx.support.service.UserService;

@Component
public class UserServiceHystric implements UserService {

	@Override
	public Object login(String username, String password) {
		return "sorry, "+username;
	}

	@Override
	public Object list() {
		return "/user/list service not active";
	}

}
