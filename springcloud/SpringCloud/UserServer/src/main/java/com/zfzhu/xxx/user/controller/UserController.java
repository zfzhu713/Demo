/**
 * 
 */
package com.zfzhu.xxx.user.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zfzhu.xxx.user.entity.User;
import com.zfzhu.xxx.user.service.TestService;
import com.zfzhu.xxx.user.service.UserService;

/**
 * @author Think
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Value("xxx.secret")
	String secret;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private TestService testService;
	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/login")
    @ResponseBody
    public Object login(@RequestParam(value="username") String username, @RequestParam(value="password") String password){
    	Map<String, Object> params = new HashMap<>();
    	params.put("username", username);
    	params.put("password", password);
    	
        User user = userService.findUser(params);
        String userJson = "";
        String token = "";
        if (user!=null) {
        	long curTime = System.currentTimeMillis();
        	Date expiresAt = new Date(curTime+28800000);
        	
        	token = JWT.create().withAudience(user.getUsername()).withExpiresAt(expiresAt)
        			.sign(Algorithm.HMAC256(secret));
        	
        	ObjectMapper objectMapper = new ObjectMapper();
        	try {
				userJson = objectMapper.writeValueAsString(user);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
        	
        	redisTemplate.opsForValue().set("userSession@"+user.getUsername(), userJson);
        }
        
        Map<String, Object> res = new HashMap<>();
        res.put("code", 1001);
        res.put("token", token);
        res.put("data", user);
        
        return ResponseEntity.ok(res);
    }
    
    @RequestMapping("/list")
    @ResponseBody
    public Object list(){
    	PageHelper.startPage(1, 5);
    	List<User> list = userService.listAll();
    	PageInfo<User> pageInfo = new PageInfo<>(list);
    	
    	Map<String, Object> res = new HashMap<>();
    	res.put("code", 1001);
    	res.put("data", pageInfo);
    	
        return ResponseEntity.ok(res);
    }
    
    @RequestMapping("/test")
    @ResponseBody
    public Object test(){
    	PageHelper.startPage(1, 5);
    	List<Map<String, Object>> list = testService.listAll();
    	PageInfo<Map<String, Object>> pageInfo = new PageInfo<>(list);
    	
    	Map<String, Object> res = new HashMap<>();
    	res.put("code", 1001);
    	res.put("data", pageInfo);
    	
        return ResponseEntity.ok(res);
    }
}
