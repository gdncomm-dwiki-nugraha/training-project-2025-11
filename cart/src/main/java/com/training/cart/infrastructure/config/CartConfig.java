package com.training.cart.infrastructure.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.training.cart.application.usecase.AddItemToCartUseCase;
import com.training.cart.application.usecase.GetCartUseCase;
import com.training.cart.application.usecase.RemoveItemFromCartUseCase;
import com.training.cart.domain.repository.CartRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * CartConfig - Infrastructure configuration for Cart service.
 * Wires domain, application layer components and Redis.
 */
@Configuration
public class CartConfig {

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new StringRedisSerializer());
        return template;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    @Bean
    public AddItemToCartUseCase addItemToCartUseCase(CartRepository cartRepository) {
        return new AddItemToCartUseCase(cartRepository);
    }

    @Bean
    public RemoveItemFromCartUseCase removeItemFromCartUseCase(CartRepository cartRepository) {
        return new RemoveItemFromCartUseCase(cartRepository);
    }

    @Bean
    public GetCartUseCase getCartUseCase(CartRepository cartRepository) {
        return new GetCartUseCase(cartRepository);
    }
}
