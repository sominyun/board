package com.soyaa.boardserver.service.impl;

import com.soyaa.boardserver.dto.CommentDTO;
import com.soyaa.boardserver.dto.PostDTO;
import com.soyaa.boardserver.dto.TagDTO;
import com.soyaa.boardserver.dto.UserDTO;
import com.soyaa.boardserver.mapper.CommentMapper;
import com.soyaa.boardserver.mapper.PostMapper;
import com.soyaa.boardserver.mapper.TagMapper;
import com.soyaa.boardserver.mapper.UserProfileMapper;
import com.soyaa.boardserver.service.PostService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Log4j2
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private UserProfileMapper userProfileMapper;

    @CacheEvict(value="getProducts", allEntries = true)
    @Override
    public void register(String id, PostDTO postDTO) {
        UserDTO memberInfo = userProfileMapper.getUserProfile(id);
        postDTO.setUserId(memberInfo.getId());
        postDTO.setCreateTime(new Date());

        if (memberInfo != null) {
            postMapper.register(postDTO);
            Integer postId = postDTO.getId();
            // 생성된 post 객체 에서 태그 리스트 생성
            for(int i=0; i<postDTO.getTagDTOList().size(); i++){
                TagDTO tagDTO = postDTO.getTagDTOList().get(i);
                tagMapper.register(tagDTO);
                Integer tagId = tagDTO.getId();
                // M:N 관계 테이블 생성
                tagMapper.createPostTag(tagId, postId);
            }
        } else {
            log.error("register ERROR! {}", postDTO);
            throw new RuntimeException("register ERROR! 상품 등록 메서드를 확인해주세요\n" + "Params : " + postDTO);
        }
    }

    @Override
    public List<PostDTO> getMyProducts(int accountId) {
        List<PostDTO> postDTOList = postMapper.selectMyProducts(accountId);
        return postDTOList;
    }

    @Override
    public void updateProducts(PostDTO postDTO) {
        if (postDTO != null && postDTO.getId() != 0 && postDTO.getUserId() != 0) {
            postMapper.updateProducts(postDTO);
        } else {
            log.error("updateProducts ERROR! {}", postDTO);
            throw new RuntimeException("updateProducts ERROR! 물품 변경 메서드를 확인해주세요\n" + "Params : " + postDTO);
        }
    }

    @Override
    public void deleteProduct(int userId, int productId) {
        if (userId != 0 && productId != 0) {
            postMapper.deleteProduct(productId);
        } else {
            log.error("deleteProudct ERROR! {}", productId);
            throw new RuntimeException("updateProducts ERROR! 물품 삭제 메서드를 확인해주세요\n" + "Params : " + productId);
        }
    }
    //-------------------------comment----------------------------------
    @Override
    public void registerComment(CommentDTO commentDTO) {
        if (commentDTO.getPostId() != 0) {
            commentMapper.register(commentDTO);
        } else {
            log.error("registerComment ERROR! {}", commentDTO);
            throw new RuntimeException("registerComment ERROR! 댓글 추가 메서드를 확인해주세요\n" + "Params : " + commentDTO);
        }
    }

    @Override
    public void updateComment(CommentDTO commentDTO) {
        if (commentDTO != null) {
            commentMapper.updateComments(commentDTO);
        } else {
            log.error("updateComment ERROR! {}", commentDTO);
            throw new RuntimeException("updateComment ERROR! 댓글 변경 메서드를 확인해주세요\n" + "Params : " + commentDTO);
        }
    }
    @Override
    public void deletePostComment(int userId, int commentId) {
        if (userId != 0 && commentId != 0) {
            commentMapper.deletePostComment(commentId);
        } else {
            log.error("deletePostComment ERROR! {}", commentId);
            throw new RuntimeException("deletePostComment ERROR! 댓글 삭제 메서드를 확인해주세요\n" + "Params : " + commentId);
        }
    }
    //--------------------------------tag---------------------------------------------
    @Override
    public void registerTag(TagDTO tagDTO) {
        if (tagDTO.getPostId() != 0) {
            tagMapper.register(tagDTO);
        } else {
            log.error("registerTag ERROR! {}", tagDTO);
            throw new RuntimeException("registerTag ERROR! 태그 추가 메서드를 확인해주세요\n" + "Params : " + tagDTO);
        }
    }

    @Override
    public void updateTag(TagDTO tagDTO) {
        if (tagDTO != null) {
            tagMapper.updateTags(tagDTO);
        } else {
            log.error("updateTag ERROR! {}", tagDTO);
            throw new RuntimeException("updateTag ERROR! 태그 변경 메서드를 확인해주세요\n" + "Params : " + tagDTO);
        }
    }

    @Override
    public void deletePostTag(int userId, int tagId) {
        if (userId != 0 && tagId != 0) {
            tagMapper.deletePostTag(tagId);
        } else {
            log.error("deletePostTag ERROR! {}", tagId);
            throw new RuntimeException("deletePostTag ERROR! 태그 삭제 메서드를 확인해주세요\n" + "Params : " + tagId);
        }
    }
}
