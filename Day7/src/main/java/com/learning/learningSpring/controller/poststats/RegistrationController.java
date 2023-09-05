package com.learning.learningSpring.controller.poststats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.learningSpring.business.UserForm;
import com.learning.learningSpring.entity.Users;
import com.learning.learningSpring.service.UserService;



@Controller
@RequestMapping("/register")
public class RegistrationController {
    
    @Autowired
    private UserService userService;

    @GetMapping
    public String getRegistrationForm(Model model) {
        if (!model.containsAttribute("userForm")) {
            model.addAttribute("userForm", new UserForm());
        }
        return "forum/register";
    }

    @PostMapping
    public String register(@ModelAttribute("userForm") UserForm userForm, 
    BindingResult bindingResult, 
    RedirectAttributes attr) {
        if (bindingResult.hasErrors()) {
            attr.addFlashAttribute("org.springframework.validation.BindingResult.userForm", bindingResult);
            attr.addFlashAttribute("userForm", userForm);
            return "redirect:/register";
        }
        if (!userForm.getPassword().equals(userForm.getPasswordRepeat())) {
            attr.addFlashAttribute("message", "Passwords must match");
            attr.addFlashAttribute("userForm", userForm);
            return "redirect:/register";
        }
        Users userToCreate = new Users();
        userToCreate.setName(userForm.getName());
        userToCreate.setPassword(userForm.getPassword());
        userService.create(userToCreate);
        attr.addFlashAttribute("result", "Registration success!");
        return "redirect:/loginpage";
    }

}
