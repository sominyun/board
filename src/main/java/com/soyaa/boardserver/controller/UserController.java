package com.soyaa.boardserver.controller;

import com.soyaa.boardserver.aop.LoginCheck;
import com.soyaa.boardserver.dto.UserDTO;
import com.soyaa.boardserver.dto.request.UserDeleteId;
import com.soyaa.boardserver.dto.request.UserLoginRequest;
import com.soyaa.boardserver.dto.request.UserUpdatePasswordRequest;
import com.soyaa.boardserver.dto.response.UserInfoResponse;
import com.soyaa.boardserver.dto.response.UserLoginResponse;
import com.soyaa.boardserver.service.impl.UserServiceImpl;
import com.soyaa.boardserver.util.SessionUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Log4j2
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PostMapping("sign-up")
    @ResponseStatus(HttpStatus.CREATED)
    public void signUp(@RequestBody UserDTO userDTO) {
        if (UserDTO.hasNullDataBeforeSignup(userDTO)) {
            throw new NullPointerException("회원가입시 필수 데이터를 모두 입력해야 합니다.");
        }
        userService.register(userDTO);
    }

    @PostMapping("sign-in")
    public HttpStatus login(@RequestBody UserLoginRequest loginRequest, HttpSession session) {
        ResponseEntity<UserLoginResponse> responseEntity = null;
        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();
        UserDTO userInfo = userService.login(userId, password);
        String id = String.valueOf(userInfo.getId());

        if (userInfo == null) {
            return HttpStatus.NOT_FOUND;
        } else if (userInfo != null) {
            UserLoginResponse loginResponse = UserLoginResponse.success(userInfo);
            if (userInfo.getStatus() == (UserDTO.Status.ADMIN)) //관리자페이지
                SessionUtil.setLoginAdminId(session, id);
            else
                SessionUtil.setLoginMemberId(session, id); // 멤버

            responseEntity = new ResponseEntity<UserLoginResponse>(loginResponse, HttpStatus.OK);
        } else {
            throw new RuntimeException("Login Error! 유저 정보가 없거나 지워진 유저 정보입니다.");
        }

        return HttpStatus.OK;
    }

    @GetMapping("my-info")
    public UserInfoResponse memberInfo(HttpSession session) {
        String id = SessionUtil.getLoginMemberId(session);
        if (id == null) id = SessionUtil.getLoginAdminId(session);
        UserDTO memberInfo = userService.getUserInfo(id);
        return new UserInfoResponse(memberInfo);
    }

    @PutMapping("logout")
    public void logout(HttpSession session) {
        SessionUtil.clear(session);
    }

    @PatchMapping("password")
    @LoginCheck(type = LoginCheck.UserType.USER)
    public ResponseEntity<UserLoginResponse> updateUserPassword(String accountId, @RequestBody UserUpdatePasswordRequest userUpdatePasswordRequest,
                                                            HttpSession session) {
        ResponseEntity<UserLoginResponse> responseEntity = null;
        String beforePassword = userUpdatePasswordRequest.getBeforePassword();
        String afterPassword = userUpdatePasswordRequest.getAfterPassword();

        try {
            userService.updatePassword(accountId, beforePassword, afterPassword);
            UserDTO userInfo = userService.login(accountId, afterPassword);
            UserLoginResponse loginResponse = UserLoginResponse.success(userInfo);
            ResponseEntity.ok(new ResponseEntity<UserLoginResponse>(loginResponse, HttpStatus.OK));
        } catch (IllegalArgumentException e) {
            log.error("updatePassword 실패", e);
            responseEntity = new ResponseEntity<UserLoginResponse>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @DeleteMapping
    public ResponseEntity<UserLoginResponse> deleteId(@RequestBody UserDeleteId userDeleteId,
                                                  HttpSession session) {
        ResponseEntity<UserLoginResponse> responseEntity = null;
        String Id = SessionUtil.getLoginMemberId(session);

        try {
            UserDTO userInfo = userService.login(Id, userDeleteId.getPassword());
            userService.deleteId(Id, userDeleteId.getPassword());
            UserLoginResponse loginResponse = UserLoginResponse.success(userInfo);
            responseEntity = new ResponseEntity<UserLoginResponse>(loginResponse, HttpStatus.OK);
        } catch (RuntimeException e) {
            log.info("deleteID 실패");
            responseEntity = new ResponseEntity<UserLoginResponse>(HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

}
