package com.tech_challange.grupo35.infrastructure.config;

import com.tech_challange.grupo35.adapters.controller.MenuItemController;
import com.tech_challange.grupo35.adapters.controller.RestaurantController;
import com.tech_challange.grupo35.adapters.controller.UserController;
import com.tech_challange.grupo35.adapters.controller.UserTypeController;
import com.tech_challange.grupo35.adapters.presenter.MenuItemPresenter;
import com.tech_challange.grupo35.adapters.presenter.RestaurantPresenter;
import com.tech_challange.grupo35.adapters.presenter.UserPresenter;
import com.tech_challange.grupo35.adapters.presenter.UserTypePresenter;
import com.tech_challange.grupo35.application.port.in.AssignUserType;
import com.tech_challange.grupo35.application.port.in.ChangePassword;
import com.tech_challange.grupo35.application.port.in.CreateMenuItem;
import com.tech_challange.grupo35.application.port.in.CreateRestaurant;
import com.tech_challange.grupo35.application.port.in.CreateUser;
import com.tech_challange.grupo35.application.port.in.CreateUserType;
import com.tech_challange.grupo35.application.port.in.DeleteMenuItem;
import com.tech_challange.grupo35.application.port.in.DeleteRestaurant;
import com.tech_challange.grupo35.application.port.in.DeleteUser;
import com.tech_challange.grupo35.application.port.in.DeleteUserType;
import com.tech_challange.grupo35.application.port.in.FindUsersByName;
import com.tech_challange.grupo35.application.port.in.GetAllRestaurants;
import com.tech_challange.grupo35.application.port.in.GetAllUserTypes;
import com.tech_challange.grupo35.application.port.in.GetMenuItemById;
import com.tech_challange.grupo35.application.port.in.GetMenuItemsByRestaurant;
import com.tech_challange.grupo35.application.port.in.GetRestaurantById;
import com.tech_challange.grupo35.application.port.in.GetUserTypeById;
import com.tech_challange.grupo35.application.port.in.GetUserTypeByName;
import com.tech_challange.grupo35.application.port.in.LoginUser;
import com.tech_challange.grupo35.application.port.in.UpdateMenuItem;
import com.tech_challange.grupo35.application.port.in.UpdateRestaurant;
import com.tech_challange.grupo35.application.port.in.UpdateUser;
import com.tech_challange.grupo35.application.port.in.UpdateUserType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ControllerConfig {

    @Bean
    public UserTypeController userTypeController(CreateUserType createUserType, GetAllUserTypes getAllUserTypes,
            GetUserTypeById getUserTypeById, GetUserTypeByName getUserTypeByName, UpdateUserType updateUserType,
            DeleteUserType deleteUserType, UserTypePresenter userTypePresenter) {
        return UserTypeController.create(createUserType, getAllUserTypes, getUserTypeById, getUserTypeByName,
                updateUserType, deleteUserType, userTypePresenter);
    }

    @Bean
    public UserController userController(CreateUser createUser, UpdateUser updateUser, ChangePassword changePassword,
            DeleteUser deleteUser, FindUsersByName findUsersByName, LoginUser loginUser, AssignUserType assignUserType,
            UserPresenter userPresenter) {
        return UserController.create(createUser, updateUser, changePassword, deleteUser, findUsersByName, loginUser,
                assignUserType, userPresenter);
    }

    @Bean
    public RestaurantController restaurantController(CreateRestaurant createRestaurant,
            GetAllRestaurants getAllRestaurants, GetRestaurantById getRestaurantById, UpdateRestaurant updateRestaurant,
            DeleteRestaurant deleteRestaurant, RestaurantPresenter restaurantPresenter) {
        return RestaurantController.create(createRestaurant, getAllRestaurants, getRestaurantById, updateRestaurant,
                deleteRestaurant, restaurantPresenter);
    }

    @Bean
    public MenuItemController menuItemController(CreateMenuItem createMenuItem,
            GetMenuItemsByRestaurant getMenuItemsByRestaurant, GetMenuItemById getMenuItemById,
            UpdateMenuItem updateMenuItem, DeleteMenuItem deleteMenuItem, MenuItemPresenter menuItemPresenter) {
        return MenuItemController.create(createMenuItem, getMenuItemsByRestaurant, getMenuItemById, updateMenuItem,
                deleteMenuItem, menuItemPresenter);
    }
}
