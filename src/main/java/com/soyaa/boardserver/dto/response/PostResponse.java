package com.soyaa.boardserver.dto.response;

import com.soyaa.boardserver.dto.PostDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostResponse {
    private List<PostDTO> postDTO;
}
