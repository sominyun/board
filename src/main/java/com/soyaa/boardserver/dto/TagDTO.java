package com.soyaa.boardserver.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    private int id;
    private String name;
    private String url;
    private int postId;
}