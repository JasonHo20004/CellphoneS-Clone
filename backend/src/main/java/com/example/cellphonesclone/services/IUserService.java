package com.example.cellphonesclone.services;

import com.example.cellphonesclone.DTO.UserDTO;
import com.example.cellphonesclone.exceptions.DataNotFoundException;
import com.example.cellphonesclone.models.User;

public interface IUserService {
    User createUser(UserDTO userDTO) throws DataNotFoundException;
    String login(String phoneNumber, String password) throws Exception;
}
