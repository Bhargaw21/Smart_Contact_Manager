package com.scm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm.entities.User;
import com.scm.forms.UserForm; 
import com.scm.helpers.message;
import com.scm.helpers.messageType;
import com.scm.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;




@Controller
public class PageController {

    @Autowired
    private UserService userService;



    @GetMapping("/")
    public String index(){
        return"redirect:/home";
    }


    @GetMapping("/home")
    public String home(Model model){
        System.out.println("Home page handler");
        return "home";
    }

    // about route 

    @GetMapping("/about")
    public String about(){
        System.out.println("page loading....");
        return "about";
    }

    @GetMapping("/services")
    public String Servicespage(){
        System.out.println("services page loading....");
        return "services";
    }

    @GetMapping("/contact")
    public String contactpage(){
        return "contact";
    }

    @GetMapping("/login")
    public String loginpage(){
        return "login";
    }

    @GetMapping("/register")
    public String registerpage(Model model){

        UserForm userForm = new UserForm();
       // userForm.setName("Bhargaw Singh");
        model.addAttribute("userForm" , userForm);
        return "register";
    }

    // processing registration 
    
    @RequestMapping(value = "/do-register", method=RequestMethod.POST)
    public String processRegister(@Valid @ModelAttribute UserForm userForm , BindingResult rBindingResult , HttpSession session){
        System.out.println("processing registration");
        // fetch data
        //UserForm
        System.out.println(userForm);

        //validate form data 
        if(rBindingResult.hasErrors()){
            return "register";
        }
        //save to database

        // user service
       /*  User user = User.builder()
        .Name(userForm.getName())
        .email(userForm.getEmail())
        .about(userForm.getAbout())
        .password(userForm.getPassword())
        .profilepic("")
        .build();*/

        User user = new User();
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setAbout(userForm.getAbout());
        user.setPassword(userForm.getPassword());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setEnabled(false);
        user.setProfilepic("");


       User savedUser =  userService.saveUser(user);
       System.out.println("user saved");
        // message = "registration successful"

        // add successful
      message Message =  message.builder().content("Registration Successful").type(messageType.green).build();


        session.setAttribute("Message", Message);
        //redirectto login page
        return "redirect:/register";
    }

}
