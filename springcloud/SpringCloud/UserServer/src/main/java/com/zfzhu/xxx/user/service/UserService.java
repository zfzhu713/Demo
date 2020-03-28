/**
 * 
 */
package com.zfzhu.xxx.user.service;

import java.util.List;
import java.util.Map;

import com.zfzhu.xxx.user.entity.User;

/**
 * @author Think
 *
 */
public interface UserService {
	User findUser(Map<String, Object> params);
	
	List<User> listAll();
}
