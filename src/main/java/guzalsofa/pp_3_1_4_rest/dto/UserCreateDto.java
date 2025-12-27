package guzalsofa.pp_3_1_4_rest.dto;

import guzalsofa.pp_3_1_4_rest.model.User;

import java.util.HashSet;
import java.util.Set;

public class UserCreateDto {

    private String username;
    private String password;
    private String name;
    private Integer age;
    private String job;
    private Set<String> role = new HashSet<>();

    public UserCreateDto (String username, String password,
                          String name, Integer age, String job,
                          Set<String> role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.job = job;
        this.role = role;
    }

    public UserCreateDto() {}

    public String getUsername() {return username;}

    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public Integer getAge() {return age;}

    public void setAge(Integer age) {this.age = age;}

    public String getJob() {return job;}

    public void setJob(String job) {this.job = job;}

    public Set<String> getRole() {return role;}

    public void setRole(Set<String> role) {this.role = role;}

}
