package guzalsofa.pp_3_1_4_rest.service;

import guzalsofa.pp_3_1_4_rest.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImp implements UserDetailsService {

    private final UserService userService;

    public UserDetailsServiceImp(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username '" + username + "' not found");
        }
        user.getRole().size();
        return user;
    }
}
