package org.example.finalproj.controllers;

import org.example.finalproj.models.UserM;
import org.example.finalproj.repositories.RolesRepository;
import org.example.finalproj.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
@PreAuthorize("hasAnyAuthority('Admin')")
public class AdminUserContoller {

    private  PasswordEncoder passwordEncoder;
    private final RolesRepository rolesRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public AdminUserContoller(PasswordEncoder passwordEncoder, RolesRepository rolesRepository, UsersRepository usersRepository) {
        this.passwordEncoder = passwordEncoder;
        this.rolesRepository = rolesRepository;
        this.usersRepository = usersRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("users", usersRepository.findAll());
        return "admin/user/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") String id, Model model) {
        model.addAttribute("user", usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("InvalidId:" + id)));
        return "admin/user/show";
    }


    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") String id) {
        model.addAttribute("user", usersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("InvalidId:" + id)));
        model.addAttribute("roles",rolesRepository.findAll());
        return "admin/user/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("user") UserM user, @RequestParam(name = "roleId")List<String> ids) {
        for(String id : ids){
            user.setRoles(rolesRepository.findById(id).orElseThrow());
        }
        UserM userdb = usersRepository.findById(user.getId()).orElseThrow();
        user.setPassword(userdb.getPassword());
        usersRepository.save(user);
        return "redirect:/admin/users";
    }
}
