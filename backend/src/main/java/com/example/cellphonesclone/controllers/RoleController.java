package com.example.cellphonesclone.controllers;


import com.example.cellphonesclone.models.Role;
import com.example.cellphonesclone.services.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("${api.prefix}/roles")
@RequiredArgsConstructor
public class RoleController  {
    private final RoleService roleService;
    @GetMapping("")
    public ResponseEntity<?> getAllRoles(){
        List<Role> roles = roleService.getAllRoles();
    return ResponseEntity.ok(roles);
    }
}
