/**
 * 
 */
package com.zfzhu.xxx.security.filter;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * @author Think
 *
 */
@Component
public class AccessTokenFilter extends ZuulFilter {

	@Value("xxx.secret")
	String secret;
	
	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		
		String token = request.getHeader("token");
		boolean valid = false;
		
		if (!StringUtils.isEmpty(token)) {
			JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(secret)).build();
			DecodedJWT decodedJWT = jwtVerifier.verify(token);
			Date expires =decodedJWT.getExpiresAt();
			if (expires.after(new Date())) {
				valid = true;
			}
		}
		
		if (!valid) {
			ctx.setSendZuulResponse(false);
			ctx.setResponseStatusCode(401);
			ctx.setResponseBody("please login first");
		}
		
		return null;
	}

	@Override
	public boolean shouldFilter() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		String path = request.getServletPath();
		if (path.startsWith("/api/user/login")) {
			return false;
		}
		return true;
	}

	@Override
	public int filterOrder() {
		return 0;
	}

	@Override
	public String filterType() {
		return "pre";
	}

}
