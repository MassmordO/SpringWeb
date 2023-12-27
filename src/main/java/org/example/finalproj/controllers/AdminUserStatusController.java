package org.example.finalproj.controllers;

import org.example.finalproj.models.ProductType;
import org.example.finalproj.models.UserStatus;
import org.example.finalproj.repositories.UserStatusesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/userStatuses")
@PreAuthorize("hasAnyAuthority('Admin')")
public class AdminUserStatusController {

    private final UserStatusesRepository userStatusesRepository;

    @Autowired
    public AdminUserStatusController(UserStatusesRepository userStatusesRepository) {
        this.userStatusesRepository = userStatusesRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("userStatuses", userStatusesRepository.findAll());
        return "admin/userStatus/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") String id, Model model) {
        model.addAttribute("userStatus", userStatusesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("InvalidId:" + id)));
        return "admin/userStatus/show";
    }

    @GetMapping("/new")
    public String newStatus(@ModelAttribute("userStatus") UserStatus userStatus, Model model) {
        return "admin/userStatus/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("userStatus") UserStatus userStatus) {
        userStatusesRepository.save(userStatus);
        return "redirect:/admin/userStatuses";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") String id) {
        model.addAttribute("userStatus", userStatusesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("InvalidId:" + id)));
        return "admin/userStatus/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("userStatus") UserStatus userStatus) {
        userStatusesRepository.save(userStatus);
        return "redirect:/admin/userStatuses";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        userStatusesRepository.deleteById(id);
        return "redirect:/admin/userStatuses";
    }
}
