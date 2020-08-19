package com.bsdl.service;

import com.bsdl.po.User;

public interface UserService {

    User checkUser(String username, String password);
}
