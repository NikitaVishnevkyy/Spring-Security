package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.dao.UserDaoImpl;
import ru.kata.spring.boot_security.demo.model.User;

import java.security.Principal;

@Controller
public class UserController {
    private final UserDaoImpl userDao;

    public UserController(UserDaoImpl userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/")
    public String home (){
        return "index";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/user")
    public String print(ModelMap model, Principal principal) {
        User userDaoByName = userDao.findByName(principal.getName());
        model.addAttribute("messages", userDaoByName);
        return "user";
    }
}


