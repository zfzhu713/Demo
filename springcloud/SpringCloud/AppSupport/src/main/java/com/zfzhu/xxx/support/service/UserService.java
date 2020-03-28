/**
 * 
 */
package com.zfzhu.xxx.support.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.zfzhu.xxx.support.service.hystrix.UserServiceHystric;

/**
 * @author Think
 *
 */
@FeignClient(value = "user-server", fallback = UserServiceHystric.class)
public interface UserService {
	@RequestMapping(value = "/user/login")
	Object login(@RequestParam(value = "username") String username,
			@RequestParam(value = "password") String password);
	
	@RequestMapping(value = "/user/list")
	Object list();
}
