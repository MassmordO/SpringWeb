package org.example.finalproj.controllers;


import org.example.finalproj.models.UserAdd;
import org.example.finalproj.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/")
public class MainController {

    private final ProductsRepository productsRepository;
    @Autowired
    public MainController(ProductsRepository productsRepository) {
        this.productsRepository = productsRepository;
    }

    @GetMapping("/")
    public String index(Model model){
        List<GrantedAuthority> roles = (List<GrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        if (roles.get(0).getAuthority().equals("User")) {
            model.addAttribute("products", productsRepository.findAll().stream().limit(8).toList());
            return "/index";
        }
        else if (roles.get(0).getAuthority().equals("Admin"))
            return "/indexAdm";
        else if (roles.get(0).getAuthority().equals("Employee"))
            return "/indexEmp";
        model.addAttribute("products", productsRepository.findAll().stream().limit(8).toList());
        return "/index";
    }

//    @GetMapping("/profile")
//    public String profile(Model model){
//        User user = userClient.findAll()
//                .stream().filter(u -> u.getUsername().equals(getUsername())).findFirst().orElseThrow();
//        model.addAttribute("user", user);
//
//        return "profile";
//    }
//
//    @GetMapping("/{id}/edit")
//    public String edit(Model model, @PathVariable("id") long id) {
//        User user = userClient.findAll()
//                .stream().filter(u -> u.getUsername().equals(getUsername())).findFirst().orElseThrow();
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roleClient.findAll());
//        return "edit";
//    }
//
//    @PostMapping("/{id}/update")
//    public String update(@PathVariable("id") long id, @Valid User user, BindingResult result) {
//        if (result.hasErrors()){
//            return "edit";
//        }
//
//        userClient.update(user);
//        return "profile";
//    }
//
//    public static String getUsername(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // Get the username of the currently authenticated user
//        String username = authentication.getName();
//        return username;
//    }
}
