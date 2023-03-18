package com.itbcafrica.jwtapplication.service;

import com.itbcafrica.jwtapplication.entity.RoleEntity;
import com.itbcafrica.jwtapplication.model.RoleModel;
import com.itbcafrica.jwtapplication.repository.RoleRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void deleteRoleById(Long roleId) {
        roleRepository.deleteById( roleId );
    }

    @Override
    public RoleModel getRoleById(Long roleId) {
        RoleEntity roleEntity = roleRepository.findById( roleId ).get();
        RoleModel roleModel = new RoleModel();
        BeanUtils.copyProperties( roleEntity, roleModel );
        return roleModel;
    }

    @Override
    public RoleModel createRole(RoleModel roleModel) {
        RoleEntity roleEntity = new RoleEntity();
        BeanUtils.copyProperties( roleModel, roleEntity );
        RoleEntity roleEntity1 = roleRepository.save( roleEntity );
        BeanUtils.copyProperties( roleEntity1, roleModel );
        return roleModel;
    }

    @Override
    public List<RoleModel> getAllRoles() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<RoleModel> roleModels = new ArrayList<>();
        RoleModel roleModel = null;
        for (RoleEntity re : roleEntities) {
            roleModel = new RoleModel();
            BeanUtils.copyProperties( re, roleModel );
            roleModels.add( roleModel );
        }
       /*
        roleEntities.forEach( roleEntity -> {
            RoleModel roleModel2= new RoleModel(  );
            BeanUtils.copyProperties( roleEntity,roleModel2 );
            roleModels.add( roleModel2 );
        } );*/

        return roleModels;
    }


}
