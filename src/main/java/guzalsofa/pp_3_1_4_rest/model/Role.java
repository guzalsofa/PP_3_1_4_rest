package guzalsofa.pp_3_1_4_rest.model;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="role", unique = true, nullable = false)
    private String role;

    public Role() {
    }

    public Role(String role) {
        this.role = role;
    }

    public Long getId() {return id;}

    public String getRole() {return role;}

    public void setRole(String role) {this.role = role;}


    @Override
    public String getAuthority() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        Role other = (Role) o;
        return Objects.equals(role, other.role);
    }
    @Override
    public int hashCode() {
        return Objects.hash(role);
    }
    @Override
    public String toString() {
        return role;
    }
}
