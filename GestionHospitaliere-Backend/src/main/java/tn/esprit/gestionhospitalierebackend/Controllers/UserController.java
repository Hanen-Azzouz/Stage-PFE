package tn.esprit.gestionhospitalierebackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionhospitalierebackend.DAO.entities.User;
import tn.esprit.gestionhospitalierebackend.Services.interfaces.IUserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*")
public class UserController {



    @Autowired
    private IUserService userRest;


    @PostMapping("/addUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    User addUser(@RequestBody User user){

        return userRest.addUserAndAffectToRole(user);
    }

    @PutMapping("/updateUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    User updateUser(@RequestBody User user){
        return userRest.updateUser(user);
    }

    @GetMapping("/getUserById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    User getUserById(@PathVariable Integer id){
        return userRest.findUserById(id);
    }

    @GetMapping("/getAllUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    List<User> getAllUsers(){
        return userRest.findAllUsers();
    }

    @DeleteMapping("/deleteUserById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteUserById(@PathVariable Integer id){
        userRest.deleteUserById(id);
    }

    @DeleteMapping("/deleteUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteUser(@RequestBody User user){
        userRest.deleteUser(user);
    }


}
