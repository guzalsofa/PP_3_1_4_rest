package guzalsofa.pp_3_1_4_rest.dao;




import guzalsofa.pp_3_1_4_rest.model.User;

import java.util.List;

public interface UserDao {
    List<User> findAll();
    User findById(Long id);
    void addUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    User findUserByUsername(String username);

}
