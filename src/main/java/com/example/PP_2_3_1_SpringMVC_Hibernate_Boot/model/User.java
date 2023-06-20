package com.example.PP_2_3_1_SpringMVC_Hibernate_Boot.model;



import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.*;


@Entity
@Table(name = "user_security")
public class User implements UserDetails {
//
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "First_Name should not be empty")
    @Size(min = 2, max = 30, message = "First_Name should be between 2 and 30 characters")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last_Name should not be empty")
    @Size(min = 2, max = 30, message = "last_Name should be between 2 and 30 characters")
    private String lastName;

    @Column(name = "age")
    @Min(value = 0, message = "Age should be greater than 0")
    @NotEmpty(message = "Age should be greater than 0")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;

    @Column(name = "passw")
    private String passw;

    @Column(name = "enabled")
    private boolean enabled;


    public User() {
    }

    public User(Long id, String firstName, String lastName, int age, String email, String passw,
                boolean enabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.passw = passw;
        this.enabled = enabled;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    //метод для добавления роли пользователю
    public void addRole(Role role) {
        this.roles.add(role);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassw() {
        return passw;
    }

    public void setPassw(String passw) {
        this.passw = passw;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", passw='" + passw + '\'' +
                ", enabled=" + enabled +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() && getAge() == user.getAge() && isEnabled() == user.isEnabled() && Objects.equals(getFirstName(), user.getFirstName()) && Objects.equals(getLastName(), user.getLastName()) && Objects.equals(getEmail(), user.getEmail()) && Objects.equals(getPassw(), user.getPassw());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getFirstName(), getLastName(), getAge(), getEmail(), getPassw(), isEnabled());
    }

   // Имплементация методов UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role roleFromSet : roles) {
            //добавляем в список authorities наименования ролей, связанных с пользователем,
            //получаемые через метод getAuthority() интерфейса
            //org.springframework.security.core.GrantedAuthority, который реализовали в
            //сущности Role. Метод возвращает значние String поля Role.name
            authorities.add(new SimpleGrantedAuthority(roleFromSet.getAuthority()));
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return getPassw();
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
