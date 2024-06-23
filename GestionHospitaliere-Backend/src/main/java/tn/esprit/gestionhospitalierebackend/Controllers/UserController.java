package tn.esprit.gestionhospitalierebackend.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionhospitalierebackend.DAO.entities.User;
import tn.esprit.gestionhospitalierebackend.Services.implementation.ChangePasswordRequest;
import tn.esprit.gestionhospitalierebackend.Services.interfaces.IUserService;

import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {



    @Autowired
    private IUserService userRest;

    @PostMapping(value = "/addUser",consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ADMIN')")
    User addUser(@RequestBody User user){

        return userRest.addUserAndAffectToRole(user);
    }

    @PutMapping(value ="/updateUser/{idUser}",consumes = MediaType.APPLICATION_JSON_VALUE)
   @PreAuthorize("hasAuthority('ADMIN')")
    User updateUser(@PathVariable Integer idUser,@RequestBody User user){
        return userRest.updateUser(idUser,user);
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
    ResponseEntity<Map<String,String>> deleteUserById(@PathVariable Integer id){
        userRest.deleteUserById(id);
        Map<String,String> response=new HashMap<>();
        response.put("message","User account deleted successfully");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteUser")
    @PreAuthorize("hasAuthority('ADMIN')")
    void deleteUser(@RequestBody User user){
        userRest.deleteUser(user);
    }

    @PutMapping("/lockUserAccount")
    @PreAuthorize("hasAuthority('ADMIN')")
     ResponseEntity<Map<String,String>> lockUserAccount(@RequestBody String username){
        userRest.lockUserAccount(username);
        Map<String,String> response=new HashMap<>();
        response.put("message","User account locked successfully");
        return ResponseEntity.ok(response);
    }


    @PutMapping("/unlockUserAccount")
    @PreAuthorize("hasAuthority('ADMIN')")
    ResponseEntity<Map<String,String>> unlockUserAccount(@RequestBody String username){
        userRest.unlockUserAccount(username);
        Map<String,String> response=new HashMap<>();
        response.put("message","User account unlocked successfully");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getActifUsers/{status}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> searchActifUsers(@PathVariable boolean status) {
        return userRest.searchActifUsers(status);
    }
    @GetMapping("/getUserByUsername/{username}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public User searchUserByUsername(@PathVariable String username) {
        return userRest.searchUserByUsername(username) ;
    }

    @GetMapping("/getUserByDateInscriptionBetween/{date1}/{date2}")
    @PreAuthorize("hasAuthority('ADMIN')")
   public List<User> getUserByDateInscriptionBetween(@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date1, @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date2){

        return userRest.getUserByDateInscriptionBetween(date1,date2);
   }
   @PatchMapping("/changePwd")
   public ResponseEntity<?> changePassword(
           @RequestBody ChangePasswordRequest request,
           Principal connectedUser){
        userRest.changePassword(request,connectedUser);
        return ResponseEntity.accepted().build();
   }

   @GetMapping("/isPWDExpired/{username}")
   public boolean isPWDExpired(@PathVariable String username){
        return userRest.isPasswordExpired(username);

   }

}
