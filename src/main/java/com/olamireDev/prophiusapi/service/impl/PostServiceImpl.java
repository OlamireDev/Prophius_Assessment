package com.olamireDev.prophiusapi.service.impl;

import com.olamireDev.prophiusapi.entity.Comment;
import com.olamireDev.prophiusapi.entity.Post;
import com.olamireDev.prophiusapi.enums.PostSortingParameter;
import com.olamireDev.prophiusapi.exception.InvalidOperationException;
import com.olamireDev.prophiusapi.exception.PostNotFoundException;
import com.olamireDev.prophiusapi.exception.UserNotFoundException;
import com.olamireDev.prophiusapi.payload.request.CreatePostDTO;
import com.olamireDev.prophiusapi.payload.request.EditPostDTO;
import com.olamireDev.prophiusapi.payload.request.PostPageRequestDTO;
import com.olamireDev.prophiusapi.payload.response.PostDTO;
import com.olamireDev.prophiusapi.repository.CommentRepository;
import com.olamireDev.prophiusapi.repository.PostRepository;
import com.olamireDev.prophiusapi.repository.UserRepository;
import com.olamireDev.prophiusapi.service.PostService;
import com.olamireDev.prophiusapi.util.ContextEmail;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    @Override
    public PostDTO createPost(CreatePostDTO createPostDTO) throws UserNotFoundException {
        var user = userRepository.findByEmail(ContextEmail.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user details not fund"));
        var post = Post.builder().content(createPostDTO.content()).createdBy(user).build();
        post  = postRepository.save(post);
        return PostDTO.mapToDTO(post);
    }

    @Override
    public PostDTO getPost(Long id) throws PostNotFoundException {
        var post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("post not found"));
        return PostDTO.mapToDTO(post);
    }

    @Override
    public List<PostDTO> getAllPosts(Long userId) throws UserNotFoundException {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found"));
        var posts = postRepository.findAllByCreatedBy(user);
        return posts.stream().map(PostDTO::mapToDTO).toList();
    }


    @Override
    public String updatePost(EditPostDTO editPostDTO) throws UserNotFoundException, PostNotFoundException, InvalidOperationException {
        var user = userRepository.findByEmail(ContextEmail.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user details not fund"));
        var post = postRepository.findById(editPostDTO.id()).orElseThrow(() -> new PostNotFoundException("post not found"));
        if(post.getCreatedBy() == user){
            post.setContent(editPostDTO.content());
            postRepository.save(post);
            return "Post updated";
        }
        throw new InvalidOperationException("Wow, taking credit for what isnt yours... its sad");
    }

    @Override
    public String deletePost(Long id) throws UserNotFoundException, PostNotFoundException, InvalidOperationException {
        var user = userRepository.findByEmail(ContextEmail.getEmail())
                .orElseThrow(() -> new UserNotFoundException("user details not fund"));
        var post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("post not found"));
        if(post.getCreatedBy() == user){
            commentRepository.deleteAll(commentRepository.findAllByPost(post));
            postRepository.delete(post);
            return "Post deleted";
        }
        throw new InvalidOperationException("Wow, you cannot delete a post you did not create, its sad");
    }

    @Override
    public Page<PostDTO> getAllPostPaged(PostPageRequestDTO pageRequest) throws UserNotFoundException {
        var user = userRepository.findById(pageRequest.userId())
                .orElseThrow(() -> new UserNotFoundException("user details not fund"));
        Pageable paging = PageRequest.of(pageRequest.pageNumber() < 0? 0: pageRequest.pageNumber(), pageRequest.pageSize() < 1? 1: pageRequest.pageSize());
        Page<Post> posts = null;
        if(pageRequest.sorted()){
            switch(pageRequest.parameter()){
                case DATE_ASC -> posts = postRepository.findAllByCreatedByOrderByCreatedAtAsc(user, paging);

                case DATE_DESC -> posts = postRepository.findAllByCreatedByOrderByCreatedAtDesc(user, paging);

                case LIKES_ASC -> posts = postRepository.findAllByCreatedByOrderByLikeCountAsc(user, paging);

                case LIKES_DESC -> posts = postRepository.findAllByCreatedByOrderByLikeCountDesc(user, paging);
            }
        }else{
            posts = postRepository.findAllByCreatedBy(user, paging);
        }
        var postDTOs = posts.stream().map(PostDTO::mapToDTO).toList();
        return new PageImpl(postDTOs, paging, posts.getTotalElements());
    }
}
