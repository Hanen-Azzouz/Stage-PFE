package tn.esprit.gestionhospitalierebackend.Services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.gestionhospitalierebackend.DAO.entities.Role;
import tn.esprit.gestionhospitalierebackend.DAO.repositories.RoleRepository;
import tn.esprit.gestionhospitalierebackend.Services.interfaces.IRoleService;

import java.util.List;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepo;

    @Override
    public Role addRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public Role updateRole(Role role) {
        return roleRepo.save(role);
    }

    @Override
    public void deleteRole(Role role) {
        roleRepo.delete(role);
    }

    @Override
    public void deleteRoleById(Integer id) {
        roleRepo.deleteById(id);
    }

    @Override
    public List<Role> findAllRoles() {
        return (List<Role>) roleRepo.findAll();
    }

    @Override
    public Role findRoleById(Integer id) {
        return roleRepo.findById(id).get();
    }
}
