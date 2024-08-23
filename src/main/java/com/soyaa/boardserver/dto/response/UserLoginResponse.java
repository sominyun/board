package com.soyaa.boardserver.dto.response;

import com.soyaa.boardserver.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class UserLoginResponse {
    enum LoginStatus {
        SUCCESS, FAIL, DELETED
    }

    @NonNull
    private LoginStatus result;
    private UserDTO userDTO;

    private static final UserLoginResponse FAIL = new UserLoginResponse(LoginStatus.FAIL);

    public static UserLoginResponse success(UserDTO userDTO) {
        return new UserLoginResponse(LoginStatus.SUCCESS, userDTO);
    }
}