package guzalsofa.pp_3_1_4_rest.dao;

import guzalsofa.pp_3_1_4_rest.model.Role;
import org.springframework.stereotype.Repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class RoleDaoImp implements RoleDao {
    @PersistenceContext
    private EntityManager em;

    @Override
    public Set<Role> findAllRoles() {
        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r", Role.class);
        return query.getResultStream()
                .collect(Collectors.toSet());
    }

    @Override
    public Role findByRole(String role) {
        TypedQuery<Role> query = em.createQuery("SELECT r FROM Role r where r.role= :role", Role.class);
        return query.setParameter("role", role)
                .getResultStream()
                .findFirst()
                .orElse(null);
        }

    @Override
    public void addRole(Role role) {
        em.persist(role);
    }
}
