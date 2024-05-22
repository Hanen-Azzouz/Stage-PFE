package tn.esprit.gestionhospitalierebackend.DAO.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import tn.esprit.gestionhospitalierebackend.DAO.enums.TypeRole;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idRole;
    @Enumerated(value = EnumType.STRING)
    TypeRole roleName;
    @JsonIgnore
    @OneToMany(mappedBy = "role",cascade = CascadeType.ALL)
    private List<User> users;

    public String getRoleName() {
        return roleName.name();
    }


}
