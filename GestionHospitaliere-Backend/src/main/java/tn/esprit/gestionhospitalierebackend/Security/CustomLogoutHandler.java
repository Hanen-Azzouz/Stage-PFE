package tn.esprit.gestionhospitalierebackend.Security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tn.esprit.gestionhospitalierebackend.DAO.entities.Token;
import tn.esprit.gestionhospitalierebackend.DAO.repositories.TokenRepository;

//@Component
@Service
public class CustomLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepo;

    public CustomLogoutHandler(TokenRepository tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String authHeader=request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            return;
        }

        String token=authHeader.substring(7);

        //Get stored Token from DB
        Token storedToken = tokenRepo.findByAccessToken(token).orElse(null);
        //invalidate the token => make logout true
        if(storedToken != null) {
            storedToken.setLoggedOut(true);

            tokenRepo.save(storedToken);
        }

    }
}
