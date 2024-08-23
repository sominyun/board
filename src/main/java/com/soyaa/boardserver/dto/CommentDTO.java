package com.soyaa.boardserver.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private int id;
    private int postId;
    private String contents;
    private int subCommentId;
}
