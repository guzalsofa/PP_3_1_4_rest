package guzalsofa.pp_3_1_4_rest.service;



import guzalsofa.pp_3_1_4_rest.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> findAllRoles();
    Role findRoleByName(String name);
    void addRole(Role role);
}
