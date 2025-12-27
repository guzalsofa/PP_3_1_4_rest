package guzalsofa.pp_3_1_4_rest.dto;

import java.util.HashSet;
import java.util.Set;

public class UserDto {

    private Long id;
    private String username;
    private String name;
    private Integer age;
    private String job;
    private Set<String> role = new HashSet<>();

    public UserDto (Long id, String name, Integer age, String job, String username, Set<String> role) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.job = job;
        this.username = username;
        this.role = role;

    }

    public UserDto() {
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getJob() {
        return job;
    }

    public Integer getAge() {
        return age;
    }

    public Set<String> getRole() {
        return role;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setRole(Set<String> role) {this.role = role;}

    public void setUsername(String username) {this.username = username;}

    public void setId(Long id) {this.id = id;}
}
