package com.soyaa.boardserver.controller;

import com.soyaa.boardserver.dto.PostDTO;
import com.soyaa.boardserver.dto.request.PostSearchRequest;
import com.soyaa.boardserver.dto.response.PostSearchResponse;
import com.soyaa.boardserver.service.impl.PostSearchServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@Log4j2
@RequiredArgsConstructor
public class PostSearchController {

    private final PostSearchServiceImpl postSearchService;

    @PostMapping
    public PostSearchResponse search(@RequestBody PostSearchRequest postSearchRequest) {
        List<PostDTO> postDTOList = postSearchService.getProducts(postSearchRequest);
        return new PostSearchResponse(postDTOList);
    }
}
