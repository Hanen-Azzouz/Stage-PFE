package tn.esprit.gestionhospitalierebackend.Services.interfaces;

import tn.esprit.gestionhospitalierebackend.DAO.entities.Role;

import java.util.List;

public interface IRoleService {




    Role addRole(Role role);
    Role updateRole(Role role);
    void deleteRole(Role role);
    void deleteRoleById(Integer id);
    List<Role> findAllRoles();
    Role findRoleById(Integer id);










}
