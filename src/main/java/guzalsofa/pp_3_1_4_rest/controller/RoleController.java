package guzalsofa.pp_3_1_4_rest.controller;

import guzalsofa.pp_3_1_4_rest.model.Role;
import guzalsofa.pp_3_1_4_rest.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {this.roleService = roleService;}

   @GetMapping
   public ResponseEntity<Set<Role>> findAll() {
        return ResponseEntity.ok(roleService.findAllRoles());
   }
}
