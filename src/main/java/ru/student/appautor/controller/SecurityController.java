package ru.student.appautor.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.student.appautor.dto.UserDto;
import ru.student.appautor.entity.User;
import ru.student.appautor.service.UserService;

import javax.management.modelmbean.ModelMBeanOperationInfo;
import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
public class SecurityController {

    private UserService userService;

    @GetMapping("/index")
    public String home(){
        return "index";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
        }

    @GetMapping("/register")
    public String showRegistrationForm (Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registrration (@Valid @ModelAttribute("user") UserDto userDto,
                                 BindingResult result,
                                 Model model){
        User existingUser = userService.findUserByEmail(userDto.getEmail());

        if(existingUser!=null&&existingUser.getEmail()!=null&&!existingUser.getEmail().isEmpty()){
            result.rejectValue("email",null,
                    "На этот адрес уже есть учетная запись");
        }
        if(result.hasErrors()){
            model.addAttribute("user",userDto);
            return "/register";
        }
        userService.saveUser(userDto);
        return "/login";
    }

    @GetMapping("/users")
    public String user(Model model){
        List <UserDto> users = userService.findAllUsers();
        model.addAttribute("users",users);
        return "users";
    }
}
