package com.olamireDev.prophiusapi.payload.response;

import com.olamireDev.prophiusapi.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@Data
public class CommentDTO {
    private Long id;
    private String content;
    private String username;

    public static CommentDTO commentDTOBuilder(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getContent(), comment.getCommentedBy().getUsername());
    }
}
