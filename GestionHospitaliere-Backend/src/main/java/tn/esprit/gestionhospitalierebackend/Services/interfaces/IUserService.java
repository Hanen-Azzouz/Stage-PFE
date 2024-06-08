package tn.esprit.gestionhospitalierebackend.Services.interfaces;

import org.springframework.http.ResponseEntity;
import tn.esprit.gestionhospitalierebackend.DAO.entities.User;

import java.util.Date;
import java.util.List;

public interface IUserService {


    User addUserAndAffectToRole(User user);
    User updateUser(Integer idUser,User user);
    void deleteUser(User user);
    void deleteUserById(Integer id);
    List<User> findAllUsers();
    User findUserById(Integer id);
    public void lockUserAccount( String username);
    public void unlockUserAccount(String username);
    public List<User> searchActifUsers(boolean status);
    public User searchUserByUsername(String username);
    public List<User> getUserByDateInscriptionBetween(Date date1, Date date2);











}
