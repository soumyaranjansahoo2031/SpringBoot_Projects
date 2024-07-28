package com.example.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class CacheConfig {
	
	@Bean
	public RedisConnectionFactory getConnectionFactory() {
		RedisStandaloneConfiguration rsac = new RedisStandaloneConfiguration();
		LettuceConnectionFactory lcf = new LettuceConnectionFactory(rsac);
		
		return lcf;
	}
	
	@Bean
	public RedisTemplate<String, Object> getRedisTemplate(){
		RedisTemplate<String,Object> rt = new RedisTemplate<>();
		rt.setKeySerializer(new StringRedisSerializer());
		rt.setValueSerializer(new JdkSerializationRedisSerializer());
		
		rt.setConnectionFactory(getConnectionFactory());
		
		return rt;
	}
}
