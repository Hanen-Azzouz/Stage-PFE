package tn.esprit.gestionhospitalierebackend.Services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.gestionhospitalierebackend.DAO.entities.Role;
import tn.esprit.gestionhospitalierebackend.DAO.entities.User;
import tn.esprit.gestionhospitalierebackend.DAO.repositories.RoleRepository;
import tn.esprit.gestionhospitalierebackend.DAO.repositories.UserRepository;
import tn.esprit.gestionhospitalierebackend.Services.interfaces.IUserService;

import java.util.Date;
import java.util.List;

@Service
public class UserService implements IUserService {
         @Autowired
        private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private RoleRepository roleRepo;
    @Override
    public User addUserAndAffectToRole(User user) {

       System.out.println("user is"+user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setLastName(user.getLastName());
        user.setFirstName(user.getFirstName());
        user.setEmail(user.getEmail());
        user.setUsername(user.getUsername());
        user.setPhoneNumber(user.getPhoneNumber());
        user.setRole(user.getRole());

        return userRepo.save(user);
    }

    @Override
    public User updateUser(Integer idUser,User user) {
        //Role roleaffecte=roleRepo.findById(idRole).get();
        System.out.println("role to affect is :"+user.getRole().getRoleName().toString());

        User updatedUser=userRepo.findById(idUser).get();
        updatedUser.setIdUser(user.getIdUser());
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setEmail(user.getEmail());
        updatedUser.setPhoneNumber(user.getPhoneNumber());
        updatedUser.setDateNaissance(user.getDateNaissance());
        updatedUser.setDateInscription(user.getDateInscription());
        updatedUser.setUsername(user.getUsername());
        updatedUser.setPassword(user.getPassword());
        updatedUser.setRole(user.getRole());



        return userRepo.save(updatedUser);
    }

    @Override
    public void deleteUser(User user) {
        userRepo.delete(user);
    }

    @Override
    public void deleteUserById(Integer id) {
        userRepo.deleteById(id);

    }

    @Override
    public List<User> findAllUsers() {
        return (List<User>) userRepo.findAll();
    }

    @Override
    public User findUserById(Integer id) {

        return userRepo.findById(id).get();
    }

    @Override
    public void lockUserAccount(String username) {
            User userLocked= userRepo.findByUsername(username).orElseThrow();
            if(userLocked!=null) {
                userLocked.setAccountNonLocked(false);
                userRepo.save(userLocked);

            }

    }

    @Override
    public void unlockUserAccount(String username) {
        User userUnlocked = userRepo.findByUsername(username).orElseThrow();
        if (userUnlocked != null) {
            userUnlocked.setAccountNonLocked(true);
            userRepo.save(userUnlocked);
        }

    }

    @Override
    public List<User> searchActifUsers(boolean status) {
        return userRepo.searchActifUsers(status);
    }
    public User searchUserByUsername(String username){
        return userRepo.findByUsername(username).get();
    }

    @Override
    public List<User> getUserByDateInscriptionBetween(Date date1, Date date2) {
        return userRepo.getUserByDateInscriptionBetween(date1,date2);
    }


}
