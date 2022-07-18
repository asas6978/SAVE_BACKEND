package com.save_backend.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PutUserInfoReq {

    private int userIdx;
    private String name;
    private String phone;
    private String email;
}
