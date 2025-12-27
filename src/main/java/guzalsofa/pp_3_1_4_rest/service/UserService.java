package guzalsofa.pp_3_1_4_rest.service;



import guzalsofa.pp_3_1_4_rest.model.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User addUser(User user);
    User updateUser(User user);
    void deleteUser(Long id);
    User findByUsername(String username);
}
