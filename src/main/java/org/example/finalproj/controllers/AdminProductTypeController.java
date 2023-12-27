package org.example.finalproj.controllers;

import org.example.finalproj.models.ProductType;
import org.example.finalproj.repositories.ProductTypesRepository;
import org.example.finalproj.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/productTypes")
@PreAuthorize("hasAnyAuthority('Admin')")
public class AdminProductTypeController {

    private final ProductTypesRepository productTypesRepository;

    @Autowired
    public AdminProductTypeController(ProductTypesRepository productTypesRepository) {
        this.productTypesRepository = productTypesRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("productTypes", productTypesRepository.findAll());
        return "admin/productType/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") String id, Model model) {
        model.addAttribute("productType", productTypesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("InvalidId:" + id)));
        return "admin/productType/show";
    }

    @GetMapping("/new")
    public String newType(@ModelAttribute("productType") ProductType productType, Model model) {
        return "admin/productType/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("productType") ProductType productType) {
        productTypesRepository.save(productType);
        return "redirect:/admin/productTypes";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") String id) {
        model.addAttribute("productType", productTypesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("InvalidId:" + id)));
        return "admin/productType/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("productType") ProductType productType) {
        productTypesRepository.save(productType);
        return "redirect:/admin/productTypes";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        productTypesRepository.deleteById(id);
        return "redirect:/admin/productTypes";
    }
}
