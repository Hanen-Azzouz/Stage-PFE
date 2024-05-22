package tn.esprit.gestionhospitalierebackend.Services.interfaces;

import tn.esprit.gestionhospitalierebackend.DAO.entities.User;

import java.util.List;

public interface IUserService {


    User addUserAndAffectToRole(User user);
    User updateUser(User user);
    void deleteUser(User user);
    void deleteUserById(Integer id);
    List<User> findAllUsers();
    User findUserById(Integer id);










}
