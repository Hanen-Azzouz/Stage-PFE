package tn.esprit.gestionhospitalierebackend.DAO.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.gestionhospitalierebackend.DAO.entities.Token;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token,Integer> {
    @Query("""
SELECT t from Token t inner join User u on t.user.idUser =u.idUser
where t.user.idUser= :userId and t.loggedOut=false
""")
    List<Token> findAllAccessTokenByUser(Integer userId);
    Optional<Token> findByAccessToken(String token);
    Optional<Token> findByRefreshToken(String token);

}
