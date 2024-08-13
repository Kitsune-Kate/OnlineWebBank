package org.example.controller;

import org.example.model.Role;
import org.example.model.User;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/registration")
    public String getMapping(Model model) {

        return "registration";
    }

    @PostMapping("/registration")
    public String postMapReg(@RequestParam(name = "name") String name, @RequestParam(name = "firstName") String firstName, @RequestParam(name = "username") String username, @RequestParam(name = "password") String password,
                             @RequestParam(name = "password-repeat") String passwordRepeat, @RequestParam("image") MultipartFile file, Model model) throws IOException {
        if (!password.equals(passwordRepeat)) {
            model.addAttribute("error", "Пароли не совпадают");
            return "registration";
        }
        //userRepository.findAll().stream().map(x->x.getUsername()).filter(x->x.equals(username)).count()>1
        if (userRepository.existsByUsername(username)) {
            model.addAttribute("error", "Такой пользователь уже существут");
            return "registration";
        } else {
            User user = new User();
            Set<Role> roles = new HashSet<>();
            roles.add(Role.USER);
            user.setName(name);
            user.setFirstName(firstName);
            user.setUsername(username);
            user.setPassword(password);
            user.setRoles(roles);
            user.setActive(true);
            String uuid = UUID.randomUUID().toString().substring(0, 13);
            String fileName = uuid + file.getOriginalFilename();
            file.transferTo(new File(uploadPath + "/" + fileName));
            if (!file.isEmpty()) {
                user.setFilename(fileName);
            }
            userRepository.save(user);
        }
        return "redirect:/mainPage";
    }
}
