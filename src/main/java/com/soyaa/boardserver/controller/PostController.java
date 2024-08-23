package com.soyaa.boardserver.controller;

import com.soyaa.boardserver.aop.LoginCheck;
import com.soyaa.boardserver.dto.CommentDTO;
import com.soyaa.boardserver.dto.PostDTO;
import com.soyaa.boardserver.dto.TagDTO;
import com.soyaa.boardserver.dto.UserDTO;
import com.soyaa.boardserver.dto.request.PostDeleteRequest;
import com.soyaa.boardserver.dto.request.PostRequest;
import com.soyaa.boardserver.dto.response.CommonResponse;
import com.soyaa.boardserver.service.impl.PostServiceImpl;
import com.soyaa.boardserver.service.impl.UserServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/posts")
@Log4j2
public class PostController {

    private final PostServiceImpl postService;
    private final UserServiceImpl userService;

    public PostController(PostServiceImpl postService, UserServiceImpl userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> registerPost(String accountId, @RequestBody PostDTO postDTO) {
        postService.register(accountId, postDTO);
        CommonResponse<PostDTO> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPost", postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @GetMapping("my-posts")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<List<PostDTO>>> myPostInfo(String accountId) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        List<PostDTO> postDTOList = postService.getMyProducts(memberInfo.getId());
        CommonResponse<List<PostDTO>> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "myPostInfo", postDTOList);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDTO>> updatePosts(String accountId,
                                                                   @PathVariable(name = "postId") int postId, @RequestBody PostRequest postRequest) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        PostDTO postDTO = PostDTO.builder()
                .id(postId)
                .name(postRequest.getName())
                .contents(postRequest.getContents())
                .views(postRequest.getViews())
                .categoryId(postRequest.getCategoryId())
                .userId(memberInfo.getId())
                .fileId(postRequest.getFileId())
                .updateTime(new Date())
                .build();
        postService.updateProducts(postDTO);
        CommonResponse<PostDTO> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePosts", postDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("{postId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<PostDeleteRequest>> deleteposts(String accountId,
                                                                         @PathVariable(name = "postId") int postId,
                                                                         @RequestBody PostDeleteRequest postDeleteRequest) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        postService.deleteProduct(memberInfo.getId(), postId);
        CommonResponse<PostDeleteRequest> commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deleteposts", postDeleteRequest);
        return ResponseEntity.ok(commonResponse);
    }

    // -------------- comments --------------

    @PostMapping("comments")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> registerPostComment(String accountId, @RequestBody CommentDTO commentDTO) {
        postService.registerComment(commentDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> updatePostComment(String accountId,
                                                                        @PathVariable(name = "commentId") int commentId,
                                                                        @RequestBody CommentDTO commentDTO) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null)
            postService.updateComment(commentDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("comments/{commentId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<CommentDTO>> deletePostComment(String accountId,
                                                                        @PathVariable(name = "commentId") int commentId,
                                                                        @RequestBody CommentDTO commentDTO) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null)
            postService.deletePostComment(memberInfo.getId(), commentId);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deletePostComment", commentDTO);
        return ResponseEntity.ok(commonResponse);
    }

    // -------------- tags --------------
    @PostMapping("tags")
    @ResponseStatus(HttpStatus.CREATED)
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> registerPostTag(String accountId, @RequestBody TagDTO tagDTO) {
        postService.registerTag(tagDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "registerPostTag", tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @PatchMapping("tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> updatePostTag(String accountId,
                                                                @PathVariable(name = "tagId") int tagId,
                                                                @RequestBody TagDTO tagDTO) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null)
            postService.updateTag(tagDTO);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "updatePostTag", tagDTO);
        return ResponseEntity.ok(commonResponse);
    }

    @DeleteMapping("tags/{tagId}")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<CommonResponse<TagDTO>> deletePostTag(String accountId,
                                                                @PathVariable(name = "tagId") int tagId,
                                                                @RequestBody TagDTO tagDTO) {
        UserDTO memberInfo = userService.getUserInfo(accountId);
        if(memberInfo != null)
            postService.deletePostTag(memberInfo.getId(), tagId);
        CommonResponse commonResponse = new CommonResponse<>(HttpStatus.OK, "SUCCESS", "deletePostTag", tagDTO);
        return ResponseEntity.ok(commonResponse);
    }
}