package com.cgr.service;

import com.cgr.entity.DTO.LoginDTO;
import com.cgr.entity.ResponseModel;

public interface LoginService {
    ResponseModel checkLogin(LoginDTO loginDTO);
}
