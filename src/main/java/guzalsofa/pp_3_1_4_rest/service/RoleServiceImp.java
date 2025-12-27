package guzalsofa.pp_3_1_4_rest.service;

import guzalsofa.pp_3_1_4_rest.dao.RoleDao;
import guzalsofa.pp_3_1_4_rest.model.Role;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Set;

@Service
public class RoleServiceImp implements RoleService {
    private final RoleDao roleDao;

    public RoleServiceImp(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> findAllRoles() {
        return roleDao.findAllRoles();
    }

    @Override
    @Transactional(readOnly = true)
    public Role findRoleByName(String name) {
        return roleDao.findByRole(name);
    }

    @Override
    @Transactional
    public void addRole(Role role) {
        roleDao.addRole(role);
    }
}
