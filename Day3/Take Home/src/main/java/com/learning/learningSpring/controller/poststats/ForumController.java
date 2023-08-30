package com.learning.learningSpring.controller.poststats;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.learning.learningSpring.controller.binding.AddPostForm;
import com.learning.learningSpring.controller.exceptions.ResourceNotFoundException;
import com.learning.learningSpring.entity.LikeRecord;
import com.learning.learningSpring.entity.LikeId;
import com.learning.learningSpring.entity.Post;
import com.learning.learningSpring.entity.User;
import com.learning.learningSpring.repository.LikeCRUDRepository;
import com.learning.learningSpring.repository.LikeCountRepository;
import com.learning.learningSpring.repository.PostRepository;
import com.learning.learningSpring.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.ServletException;

@Controller
@RequestMapping("/forum")
public class ForumController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private LikeCountRepository likeCountRepository;

	@Autowired
	private LikeCRUDRepository likeCRUDRepository;

	private List<User> userList;

	@PostConstruct
	public void init() {
		userList = new ArrayList<>();
		userRepository.findAll().forEach(user -> userList.add(user));
	}

	private Optional<User> findFromInMemoryListById(int id) {
		return userList.stream().filter(user -> user.getId() == id).findFirst();
	}

	@GetMapping("/post/form")
	public String getPostForm(Model model) {
		model.addAttribute("postForm", new AddPostForm());
		model.addAttribute("userList", userList);
		model.addAttribute("authorid", 0);
		return "forum/postForm";
	}

	@PostMapping("/post/add")
	public String addNewPost(@ModelAttribute("postForm") AddPostForm postForm, BindingResult bindingResult,
			RedirectAttributes attr) throws ServletException {
		if (bindingResult.hasErrors()) {
			System.out.println(bindingResult.getFieldErrors());
			attr.addFlashAttribute("org.springframework.validation.BindingResult.post", bindingResult);
			attr.addFlashAttribute("post", postForm);
			return "redirect:/forum/post/form";
		}
		Optional<User> user = findFromInMemoryListById(postForm.getUserId());
		if (user.isEmpty()) {
			throw new ServletException("Something went seriously wrong and we couldn't find the user in the DB");
		}
		Post post = new Post();
		post.setAuthor(user.get());
		post.setContent(postForm.getContent());
		postRepository.save(post);

		return String.format("redirect:/forum/post/%d", post.getId());
	}

	@GetMapping("/post/{id}")
	public String postDetail(@PathVariable int id, Model model) throws ResourceNotFoundException {
		Optional<Post> post = postRepository.findById(id);
		if (post.isEmpty()) {
			throw new ResourceNotFoundException("No post with the requested ID");
		}
		model.addAttribute("post", post.get());
		model.addAttribute("userList", userList);
		int numLikes = likeCountRepository.countByPostId(id);
		model.addAttribute("likeCount", numLikes);
		return "forum/postDetail";
	}

	@PostMapping("/post/{id}/like")
	public String postLike(@PathVariable int id, Integer likerId, RedirectAttributes attr) {
		LikeId likeId = new LikeId();
		likeId.setUser(findFromInMemoryListById(likerId).get());
		likeId.setPost(postRepository.findById(id).get());
		LikeRecord like = new LikeRecord();
		like.setLikeId(likeId);
		likeCRUDRepository.save(like);
		return String.format("redirect:/forum/post/%d", id);
	}

}
