package com.maybank.demo.DemoRestControllerSimulator.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maybank.demo.DemoRestControllerSimulator.model.User;
import com.maybank.demo.DemoRestControllerSimulator.util.ApiUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {


    @Autowired
    private ApiUtil apiUtil;

    @Autowired
    private WebClient webClient;

    @GetMapping("/new")
    public String displayUserForm(Model model) {

        User user = new User();

        model.addAttribute("user", user);

        return "users/new-user";
    }


    @PostMapping("/save")
    public RedirectView createUser(@ModelAttribute(value = "user") User user , @ModelAttribute(value = "firstName") String firstName , @ModelAttribute(value = "lastName") String lastName ,
                                   @ModelAttribute(value = "email") String email, Errors errors, RedirectAttributes attributes) throws JsonProcessingException {

        attributes.addFlashAttribute("flashAttribute", "redirectWithRedirectView");
        user.setEmail(email);
        user.setLastName(lastName);
        user.setFirstName(firstName);
        System.out.println("user.toString()" + user.toString());
        ObjectMapper mapper = new ObjectMapper();
        String value = mapper.writeValueAsString(user);
        System.out.println("value::::--->  "  + value);
        apiUtil.callPostApi(user);

        if(errors.hasErrors()) {
            return new RedirectView("/user/new");
        }
        return new RedirectView("/user/success");
    }

    @GetMapping("/success")
    public String getAllUsers(){
        List<User> userList = webClient.get()
                .uri("http://localhost:8080/api/users")
                .retrieve()
                .bodyToFlux(User.class).collectList().block();
        System.out.println("List<User> userList     " +   userList);
        return "users/success";
    }
}
