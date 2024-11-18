package com.example.cellphonesclone.services;

import com.example.cellphonesclone.dtos.CategoryDTO;
import com.example.cellphonesclone.models.Category;
import com.example.cellphonesclone.models.Role;
import com.example.cellphonesclone.repositories.CategoryRepository;
import com.example.cellphonesclone.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService implements IRoleService{
    private final RoleRepository roleRepository;
    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}
