package com.example.cellphonesclone.services;

import com.example.cellphonesclone.DTO.UpdateUserDTO;
import com.example.cellphonesclone.DTO.UserDTO;
import com.example.cellphonesclone.exceptions.DataNotFoundException;
import com.example.cellphonesclone.models.Role;
import com.example.cellphonesclone.models.User;
import com.example.cellphonesclone.respositories.RoleRepository;
import com.example.cellphonesclone.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public User createUser(UserDTO userDTO) throws Exception {
        String phoneNumber = userDTO.getPhoneNumber();

        // Check if phone number already exists
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        Role role = roleRepository.findById(userDTO.getRoleID())
                .orElseThrow(() -> new DataNotFoundException("Role does not exist"));

        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getUserAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountID(userDTO.getFacebookAccountID())
                .googleAccountID(userDTO.getGoogleAccountID())
                .active(true)
                .build();

        newUser.setRole(role);

        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password, Long roleId) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

        if(optionalUser.isEmpty()) {
            throw new DataNotFoundException("Invalid phone number or password");
        }

        User existingUser = optionalUser.get();

        Optional<Role> optionalRole = roleRepository.findById(roleId);
        if(optionalRole.isEmpty() || !roleId.equals(existingUser.getRole().getId())) {
            throw new DataNotFoundException("Invalid role");
        }

        if(!optionalUser.get().isActive()) {
            throw new DataNotFoundException("User account is locked");
        }

        // Simple password check (remove password encoding)
        if (!password.equals(existingUser.getPassword())) {
            throw new DataNotFoundException("Invalid phone number or password");
        }

        // Return a simple token or user ID instead of JWT
        return existingUser.getId().toString();
    }

    @Transactional
    @Override
    public User updateUser(Long userId, UpdateUserDTO updatedUserDTO) throws Exception {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new DataNotFoundException("User not found"));

        String newPhoneNumber = updatedUserDTO.getPhoneNumber();
        if (!existingUser.getPhoneNumber().equals(newPhoneNumber) &&
                userRepository.existsByPhoneNumber(newPhoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exists");
        }

        if (updatedUserDTO.getFullName() != null) {
            existingUser.setFullName(updatedUserDTO.getFullName());
        }
        if (newPhoneNumber != null) {
            existingUser.setPhoneNumber(newPhoneNumber);
        }
        if (updatedUserDTO.getAddress() != null) {
            existingUser.setAddress(updatedUserDTO.getAddress());
        }
        if (updatedUserDTO.getDateOfBirth() != null) {
            existingUser.setDateOfBirth(updatedUserDTO.getDateOfBirth());
        }
        if (updatedUserDTO.getFacebookAccountId() > 0) {
            existingUser.setFacebookAccountID(updatedUserDTO.getFacebookAccountId());
        }
        if (updatedUserDTO.getGoogleAccountId() > 0) {
            existingUser.setGoogleAccountID(updatedUserDTO.getGoogleAccountId());
        }

        // Simple password update without encoding
        if (updatedUserDTO.getPassword() != null
                && !updatedUserDTO.getPassword().isEmpty()) {
            if(!updatedUserDTO.getPassword().equals(updatedUserDTO.getRetypePassword())) {
                throw new DataNotFoundException("Password and retype password not the same");
            }
            existingUser.setPassword(updatedUserDTO.getPassword());
        }

        return userRepository.save(existingUser);
    }

    @Override
    public User getUserDetailsFromToken(String token) throws Exception {
        // Simple token validation (replace with your token management)
        Optional<User> user = userRepository.findById(Long.parseLong(token));

        if (user.isPresent()) {
            return user.get();
        } else {
            throw new Exception("User not found");
        }
    }
}