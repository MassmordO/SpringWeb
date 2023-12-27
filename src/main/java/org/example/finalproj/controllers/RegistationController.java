package org.example.finalproj.controllers;

import org.example.finalproj.models.UserAccount;
import org.example.finalproj.models.UserAdd;
import org.example.finalproj.models.UserM;
import org.example.finalproj.repositories.RolesRepository;
import org.example.finalproj.repositories.UserAccountsRepository;
import org.example.finalproj.repositories.UserStatusesRepository;
import org.example.finalproj.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class RegistationController {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;
    private PasswordEncoder passwordEncoder;
    private final UserAccountsRepository userAccountsRepository;
    private final UserStatusesRepository userStatusesRepository;

    @Autowired
    public RegistationController(UsersRepository usersRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder, UserAccountsRepository userAccountsRepository, UserStatusesRepository userStatusesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
        this.userAccountsRepository = userAccountsRepository;
        this.userStatusesRepository = userStatusesRepository;
    }

    @GetMapping("/registration")
    private String registration(@ModelAttribute("userAdd") UserAdd userAdd, Model model){
        return "registration";
    }

    @PostMapping("/registration")
    private String reg(UserAdd user, Model model){
        UserM userFromDB = usersRepository.findUserMByEmail(user.getEmail());
        if (userFromDB != null){
            model.addAttribute("message", "Пользователь с таким логином уже существует");
            return "registration";
        }
        userFromDB = new UserM();
        userFromDB.setEmail(user.getEmail());
        userFromDB.setExist(true);
        userFromDB.setPassword(passwordEncoder.encode(user.getPassword()));
        userFromDB.setRoles(rolesRepository.findRoleByName("User"));

        var t = usersRepository.save(userFromDB);

        UserAccount account = new UserAccount();
        account.setName(user.getName());
        account.setSername(user.getSername());
        account.setPatronymic(user.getPatronymic());
        account.setUserM(t);
        account.setUserStatus(userStatusesRepository.findUserStatusByDiscount(0));
        userAccountsRepository.save(account);
        return "redirect:/login";
    }
}
