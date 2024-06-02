package tn.esprit.gestionhospitalierebackend.DAO.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.gestionhospitalierebackend.DAO.entities.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Integer> {

    Role getByRoleName(String rolename);
}

