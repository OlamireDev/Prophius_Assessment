package com.olamireDev.prophiusapi.payload.response;

import com.olamireDev.prophiusapi.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String content;
    private String username;
    private int likes;
    private List<CommentDTO> comments;

    public static PostDTO mapToDTO(Post post) {
        var commentDTOs = post.getComments().stream().map(CommentDTO::commentDTOBuilder).toList();
        return new PostDTO(post.getId(), post.getContent(), post.getCreatedBy().getUsername(), post.getLikeCount(), commentDTOs);
    }

}
