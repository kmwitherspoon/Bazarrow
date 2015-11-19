package com.theironyard.controllers;

import com.theironyard.entities.User;
import com.theironyard.services.ItemRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.util.PasswordHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Created by benjamindrake on 11/19/15.
 */
@RestController
public class BazarrowControllers {
    @Autowired
    UserRepository users;

    @Autowired
    ItemRepository items;

    @RequestMapping("/login")
    public String login(HttpSession session, HttpServletResponse response, String username, String password) throws Exception {
        User user = users.findOneByUsername(username);
        if (!PasswordHash.validatePassword(password, user.password)) {
            return "redirect:/create-profile";
        }

        session.setAttribute("username", username);

        return "redirect:/";
    }

    @RequestMapping("/create-profile")
    public String createProfile(String username, String password, String location) throws InvalidKeySpecException, NoSuchAlgorithmException {
        User user = new User();

        user.name = username;
        user.password = PasswordHash.createHash(password);
        user.location = location;

        return "redirect:/";
    }
}