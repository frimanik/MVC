package com.example.Spring.MVC.json.view;

import com.example.Spring.MVC.json.view.Entities.Order;
import org.junit.jupiter.api.Test;

import com.example.Spring.MVC.json.view.Controllers.UserController;
import com.example.Spring.MVC.json.view.Entities.User;
import com.example.Spring.MVC.json.view.Exceptions.UserNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class UserControllerTests {

    private UserController userController;
    private ArrayList<User> users;

    @BeforeEach
    void setUp() {
        users = new ArrayList<>();
        userController = new UserController(users);
    }

    @Test
    void testGetUsers() throws JsonProcessingException {

        Order order1 = new Order(0, "lamp", 10, "processing");
        Order order2 = new Order(1, "fridge", 15, "waiting");
        Order order3 = new Order(2, "sofa", 20, "processing");

        users.add(new User(1, "John", "mail@com", List.of(order1, order2)));
        users.add(new User(2, "Alice", "soemail@com", List.of(order3)));

        List<User> result = userController.getUsers();

        assertThat(result).hasSize(2);
        assert (result.get(0).equals(users.get(0)));
        assert (result.get(1).equals(users.get(1)));
    }

    @Test
    void testGetUser_UserExists() throws JsonProcessingException {

        Order order1 = new Order(0, "lamp", 10, "processing");

        User user = new User(1, "John", "mail@com", List.of(order1));
        users.add(user);

        User result = userController.getUser(1L);

        assertThat(result).isEqualTo(user);
    }

    @Test
    void testGetUser_UserNotFound() {

        assertThatThrownBy(() -> userController.getUser(1L))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("User with id 1 does not exist");
    }

    @Test
    void testAddUser() {

        Order order = new Order(0, "lamp", 10, "processing");
        User user = new User(0, "John", "mail@com", List.of(order));

        User result = userController.createUser(user);

        assertThat(result).isEqualTo(user);
        assertThat(users).contains(user);
    }

    @Test
    void testUpdateUser() {

        Order order1 = new Order(0, "lamp", 10, "processing");
        User user = new User(0, "John", "mail@com", List.of(order1));

        users.add(user);
        User updatedUser = new User(0, "Joe", "mail", List.of(order1));

        User result = userController.updateUser(updatedUser, 0L);

        assertThat(result).isEqualTo(updatedUser);
        assertThat(users.get(0)).isEqualTo(updatedUser);
    }
}