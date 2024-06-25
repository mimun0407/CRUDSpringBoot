package com.example.demo.service;

import com.example.demo.Dto.PermissionDto;
import com.example.demo.Model.Permission;
import com.example.demo.Response.PermissionResponse;
import com.example.demo.repository.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PermissionService {
    @Autowired
    PermissionRepository permissionRepository;
    public PermissionResponse create(PermissionDto permissionDto){
        Permission permission = new Permission();
        permission.setName(permissionDto.getName());
        permission.setDescription(permissionDto.getDescription());
        permissionRepository.save(permission);
        PermissionResponse permissionResponse = new PermissionResponse();
        permissionResponse.setName(permissionResponse.getName());
        permissionResponse.setDescription(permissionResponse.getDescription());
        return permissionResponse;
    }
    public List<Permission> findAll( ){
       return permissionRepository.findAll();
    }
    public void delete(String permissionName){
        permissionRepository.deleteById(permissionName);
    }
}
