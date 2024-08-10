package com.scm.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.scm.entities.User;
import com.scm.entities.contact;
import com.scm.forms.ContactForm;
import com.scm.forms.contactSearchForm;
import com.scm.helpers.AppConstants;
import com.scm.helpers.helper;
import com.scm.helpers.message;
import com.scm.helpers.messageType;
import com.scm.services.ContactService;
import com.scm.services.UserService;
import com.scm.services.imageService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    private Logger logger = org.slf4j.LoggerFactory.getLogger(ContactController.class);

    @Autowired
    private ContactService contactService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private imageService ImageService;

    @RequestMapping("/add")
    public String addContactView(Model model) {
        ContactForm contactForm = new ContactForm();
        model.addAttribute("contactForm", contactForm);
        return "user/add_contact";
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm,BindingResult result, Authentication authentication , HttpSession session) {

        // process th form data

        if(result.hasErrors()){
            session.setAttribute("message",message.builder().content("Please correct the following error")
            .type(messageType.red)
            .build());
            return "user/add_contact";
        }
        // validate the form

        String username = helper.getEmailOfLoggedInUser(authentication);
        // form --> contact

        User user = userService.getUserByEmail(username);

        logger.info("file information : {}", contactForm.getContactImage().getOriginalFilename());

        // upload karne ka code 

        String filename = UUID.randomUUID().toString();

       String fileURL =  ImageService.uploadImage(contactForm.getContactImage(), filename);

        contact Contact = new contact();
        Contact.setName(contactForm.getName());
        Contact.setEmail(contactForm.getEmail());
        Contact.setFavorite(contactForm.getFavorite());
        Contact.setPhoneNumber(contactForm.getPhoneNumber());
        Contact.setAddress(contactForm.getAddress());
        Contact.setDescription(contactForm.getDescription());
        Contact.setUser(user);
        Contact.setLinkednLink(contactForm.getLinkednLink());
        Contact.setWebsitelink(contactForm.getWebsitelink());
        Contact.setPicture(fileURL);
        Contact.setCloudianryImagePublicId(filename);

        contactService.save(Contact);

        System.out.println(contactForm);

        session.setAttribute("message",
        message.builder().content("you have successfully added a new contact")
            .type(messageType.blue )
            .build());
        return "redirect:/user/contacts/add";
    }

    // view contacts

    @RequestMapping
    public String viewContacts(
    @RequestParam(value ="page", defaultValue = "0") int page,    
    @RequestParam(value="size" , defaultValue = AppConstants.PAGE_SIZE + "") int size,
    @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
    @RequestParam(value="direction",defaultValue = "asc") String direction , Model model , Authentication authentication){

        //load all the user contacts
        String username = helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(username);
        Page<contact> pageContact = contactService.getByUser(user, page , size , sortBy , direction );
        model.addAttribute("pageContact", pageContact);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("ContactSearchForm", new contactSearchForm());
        return "user/contacts";
    }

    // search handler

    @RequestMapping("/search")
    public String searchHandler(
        @ModelAttribute contactSearchForm ContactSearchForm,
        @RequestParam(value = "size" , defaultValue = AppConstants.PAGE_SIZE + "") int size ,
        @RequestParam(value="page" , defaultValue = "0") int page,
        @RequestParam(value = "sortBy" , defaultValue = "name") String sortBy,
        @RequestParam(value="direction",defaultValue = "asc") String direction , Model model , Authentication authentication
    ){

        logger.info("field {} keyword {}",ContactSearchForm.getField(), ContactSearchForm.getValue());

        var user = userService.getUserByEmail(helper.getEmailOfLoggedInUser(authentication));

        Page<contact> pageContact = null;
        if(ContactSearchForm.getField().equalsIgnoreCase("name")){
         pageContact =  contactService.searchByName(ContactSearchForm.getValue(), size ,page , sortBy , direction,user);
        }else if(ContactSearchForm.getField().equalsIgnoreCase("email")){
            pageContact = contactService.searchByName(ContactSearchForm.getValue(), size ,page , sortBy , direction,user);
    }else if(ContactSearchForm.getField().equalsIgnoreCase("Phone")){
        pageContact = contactService.searchByName(ContactSearchForm.getValue(), size ,page , sortBy , direction , user);
    }

    model.addAttribute("ContactSearchForm", ContactSearchForm);
    model.addAttribute("pageContact", pageContact);
    model.addAttribute("pageSize", AppConstants.PAGE_SIZE);

        return "user/search";
    }

    @RequestMapping("/delete/{contactId}")
    public String deleteContact(
        @PathVariable String contactId
    ){

        contactService.delete(contactId);
        return "redirect:/user/contacts";
    }
}
