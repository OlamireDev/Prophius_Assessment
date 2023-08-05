package com.olamireDev.prophiusapi;

import com.olamireDev.prophiusapi.entity.Post;
import com.olamireDev.prophiusapi.entity.User;
import com.olamireDev.prophiusapi.repository.PostRepository;
import com.olamireDev.prophiusapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.LinkedList;
import java.util.List;

@SpringBootApplication
public class ProphiusApiApplication implements CommandLineRunner {

	@Autowired
	UserRepository userRepository;
	@Autowired
	PostRepository postRepository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(ProphiusApiApplication.class, args);
	}

	@Override
	public void run(String... args)  {
		//Create some users and some posts
		List<User> users = new LinkedList<>();
		users.add(User.builder().username("Arbiter").email("test1@gmail.com").password(passwordEncoder.encode("hope")).build());
		users.add(User.builder().username("John 117").email("test2@gmail.com").password(passwordEncoder.encode("hope")).build());
		users.add(User.builder().username("Laskey").email("test3@gmail.com").password(passwordEncoder.encode("hope")).build());
		users = userRepository.saveAll(users);
		var post = Post.builder().content("random content").createdBy(users.get(0)).build();
		postRepository.save(post);
		post = Post.builder().content("random content").createdBy(users.get(1)).build();
		postRepository.save(post);
		post = Post.builder().content("random content").createdBy(users.get(2)).build();
		postRepository.save(post);
	}
}
