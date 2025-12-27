package guzalsofa.pp_3_1_4_rest.service;


import guzalsofa.pp_3_1_4_rest.dao.UserDao;
import guzalsofa.pp_3_1_4_rest.model.Role;
import guzalsofa.pp_3_1_4_rest.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
public class UserServiceImp implements UserService {
    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserServiceImp(UserDao userDao, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }
    @Transactional(readOnly = true)
    @Override
    public List<User> findAll() {
         return userDao.findAll();
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return userDao.findById(id);
    }

    @Transactional
    @Override
    public User addUser(User user) {
        if (user.getRole() == null || user.getRole().isEmpty()) {
            Role defaultRole = roleService.findRoleByName("ROLE_USER");
            if (defaultRole != null) {
                user.getRole().add(defaultRole);
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(">>> BEFORE SAVE user = " + user);
        userDao.addUser(user);
        System.out.println(">>> AFTER SAVE user = " + user);
        return  user;
    }

    @Transactional
    @Override
    public User updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByUsername(String username){ return userDao.findUserByUsername(username);}
}
