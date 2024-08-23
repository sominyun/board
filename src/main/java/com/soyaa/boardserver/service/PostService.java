package com.soyaa.boardserver.service;

import com.soyaa.boardserver.dto.CommentDTO;
import com.soyaa.boardserver.dto.PostDTO;
import com.soyaa.boardserver.dto.TagDTO;

import java.util.List;

public interface PostService {
    void register(String id, PostDTO postDTO);

    List<PostDTO> getMyProducts(int accountId);

    void updateProducts(PostDTO postDTO);

    void deleteProduct(int userId, int productId);
    //----------------comment----------------------------
    void registerComment(CommentDTO commentDTO);

    void updateComment(CommentDTO commentDTO);

    void deletePostComment(int userId, int commentId);
    //----------------tag----------------------------------
    void registerTag(TagDTO tagDTO);

    void updateTag(TagDTO tagDTO);

    void deletePostTag(int userId, int tagId);
}
