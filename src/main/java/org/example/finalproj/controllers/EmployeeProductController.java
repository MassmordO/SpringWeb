package org.example.finalproj.controllers;

import org.example.finalproj.models.Photo;
import org.example.finalproj.models.Product;
import org.example.finalproj.models.UserStatus;
import org.example.finalproj.repositories.PhotosRepository;
import org.example.finalproj.repositories.ProductTypesRepository;
import org.example.finalproj.repositories.ProductsRepository;
import org.example.finalproj.repositories.UserStatusesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/employee/products")
@PreAuthorize("hasAnyAuthority('Employee')")
public class EmployeeProductController {

    private final ProductsRepository productsRepository;
    private final ProductTypesRepository productTypesRepository;
    private final PhotosRepository photosRepository;

    @Autowired
    public EmployeeProductController(ProductsRepository productsRepository, ProductTypesRepository productTypesRepository, PhotosRepository photosRepository) {
        this.productsRepository = productsRepository;
        this.productTypesRepository = productTypesRepository;
        this.photosRepository = photosRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("products", productsRepository.findAll());
        return "employee/product/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") String id, Model model) {
        model.addAttribute("product", productsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("InvalidId:" + id)));

        return "employee/product/show";
    }

    @GetMapping("/new")
    public String newProduct(@ModelAttribute("product") Product product, Model model) {
        model.addAttribute("productTypes", productTypesRepository.findAll());
        return "employee/product/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("product") Product product,
                         @RequestParam(name = "typeID") String ids
    ) {
        product.setProductType(productTypesRepository.findById(ids).orElseThrow());
        product.setExist(true);
        productsRepository.save(product);
        return "redirect:/employee/products";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") String id) {
        model.addAttribute("product", productsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("InvalidId:" + id)));
        model.addAttribute("productTypes", productTypesRepository.findAll());
        return "employee/product/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("product") Product product,
                         @RequestParam(name = "typeID") String ids) {
        product.setProductType(productTypesRepository.findById(ids).orElseThrow());
        productsRepository.save(product);
        return "redirect:/employee/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        productsRepository.deleteById(id);
        return "redirect:/employee/products";
    }
}
