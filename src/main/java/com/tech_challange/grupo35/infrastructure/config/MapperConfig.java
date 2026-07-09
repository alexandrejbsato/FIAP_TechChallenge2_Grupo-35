package com.tech_challange.grupo35.infrastructure.config;

import com.tech_challange.grupo35.application.mapper.AddressMapper;
import com.tech_challange.grupo35.application.mapper.MenuItemMapper;
import com.tech_challange.grupo35.application.mapper.RestaurantMapper;
import com.tech_challange.grupo35.application.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public AddressMapper addressMapper() {
        return new AddressMapper();
    }

    @Bean
    public UserMapper userMapper(AddressMapper addressMapper) {
        return new UserMapper(addressMapper);
    }

    @Bean
    public RestaurantMapper restaurantMapper(AddressMapper addressMapper) {
        return new RestaurantMapper(addressMapper);
    }

    @Bean
    public MenuItemMapper menuItemMapper() {
        return new MenuItemMapper();
    }
}
