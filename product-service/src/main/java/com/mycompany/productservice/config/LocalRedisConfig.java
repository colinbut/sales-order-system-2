package com.mycompany.productservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@Configuration
@EnableRedisRepositories
/*
 * Technically local profile is not required as Spring Boot by default will always fallback to 'default' profile
 * which will internally connect to Redis @ localhost:6379
 */
@Profile("local")
public class LocalRedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory(){
        /*
         * Technically this configuration is not required because by default Spring Boot is auto-configured to connect to
         * Redis at 'localhost:6379'
         */
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration("localhost", 6379));
    }

    @Bean
    public RedisTemplate<?, ?> redisTemplate(){
        RedisTemplate<byte[], byte[]> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }
}
