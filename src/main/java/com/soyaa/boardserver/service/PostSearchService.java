package com.soyaa.boardserver.service;

import com.soyaa.boardserver.dto.PostDTO;
import com.soyaa.boardserver.dto.request.PostSearchRequest;

import java.util.List;

public interface PostSearchService {
    List<PostDTO> getProducts(PostSearchRequest postSearchRequest);
}
