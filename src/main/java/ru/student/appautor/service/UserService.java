package ru.student.appautor.service;

import ru.student.appautor.dto.UserDto;
import ru.student.appautor.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    User findUserByEmail (String email);

    List<UserDto> findAllUsers();
}
