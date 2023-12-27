package org.example.finalproj.controllers;

import org.example.finalproj.models.Cart;
import org.example.finalproj.models.Order;
import org.example.finalproj.models.Product;
import org.example.finalproj.models.UserAccount;
import org.example.finalproj.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user/carts")
@PreAuthorize("hasAnyAuthority('User')")
public class UserCartController {

    private final CartsRepository cartsRepository;
    private final UsersRepository usersRepository;

    private final ProductsRepository productsRepository;

    private final UserAccountsRepository userAccountsRepository;
    private final OrdersRepository ordersRepository;

    private final  UserStatusesRepository userStatusesRepository;

    @Autowired
    public UserCartController(CartsRepository cartsRepository, UsersRepository usersRepository, ProductsRepository productsRepository, UserAccountsRepository userAccountsRepository, OrdersRepository ordersRepository, UserStatusesRepository userStatusesRepository) {
        this.cartsRepository = cartsRepository;
        this.usersRepository = usersRepository;
        this.productsRepository = productsRepository;
        this.userAccountsRepository = userAccountsRepository;
        this.ordersRepository = ordersRepository;
        this.userStatusesRepository = userStatusesRepository;
    }

    @GetMapping()
    public String Index(Model model, HttpServletRequest request){
        if(cartsRepository.findCartByUserId(usersRepository.findUserMByEmail(request.getRemoteUser()).getId())!=null){
        model.addAttribute("cart", cartsRepository.findCartByUserId(usersRepository.findUserMByEmail(request.getRemoteUser()).getId()));
        double totalSum = cartsRepository.findCartByUserId(usersRepository.findUserMByEmail(request.getRemoteUser()).getId()).getProductList()
                .stream().
                mapToDouble(x-> x.getPricSell()-(x.getPricSell()/100*x.getDiscount()))
                .sum();
        model.addAttribute("totalSum", totalSum);
        }
        return "/user/cart/index";
    }

    @PostMapping("/{id}")
    public String deleteFromCart(@PathVariable("id") String id, HttpServletRequest request){
        Cart cart = cartsRepository.findCartByUserId(usersRepository.findUserMByEmail(request.getRemoteUser()).getId());
        List<Product> products = cart.getProductList();
        products.removeIf(x->x.getId().equals(productsRepository.findById(id).orElseThrow().getId()));
        cart.setProductList(products);
        cartsRepository.save(cart);
        return "redirect:/user/carts";
    }

    @PostMapping("/buy/{id}")
    public String buy(@PathVariable("id") String id, HttpServletRequest request){
        Cart cart = cartsRepository.findById(id).orElseThrow();
        UserAccount userAccount = userAccountsRepository.findByUserMId(usersRepository.findUserMByEmail(request.getRemoteUser()).getId());
        Order order = new Order();
        order.setDone(false);
        order.setNumber(UUID.randomUUID().toString());
        order.setUserAccount(userAccount);
        order.setProducts(cart.getProductList());
        double total = 0;
        for (Product prod : cart.getProductList())
        {
            total+=prod.getPricSell()-(prod.getPricSell()/100*prod.getDiscount());
        }
        if(userAccount.getUserStatus() !=null)
            order.setPrice(total-(total/100*userAccount.getUserStatus().getDiscount()));
        else
            order.setPrice(total);
        ordersRepository.save(order);

        List<Order> orders = ordersRepository.findAllByUserAccountId(userAccount.getId());
        switch (orders.size()){
            case 10:
                userAccount.setUserStatus(userStatusesRepository.findUserStatusByDiscount(3));
                userAccountsRepository.save(userAccount);
                break;
            case 20:
                userAccount.setUserStatus(userStatusesRepository.findUserStatusByDiscount(10));
                userAccountsRepository.save(userAccount);
                break;
            case 40:
                userAccount.setUserStatus(userStatusesRepository.findUserStatusByDiscount(15));
                userAccountsRepository.save(userAccount);
                break;
            case 60:
                userAccount.setUserStatus(userStatusesRepository.findUserStatusByDiscount(20));
                userAccountsRepository.save(userAccount);
                break;
            case 100:
                userAccount.setUserStatus(userStatusesRepository.findUserStatusByDiscount(30));
                userAccountsRepository.save(userAccount);
                break;
            default:
                break;
        }
        cartsRepository.deleteById(cart.getId());
        return "redirect:/user/orders";
    }
}
