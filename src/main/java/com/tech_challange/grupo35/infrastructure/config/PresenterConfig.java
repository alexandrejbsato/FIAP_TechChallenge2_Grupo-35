package com.tech_challange.grupo35.infrastructure.config;

import com.tech_challange.grupo35.adapters.presenter.MenuItemPresenter;
import com.tech_challange.grupo35.adapters.presenter.RestaurantPresenter;
import com.tech_challange.grupo35.adapters.presenter.UserPresenter;
import com.tech_challange.grupo35.adapters.presenter.UserTypePresenter;
import com.tech_challange.grupo35.application.mapper.AddressMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PresenterConfig {

    @Bean
    public UserTypePresenter userTypePresenter() {
        return new UserTypePresenter();
    }

    @Bean
    public UserPresenter userPresenter(AddressMapper addressMapper) {
        return new UserPresenter(addressMapper);
    }

    @Bean
    public RestaurantPresenter restaurantPresenter(AddressMapper addressMapper) {
        return new RestaurantPresenter(addressMapper);
    }

    @Bean
    public MenuItemPresenter menuItemPresenter() {
        return new MenuItemPresenter();
    }
}
