package com.itbcafrica.jwtapplication.service;

import com.itbcafrica.jwtapplication.model.RoleModel;

import java.util.List;

public interface RoleService {
    RoleModel createRole(RoleModel roleModel);

    List<RoleModel> getAllRoles();

    RoleModel getRoleById(Long roleId);

    void deleteRoleById(Long roleId);

}
