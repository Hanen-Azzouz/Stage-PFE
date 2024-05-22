package tn.esprit.gestionhospitalierebackend.Services.implementation;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import tn.esprit.gestionhospitalierebackend.DAO.entities.AuthenticationResponse;
import tn.esprit.gestionhospitalierebackend.DAO.entities.Token;
import tn.esprit.gestionhospitalierebackend.DAO.entities.User;
import tn.esprit.gestionhospitalierebackend.DAO.repositories.TokenRepository;
import tn.esprit.gestionhospitalierebackend.DAO.repositories.UserRepository;

import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository userRepo;
    private final TokenRepository tokenRepo;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository userRepo,
                                 TokenRepository tokenRepo,
                                 PasswordEncoder passwordEncoder,
                                 JWTService jwtService,
                                 AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse register(User request){
        //Check if the user already exists. if exists then authenticate the user
        if(userRepo.findByUsername(request.getUsername()).isPresent()){
            return new AuthenticationResponse(null,null,"User already exist");
        }

        User user=new User();
        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());

       // user.setRole(request.getRole());
        user.setRole(request.getRole());

        user=userRepo.save(user);


        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);
        // save the generated token in DB

       saveUserToken(accessToken,refreshToken,user);

        return new AuthenticationResponse(accessToken,refreshToken,"User registration was successful");

    }



    public AuthenticationResponse authenticate(User request){
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );
    User user=userRepo.findByUsername(request.getUsername()).orElseThrow();
        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);

        revokeAllTokensByUser(user);

        saveUserToken(accessToken,refreshToken,user);
        return new AuthenticationResponse(accessToken,refreshToken,"User login was successful");
    }

    private void revokeAllTokensByUser(User user) {
        List<Token> validTokens = tokenRepo.findAllAccessTokenByUser(user.getIdUser());

        if(validTokens.isEmpty()){
            return;}

            validTokens.forEach(t->{
                t.setLoggedOut(true);
            });



        tokenRepo.saveAll(validTokens);
    }

    private void saveUserToken( String accessToken,String refreshToken,User user) {
        Token token=new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepo.save(token);
    }


    public ResponseEntity refreshToken(
            HttpServletRequest request, HttpServletResponse response) {
        //extract Token from authorization header
       String authHeader= request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        String token=authHeader.substring(7);
        //extract username from token
        String username=jwtService.extractUsername(token);
        //Check if the user exist in DB
        User user=userRepo.findByUsername(username)
                .orElseThrow(()->new UsernameNotFoundException("No User Found"));
    //now Check if refresh token is valid
        if (jwtService.isValidRefreshToken(token,user)){
            //generate access token
            String accessToken=jwtService.generateAccessToken(user);
            String refreshToken= jwtService.generateRefreshToken(user);

            revokeAllTokensByUser(user);

            saveUserToken(accessToken,refreshToken,user);

            return new ResponseEntity(new AuthenticationResponse(accessToken,refreshToken,"New token generated"), HttpStatus.OK);

        }
        return new ResponseEntity(HttpStatus.UNAUTHORIZED);

    }
}
