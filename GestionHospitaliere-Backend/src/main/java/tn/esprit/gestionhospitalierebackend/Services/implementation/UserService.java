package tn.esprit.gestionhospitalierebackend.Services.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.gestionhospitalierebackend.DAO.entities.User;
import tn.esprit.gestionhospitalierebackend.DAO.repositories.UserRepository;
import tn.esprit.gestionhospitalierebackend.Services.interfaces.IUserService;

import java.util.List;

@Service
public class UserService implements IUserService {
         @Autowired
        private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepo;
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
    public User updateUser(User user) {
        return userRepo.save(user);
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
}
