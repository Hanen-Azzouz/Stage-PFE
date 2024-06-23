package tn.esprit.gestionhospitalierebackend.DAO.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level=AccessLevel.PRIVATE)
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer idPatient;
    String firstName;
    String lastName;
    @Temporal(TemporalType.DATE)
    Date dateNaissance;
    String adresse;
    String nationalite;

    int phoneNumber;
    String email;
}
