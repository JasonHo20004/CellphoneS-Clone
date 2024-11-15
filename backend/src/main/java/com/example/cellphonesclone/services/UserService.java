package com.example.cellphonesclone.services;

import com.example.cellphonesclone.DTO.UserDTO;
import com.example.cellphonesclone.components.JwtTokenUtil;
import com.example.cellphonesclone.exceptions.DataNotFoundException;
import com.example.cellphonesclone.models.Role;
import com.example.cellphonesclone.models.User;
import com.example.cellphonesclone.respositories.RoleRepository;
import com.example.cellphonesclone.respositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDTO userDTO;
    private final AuthenticationManager authenticationManager;
    @Override
    //register user
    public User createUser(UserDTO userDTO) throws DataNotFoundException {
        String phoneNumber = userDTO.getPhoneNumber();
        if(userRepository.existsByPhoneNumber(phoneNumber)) {
            throw new DataIntegrityViolationException("Phone number already exist.");
        }
        User newUser = User.builder()
                .fullName(userDTO.getFullName())
                .phoneNumber(userDTO.getPhoneNumber())
                .password(userDTO.getPassword())
                .address(userDTO.getUserAddress())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccountID(userDTO.getFacebookAccountID())
                .googleAccountID(userDTO.getGoogleAccountID())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleID()).orElseThrow(()->new DataNotFoundException("Role not found"));
        newUser.setRole(role);
        //Kiem tra dang nhap bang google, fb thi kh can password
        if(userDTO.getFacebookAccountID()==0 && userDTO.getGoogleAccountID()==0){
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    @Override
    public String login(String phoneNumber, String password) throws Exception {
        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);
        if (optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid phoneNumber/ password");
        }
        //return optionalUser.get(); //Muon tra JWT token
        User existingUser = optionalUser.get();
        //check password
        if(userDTO.getFacebookAccountID() == 0 && userDTO.getGoogleAccountID()==0){
            if(!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new BadCredentialsException("Wrong phone number or password!");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                phoneNumber, password
        );
        //authenticate with java spring security
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }
}
