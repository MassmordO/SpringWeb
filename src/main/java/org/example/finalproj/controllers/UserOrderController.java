package org.example.finalproj.controllers;

import org.example.finalproj.repositories.OrdersRepository;
import org.example.finalproj.repositories.UserAccountsRepository;
import org.example.finalproj.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/user/orders")
@PreAuthorize("hasAnyAuthority('User')")
public class UserOrderController {
    private final OrdersRepository ordersRepository;
    private final UsersRepository usersRepository;

    private final UserAccountsRepository userAccountsRepository;

    @Autowired
    public UserOrderController(OrdersRepository ordersRepository, UsersRepository usersRepository, UserAccountsRepository userAccountsRepository) {
        this.ordersRepository = ordersRepository;
        this.usersRepository = usersRepository;
        this.userAccountsRepository = userAccountsRepository;
    }
    @GetMapping()
    public String Index(HttpServletRequest request, Model model) {
        model.addAttribute("orders",
                ordersRepository
                .findAllByUserAccountId(
                        userAccountsRepository.findByUserMId(
                        usersRepository.findUserMByEmail(request.getRemoteUser()).getId()).getId()));
            return "/user/order/index";
        }
    @PostMapping("/{id}")
    public String show(@PathVariable("id") String id, Model model){
        model.addAttribute("order", ordersRepository.findById(id).orElseThrow());
        return "/user/order/show";
    }
}
