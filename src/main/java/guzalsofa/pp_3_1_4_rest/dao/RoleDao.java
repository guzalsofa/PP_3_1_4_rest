package guzalsofa.pp_3_1_4_rest.dao;



import guzalsofa.pp_3_1_4_rest.model.Role;

import java.util.Set;

public interface RoleDao {
    Set<Role> findAllRoles();
    Role findByRole(String role);
    void addRole(Role role);
}
