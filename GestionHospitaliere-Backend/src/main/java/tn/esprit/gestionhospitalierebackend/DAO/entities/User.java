package tn.esprit.gestionhospitalierebackend.DAO.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idUser;

    String firstName;
    String lastName;
    String username;
    String password;
    int phoneNumber;
    String email;

    @Temporal(TemporalType.DATE)
    Date dateNaissance;
    @Temporal(TemporalType.DATE)
    Date dateInscription;
    boolean accountNonLocked;

    @ManyToOne
    private Role role;
    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
    List<Token> tokens;

    public List<Token> getTokens() {
        return tokens;
    }

    public void setTokens(List<Token> tokens) {
        this.tokens = tokens;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return List.of(new SimpleGrantedAuthority(role.name()));
       // return new SimpleGrantedAuthority(role.getRoleName());
        return List.of(new SimpleGrantedAuthority(role.getRoleName()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
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
