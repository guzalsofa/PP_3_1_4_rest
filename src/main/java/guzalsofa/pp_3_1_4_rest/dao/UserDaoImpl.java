package guzalsofa.pp_3_1_4_rest.dao;


import guzalsofa.pp_3_1_4_rest.model.User;
import org.springframework.stereotype.Repository;


import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = em.createQuery("select u from User u", User.class);
        return query.getResultList();
    }

    @Override
    public User findById(Long id) {
        return em.find(User.class, id);
    }

    @Override
    public void addUser(User user) {
        em.persist(user);
    }

    @Override
    public User updateUser(User user) {
        return em.merge(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = em.find(User.class, id);
        if (user != null) {
            em.remove(user);
        }
    }

    @Override
    public User findUserByUsername(String username) {
        return em.createQuery("select u from User u where u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst()
                .orElse(null);

    }
}
