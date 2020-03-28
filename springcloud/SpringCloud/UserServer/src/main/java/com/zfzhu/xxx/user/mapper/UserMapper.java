/**
 * 
 */
package com.zfzhu.xxx.user.mapper;

import java.util.List;
import java.util.Map;

import com.zfzhu.xxx.user.entity.User;

/**
 * @author Think
 *
 */
public interface UserMapper {
	User findUser(Map<String, Object> params);
	List<User> listAll();
}
