package tn.esprit.gestionhospitalierebackend.DAO.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.gestionhospitalierebackend.DAO.entities.User;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

  @Repository
  public interface UserRepository extends JpaRepository<User,Integer> {
      Optional <User> findByUsername(String username);
      @Query(value = "SELECT * FROM user u where u.account_non_locked =?1  ", nativeQuery = true)

      List<User> searchActifUsers(boolean status);
      List<User> getUserByDateInscriptionBetween(Date date1,Date date2);

      @Query(value = "SELECT DATEDIFF(Now(), u.date_updatepwd) as duration FROM user u WHERE (DATEDIFF(Now(), u.date_updatepwd) >= 1)and u.username=?1", nativeQuery = true)
      Integer isPasswordExpired(String username);


}
