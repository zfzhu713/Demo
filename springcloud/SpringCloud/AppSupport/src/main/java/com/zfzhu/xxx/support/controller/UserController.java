/**
 * 
 */
package com.zfzhu.xxx.support.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

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
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zfzhu.xxx.support.service.UserService;

/**
 * @author Think
 *
 */
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Value("zfzhu.xxx.secret")
	String secret;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private RedisTemplate<String, String> redisTemplate;

    @RequestMapping("/login")
    @ResponseBody
    public Object login(@RequestParam(value="username") String username, @RequestParam(value="password") String password){
    	
        Object user = userService.login(username, password);
        return ResponseEntity.ok(user);
    }
    
    @RequestMapping("/list")
    @ResponseBody
    public Object list(HttpServletRequest request){
    	String token = request.getParameter("token");
    	
    	JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
		DecodedJWT decodedJWT = jwtVerifier.verify(token);
		String name =decodedJWT.getAudience().get(0);
    	
    	String userJson = redisTemplate.opsForValue().get("userSession@"+name);
    	System.err.print(userJson);
    	
    	Object data = userService.list();
    	
        return ResponseEntity.ok(data);
    }
}
