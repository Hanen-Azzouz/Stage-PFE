package tn.esprit.gestionhospitalierebackend.DAO.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;


public enum TypeRole {
    ADMIN,
    MEDECIN,
    INFIRMIER,
    CAISSIER,
    ADMISSION,
    FACTURATION,
    HONORAIRE
        ;






    }
