package com.example.springappnewssecure.configuration;

import com.example.springappnewssecure.applicationparametr.RedisConnect;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@RequiredArgsConstructor
public class RedisReactiveConfiguration {
    private final RedisConnect redisConnect;
    @Bean
    @Primary
    public ReactiveRedisConnectionFactory reactiveRedisConnectionFactory() {
        return new LettuceConnectionFactory(redisConnect.getHost(), redisConnect.getPort());
    }

    @Bean
    public ReactiveRedisTemplate<Long, String> reactiveRedisTemplate(ReactiveRedisConnectionFactory factory) {
        GenericToStringSerializer<Long> keySerializer = new GenericToStringSerializer<>(Long.class);
        StringRedisSerializer valueSerializer = new StringRedisSerializer();
        RedisSerializationContext.RedisSerializationContextBuilder<Long, String> builder =
                RedisSerializationContext.newSerializationContext(keySerializer);
        RedisSerializationContext<Long, String> context =
                builder.value(valueSerializer).build();
        return new ReactiveRedisTemplate<>(factory, context);
    }
}
