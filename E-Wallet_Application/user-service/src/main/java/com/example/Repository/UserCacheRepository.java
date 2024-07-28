package com.example.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.example.model.User;

@Repository
public class UserCacheRepository {
	
	private static final String USER_KEY_PREFIX = "usr::";
	
	private static final Long USER_KEY_EXPIRY = 1l;
	
	@Autowired
	RedisTemplate<String, Object> rt;
	
	public void save(User user) {
		rt.opsForValue().set( getKey(user.getId()), user);
	}
	
	public User get(Integer userId) {
		return (User)rt.opsForValue().get(getKey(userId));
	}
	
	private String getKey(Integer userId) {
		return USER_KEY_PREFIX + userId;
	}
}
