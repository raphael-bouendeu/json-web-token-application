package com.itbcafrica.jwtapplication.controller;


import com.itbcafrica.jwtapplication.model.RoleModel;
import com.itbcafrica.jwtapplication.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping("/roles")
    public RoleModel createRole(@RequestBody RoleModel roleModel) {
        return roleService.createRole( roleModel );
    }

    @GetMapping("/roles")
    public List<RoleModel> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GetMapping("/roles/{roleId}")
    public RoleModel getRoleById(@PathVariable("roleId") Long roleId) {
        return roleService.getRoleById( roleId );
    }

    @DeleteMapping("/roles/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId) {
        roleService.deleteRoleById( roleId );
    }
}
