package com.example.demo.controller;

import com.example.demo.Dto.PermissionDto;
import com.example.demo.Model.Permission;
import com.example.demo.Response.PermissionResponse;
import com.example.demo.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @PostMapping
    public ResponseEntity<PermissionResponse> CreatePermission(@RequestBody PermissionDto permissionDto){
        PermissionResponse permissionResponse =   permissionService.create(permissionDto);
        return ResponseEntity.ok(permissionResponse);
    }
    @GetMapping
    public ResponseEntity<List<Permission>>ListPermission(){
       List< Permission> permission=permissionService.findAll();
       return ResponseEntity.ok(permission);
    }
    @DeleteMapping("/{name}")
    public ResponseEntity<PermissionResponse> DeletePermission(@PathVariable String name){
        permissionService.delete(name);
        return ResponseEntity.ok().build();
    }
}
