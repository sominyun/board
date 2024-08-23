package com.soyaa.boardserver.mapper;

import com.soyaa.boardserver.dto.PostDTO;
import com.soyaa.boardserver.dto.request.PostSearchRequest;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostSearchMapper {
    public List<PostDTO> selectPosts(PostSearchRequest postSearchRequest);
}
