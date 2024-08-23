package com.soyaa.boardserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDTO {

    public enum Status{
        DEFAULT,ADMIN,DELETED
    }
    private int id;
    private String userId;
    private String password;
    private String nickname;
    private boolean isAdmin;
    private Date createTime;
    private boolean isWithDraw;
    private Status status;

    public static boolean hasNullDataBeforeSignup(UserDTO userDTO) {
        return userDTO.getUserId() == null || userDTO.getPassword() == null
                || userDTO.getNickname() == null;
    }
}
