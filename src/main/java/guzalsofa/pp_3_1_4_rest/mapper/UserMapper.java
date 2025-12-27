package guzalsofa.pp_3_1_4_rest.mapper;

import guzalsofa.pp_3_1_4_rest.dto.UserCreateDto;
import guzalsofa.pp_3_1_4_rest.dto.UserDto;
import guzalsofa.pp_3_1_4_rest.model.Role;
import guzalsofa.pp_3_1_4_rest.model.User;
import guzalsofa.pp_3_1_4_rest.service.RoleService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class UserMapper {
    private final RoleService roleService;

    public UserMapper(RoleService roleService) {
        this.roleService = roleService;
    }

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        userDto.setAge(user.getAge());
        userDto.setJob(user.getJob());
        Set<String> rolesDto = new HashSet<>();
        Set<Role> rolesEntity = user.getRole();
        if (rolesEntity != null) {
            for (Role role : rolesEntity) {
                rolesDto.add(role.getRole());}
        }
        userDto.setRole(rolesDto);
        return userDto;
    }
    public List<UserDto> toDtos(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            if (user == null) {
                continue;
            }
            userDtos.add(toDto(user));
        }
        return userDtos;
    }
    public User toUser (UserDto userDto) {
        if (userDto == null) {
            return null;
        }
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setJob(userDto.getJob());
        Set<Role> roles = new HashSet<>();
        if (userDto.getRole() != null) {
            for (String roleName : userDto.getRole()) {
                Role role = roleService.findRoleByName(roleName);
                if (role != null) {
                    roles.add(role);
                }
        }
        }
        user.setRole(roles);
        return user;
    }

    public User toUser (UserCreateDto userCreateDto) {
        if (userCreateDto == null) {
            return null;
        }
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setName(userCreateDto.getName());
        user.setAge(userCreateDto.getAge());
        user.setJob(userCreateDto.getJob());
        Set<Role> roles = new HashSet<>();
        if (userCreateDto.getRole() != null) {
            for (String roleName : userCreateDto.getRole()) {
                Role role = roleService.findRoleByName(roleName);
                if (role != null) {
                    roles.add(role);
                }
            }
        }
        user.setRole(roles);
        return user;
    }
     public User updateEntityFromDto (UserDto userDto, User user){
        if(user == null){
            return null;
        }
        if (userDto == null) {
            return user;
        }
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());
        user.setJob(userDto.getJob());
        if(userDto.getRole() != null){
            Set<Role> roles = new HashSet<>();
            for (String roleName : userDto.getRole()) {
                Role role = roleService.findRoleByName(roleName);
                if (role != null) {
                    roles.add(role);
                }
            }
            user.setRole(roles);
        }
        return user;
     }
}
