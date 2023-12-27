package org.example.finalproj.controllers;

import org.example.finalproj.models.Cart;
import org.example.finalproj.models.Product;
import org.example.finalproj.repositories.CartsRepository;
import org.example.finalproj.repositories.ProductTypesRepository;
import org.example.finalproj.repositories.ProductsRepository;
import org.example.finalproj.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user/products")
@PreAuthorize("hasAnyAuthority('User')")
public class UserProductController {

    private final UsersRepository usersRepository;
    final private CartsRepository cartsRepository;
    final private ProductTypesRepository productTypesRepository;
    final private ProductsRepository productsRepository;
    @Autowired
    public UserProductController(UsersRepository usersRepository, CartsRepository cartsRepository, ProductTypesRepository productTypesRepository, ProductsRepository productsRepository) {
        this.usersRepository = usersRepository;
        this.cartsRepository = cartsRepository;
        this.productTypesRepository = productTypesRepository;
        this.productsRepository = productsRepository;
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") String id, Model model) {
        model.addAttribute("product", productsRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("InvalidId:" + id)));

        return "user/product/show";
    }

    @GetMapping("/tea")
    public String showTea(Model model){
        model.addAttribute("products", productsRepository.findProductsByProductTypeId(productTypesRepository.findProductTypeByName("Чай").getId()));
        System.out.println(model.getAttribute("products"));
        return "user/product/tea";
    }

    @GetMapping("/all")
    public String showAll(Model model){
        model.addAttribute("products", productsRepository.findByProductTypeIdNot(productTypesRepository.findProductTypeByName("Чай").getId()));
        return "user/product/tea";
    }



    @PostMapping("/add-to-card/{id}")
    public String addToCard(@PathVariable("id") String id,@RequestParam(name = "quantity") int quantity,HttpServletRequest request){
        Cart cart = cartsRepository.findCartByUserId(usersRepository.findUserMByEmail(request.getRemoteUser()).getId());
        if(cart!=null){
            List<Product> products = cart.getProductList();
            for(int i =1; i<=quantity;i++)
            {
                products.add(productsRepository.findById(id).orElseThrow());
            }
            cart.setProductList(products);
            cartsRepository.save(cart);
        }
        else{
            Cart ncart = new Cart();
            ncart.setUser(usersRepository.findUserMByEmail(request.getRemoteUser()));
            List<Product> prods =new ArrayList<>();
            for(int i =1; i<=quantity;i++)
            {
                prods.add(productsRepository.findById(id).orElseThrow());
            }
            ncart.setProductList(prods);
            cartsRepository.save(ncart);
        }
        return "redirect:/";
    }

}
