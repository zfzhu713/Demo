/**
 * 
 */
package com.zfzhu.xxx.support.controller;

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
import com.zfzhu.xxx.support.service.UserService;

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
    	String token = request.getHeader("token");
    	
    	JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
		DecodedJWT decodedJWT = jwtVerifier.verify(token);
		String username =decodedJWT.getAudience().get(0);
    	
    	String userJson = redisTemplate.opsForValue().get("userSession@"+username);
    	System.err.print(userJson);
    	
    	Object data = userService.list();
    	
        return ResponseEntity.ok(data);
    }
}
