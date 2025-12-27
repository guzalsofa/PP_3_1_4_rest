package guzalsofa.pp_3_1_4_rest.controller;

import guzalsofa.pp_3_1_4_rest.dto.UserCreateDto;
import guzalsofa.pp_3_1_4_rest.dto.UserDto;
import guzalsofa.pp_3_1_4_rest.mapper.UserMapper;
import guzalsofa.pp_3_1_4_rest.model.User;
import guzalsofa.pp_3_1_4_rest.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/users")
public class AdminController {

    private UserService userService;
    private final UserMapper userMapper;

    public AdminController(UserService userService,  UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(userMapper.toDtos(users));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        UserDto userDto = userMapper.toDto(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        if(userCreateDto==null) {
            return ResponseEntity.badRequest().build();
        }
        if (userCreateDto.getPassword() == null || userCreateDto.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        User user = userMapper.toUser(userCreateDto);
        User saveUser = userService.addUser(user);
        UserDto userDtoSaved = userMapper.toDto(saveUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(userDtoSaved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,
                                              @RequestBody  UserDto userDto) {
        if(userDto==null) {
            return ResponseEntity.badRequest().build();
        }
        User existing = userService.findById(id);
        if (existing == null) {
            return ResponseEntity.notFound().build();
        }
        userMapper.updateEntityFromDto(userDto, existing);
        User updated = userService.updateUser(existing);
        return ResponseEntity.ok(userMapper.toDto(updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = userService.findById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
