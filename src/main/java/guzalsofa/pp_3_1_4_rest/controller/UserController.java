package guzalsofa.pp_3_1_4_rest.controller;

import guzalsofa.pp_3_1_4_rest.dto.UserDto;
import guzalsofa.pp_3_1_4_rest.mapper.UserMapper;
import guzalsofa.pp_3_1_4_rest.model.User;
import guzalsofa.pp_3_1_4_rest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> showUser (@AuthenticationPrincipal User principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(userMapper.toDto(principal));

    }
}
