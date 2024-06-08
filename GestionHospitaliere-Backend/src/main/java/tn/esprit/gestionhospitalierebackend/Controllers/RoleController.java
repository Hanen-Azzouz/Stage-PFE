package tn.esprit.gestionhospitalierebackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionhospitalierebackend.DAO.entities.Role;
import tn.esprit.gestionhospitalierebackend.Services.interfaces.IRoleService;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/role")

public class RoleController {

    @Autowired
    private IRoleService roleRest;



    @GetMapping("/getRoleByName/{rolename}")
    @PreAuthorize("hasAuthority('ADMIN')")
    Role getRoleByName(@PathVariable String rolename){
        return roleRest.getRoleByName(rolename);


    }
    @PostMapping(value = "/addRole", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    Role addRole(@RequestBody Role role){
        return roleRest.addRole(role);
    }
    @PutMapping(value = "/updateRole", consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    Role updateRole(@RequestBody Role role){
        return roleRest.updateRole(role);
    }
    @DeleteMapping("/deleteRoleById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteRoleById(@PathVariable Integer id){
        roleRest.deleteRoleById(id);

    }
    @DeleteMapping("/deleteRole")
    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteRole(@RequestBody Role role){

    }
    @GetMapping("/getRoleById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    Role getRoleById(@PathVariable Integer id){
        return roleRest.findRoleById(id);
    }


    @GetMapping("/getAllRoles")
   // @PreAuthorize("hasAuthority('ADMIN')")
    List<Role> getAllRoles(){
        return roleRest.findAllRoles();
    }





}
