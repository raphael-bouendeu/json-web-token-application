package com.itbcafrica.jwtapplication.service;

import com.itbcafrica.jwtapplication.entity.RoleEntity;
import com.itbcafrica.jwtapplication.entity.UserEntity;
import com.itbcafrica.jwtapplication.model.RoleModel;
import com.itbcafrica.jwtapplication.model.UserModel;
import com.itbcafrica.jwtapplication.repository.RoleRepository;
import com.itbcafrica.jwtapplication.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity byUsername = userRepository.findByUsername( username );

        if (byUsername == null) {
            throw new UsernameNotFoundException( "User not exist" );
        }
        UserModel userModel = new UserModel();
        BeanUtils.copyProperties( byUsername, userModel );
        Set<RoleModel> roleModels = new HashSet<>();
        RoleModel roleModel = null;
        for (RoleEntity roleEntity : byUsername.getRoles()) {
            roleModel = new RoleModel();
            roleModel.setRoleName( roleEntity.getRoleName() );
            roleModel.setId( roleEntity.getId() );
            roleModels.add( roleModel );
        }
        userModel.setRoles( roleModels );
        return userModel;
    }

    public UserModel register(UserModel userModel) {
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties( userModel, userEntity );// it does not do a deep copy
        Set<RoleEntity> roleEntities = new HashSet<>();
        // fecht every roles from db based on role id and than set this role to user entity roles
        for (RoleModel roleModel : userModel.getRoles()) {
            Optional<RoleEntity> roleEntity = roleRepository.findById( roleModel.getId() );
            if (roleEntity.isPresent()) {
                roleEntities.add( roleEntity.get() );
            }
        }
        userEntity.setRoles( roleEntities );
        userEntity.setPassword( passwordEncoder.encode( userModel.getPassword() ) );
        UserEntity save = userRepository.save( userEntity );
        BeanUtils.copyProperties( save, userModel );
        Set<RoleModel> roleModels = new HashSet<>();
        RoleModel roleModel = null;
        for (RoleEntity roleEntity : save.getRoles()) {
            roleModel = new RoleModel();
            roleModel.setRoleName( roleEntity.getRoleName() );
            roleModel.setId( roleEntity.getId() );
            roleModels.add( roleModel );
        }
        userModel.setRoles( roleModels );
        return userModel;
    }
}
