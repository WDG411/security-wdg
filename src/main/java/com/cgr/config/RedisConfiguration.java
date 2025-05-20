package com.cgr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        // 设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // key采用 StringRedisSerializer 类序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        // value采用 Jackson2JsonRedisSerializer 类序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        //  hashKey采用 StringRedisSerializer 类序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        // hashValue采用 Jackson2JsonRedisSerializer 类序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }
}
