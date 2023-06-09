package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserServiceImpl userService;

    @Autowired
    public AdminController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/edit")
    public String edit(User user, Model model) {
        model.addAttribute("users", userService.listUsers());
        return "edit_bootstrap";
    }

    @PostMapping("/edite")
    public String editUser(User user) {
        List<String> listS = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList());
        List<Role> listR = userService.listByRole(listS);
        user.setRoles(listR);
        userService.update(user);
        return "redirect:/admin/edit";
    }

    @GetMapping("/edite/{id}")
    public String updateUser(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", userService.listRoles());
        return "edite";
    }

    @GetMapping("/delete")
    public String delete(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "delete_bootstrap";
    }

    @GetMapping("/start")
    public String index() {
        return "start_page";
    }

    @GetMapping
    public String users(Model model, Principal principal) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("messages", user);
        model.addAttribute("use", new User());
        model.addAttribute("users", userService.listUsers());
        model.addAttribute("roleList", userService.listRoles());
        return "users";
    }

    @GetMapping("/create")
    public String createUserForm(User user, Model model) {
        model.addAttribute("roleList", userService.listRoles());
        return "create";
    }

    @PostMapping("/create")
    public String createUser(User user) {
        if(user.getRoles() != null) {
           List<String> listS = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList());
           List<Role> listR = userService.listByRole(listS);
           user.setRoles(listR);
        }
        userService.add(user);
        return "redirect:/admin";
    }

    @GetMapping("delete/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("update/{id}")
    public String updateUserForm(@PathVariable("id") Long id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleList", userService.listRoles());
        return "update";
    }

    @PostMapping("update")
    public String updateUser(User user) {
        if(user.getRoles() != null) {
            List<String> listS = user.getRoles().stream().map(r -> r.getRole()).collect(Collectors.toList());
            List<Role> listR = userService.listByRole(listS);
            user.setRoles(listR);
            userService.update(user);
        }
        userService.update(user);
        return "redirect:/admin";
    }
}
