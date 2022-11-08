package ru.student.appautor.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.student.appautor.dto.UserDto;
import ru.student.appautor.entity.Role;
import ru.student.appautor.entity.User;
import ru.student.appautor.repository.RoleRepository;
import ru.student.appautor.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser (UserDto userDto){
        User user = new User();
        user.setName(userDto.getFirstName()+" "+userDto.getLastName());
        user.setEmail(userDto.getEmail());

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findByName("ROLE_ADMIN");
        if(role==null){
            role=checkRoleExcist();
            }
        user.setRoles(Arrays.asList(role));
        userRepository.save(user);
    }

    @Override
    public User findUserByEmail (String email){
        return userRepository.findByEmail(email);
    }

    @Override
    public List <UserDto> findAllUsers(){
        List<User>users = userRepository.findAll();
        return users.stream()
                .map((user)->mapToUserDto(user))
                .collect(Collectors.toList());
    }

    private UserDto mapToUserDto (User user){
        UserDto userDto = new UserDto();
        String[]str = user.getName().split(" ");
        userDto.setFirstName(str[0]);
        userDto.setLastName(str[1]);
        userDto.setEmail(user.getEmail());
        return userDto;
    }

    private Role checkRoleExcist (){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

}
