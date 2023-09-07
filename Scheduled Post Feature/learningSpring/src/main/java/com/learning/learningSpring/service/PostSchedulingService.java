package com.learning.learningSpring.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.learningSpring.controller.binding.AddPostForm;
import com.learning.learningSpring.entity.Post;
import com.learning.learningSpring.entity.User;
import com.learning.learningSpring.repository.PostRepository;
import com.learning.learningSpring.repository.UserRepository;

import jakarta.servlet.ServletException;

public class PostSchedulingService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Scheduled(cron = "0 0 12 * * ?")
    public String schedulePost(@ModelAttribute("postForm") AddPostForm postForm,
            BindingResult bindingResult,
            RedirectAttributes attr) throws ServletException {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult.getFieldErrors());
            attr.addFlashAttribute("org.springframework.validation.BindingResult.post", bindingResult);
            attr.addFlashAttribute("post", postForm);
            return "redirect:/forum/post/form";
        }

        Optional<User> user = userRepository.findById(postForm.getUserId());
        if (user.isEmpty()) {
            throw new ServletException("Something went seriously wrong, and we couldn't find the user in the DB");
        }

        Post post = new Post();
        post.setAuthor(user.get());
        post.setContent(postForm.getContent());
        postRepository.save(post);
        return "";
    }
}
