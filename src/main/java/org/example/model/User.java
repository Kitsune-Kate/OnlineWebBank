package org.example.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //создали автоинкремент   @GeneratedValue
    private int id;
    private String name;
    private String firstName;
    private String username;
    private String password;
    @ElementCollection(fetch = FetchType.EAGER) //один ко многим,таблица с вложенной таблицей
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();
    private boolean active;
    private String filename;


    public String getStatus(boolean active) {
        if (!active) {
            return "banned";
        }
        return "not banned";
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
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
        return isActive();
    }
}
