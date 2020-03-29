package com.zfzhu.xxx.user.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zfzhu.xxx.user.mapper.db2.TestMapper;
import com.zfzhu.xxx.user.service.TestService;

@Service("testService")
public class TestServiceImpl implements TestService {
	@Autowired
	private TestMapper testMapper;

	@Override
	public List<Map<String, Object>> listAll() {
		return testMapper.listAll();
	}

}
