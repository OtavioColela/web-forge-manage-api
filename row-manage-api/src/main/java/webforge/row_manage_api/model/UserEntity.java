package webforge.row_manage_api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String school;
    private String cpf;
    private String password;
    private String role;

    public UserEntity(String email, String name, String school, String password, String role, String cpf) {
        this.email = email;
        this.name = name;
        this.school = school;
        this.password = password;
        this.role = role;
        this.cpf = cpf;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSchool() {
        return school;
    }

    public String getCpf() {
        return cpf;
    }

    public String getRole() {
        return role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(role.equals("SUPER_USER")){
            return List.of(new SimpleGrantedAuthority("ADMIN") , new SimpleGrantedAuthority("USER"),  new SimpleGrantedAuthority("SUPER_USER"));
        } else if (role.equals("ADMIN")) {
            return List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("USER"));
        }else{
            return Collections.singletonList(new SimpleGrantedAuthority("USER"));
        }
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
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

    @Override
    public boolean isEnabled() {
        return true;
    }
}
