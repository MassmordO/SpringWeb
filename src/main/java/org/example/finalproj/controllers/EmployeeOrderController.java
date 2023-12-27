package org.example.finalproj.controllers;

import org.example.finalproj.models.UserStatus;
import org.example.finalproj.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employee/orders")
@PreAuthorize("hasAnyAuthority('Employee')")
public class EmployeeOrderController {

    private final OrdersRepository ordersRepository;

    @Autowired
    public EmployeeOrderController(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("orders", ordersRepository.findAll());
        return "employee/order/index";
    }
    @GetMapping("/{id}")
    public String show(@PathVariable("id") String id, Model model) {
        model.addAttribute("order", ordersRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("InvalidId:" + id)));
        return "employee/order/show";
    }

}
