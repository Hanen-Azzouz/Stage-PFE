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

import java.util.Date;
import java.util.List;

@Service
public class AuthenticationService {

    private final UserRepository userRepo;
   // private final RoleRepository roleRepo;
    private final TokenRepository tokenRepo;

    private final PasswordEncoder passwordEncoder;

    private final JWTService jwtService;

    private final AuthenticationManager authenticationManager;

private final EmailSenderService emailService;

    public AuthenticationService(UserRepository userRepo,
                                 TokenRepository tokenRepo,
                                 PasswordEncoder passwordEncoder,
                                 JWTService jwtService,
                                 AuthenticationManager authenticationManager, EmailSenderService emailService) {
        this.userRepo = userRepo;
        this.tokenRepo = tokenRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.emailService = emailService;
    }


    public AuthenticationResponse register(User request){
        //Check if the user already exists. if exists then authenticate the user
        if(userRepo.findByUsername(request.getUsername()).isPresent()){
            return new AuthenticationResponse(null,null,"User already exist",request.getRole().getRoleName());
        }

        User user=new User();
        user.setLastName(request.getLastName());
        user.setFirstName(request.getFirstName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());
        user.setDateNaissance(request.getDateNaissance());
        user.setDateInscription(request.getDateInscription());
        user.setDateUpdatePWD(new Date());
        user.setAccountNonLocked(true);
        user.setRole(request.getRole());
        user.setExpiredPWD(false);

        user=userRepo.save(user);


        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);
        // save the generated token in DB

       saveUserToken(accessToken,refreshToken,user);
        sendValidationEmail(user);
        return new AuthenticationResponse(accessToken,refreshToken,"User registration was successful",user.getRole().getRoleName());

    }
    private void sendValidationEmail(User user){

        emailService.sendSimpleEmail("hanen.azzouz@esprit.tn","Bonjour,\n Le compte utlisateur de : "+" "
                +user.getFirstName()+" "+user.getLastName() +" "+"a été créé avec succès."+
                "\n le username est"+" "+user.getUsername()+" "+"\nLe mot de passe est"+ " "+user.getPassword()+  "  " +
                        "\n Cordialement\"\n"
                ,"User added successfully "  );
    }



    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        System.out.println("username sent is ok" + request.getUsername());
        User user = userRepo.findByUsername(request.getUsername()).orElseThrow();
            System.out.println("user ok" + user.getUsername());

                String accessToken = jwtService.generateAccessToken(user);
                String refreshToken = jwtService.generateRefreshToken(user);

                revokeAllTokensByUser(user);
                deleteLoggedTokens();

                saveUserToken(accessToken, refreshToken, user);

        return new AuthenticationResponse(accessToken, refreshToken, "User login was successful", user.getRole().getRoleName().toUpperCase());


    }



    private void deleteLoggedTokens() {
        List<Token> loggedOutTokens= tokenRepo.findAllLoggedAccessTokenByUser();
        if(loggedOutTokens.isEmpty()){
            System.out.println(" tokens logged out List is empty");
            return;
        }

        loggedOutTokens.forEach(t->{
         tokenRepo.delete(t);
            // t.setLoggedOut(true);
        });
        System.out.println(" tokens logged out deleted ok");
    }

    private void revokeAllTokensByUser(User user) {
        List<Token> validTokens = tokenRepo.findAllAccessTokenByUser(user.getIdUser());

        if(validTokens.isEmpty()){
            return;}

            validTokens.forEach(t->{
                t.setLoggedOut(true);
                //t.setExpired(true);
            });


        tokenRepo.saveAll(validTokens);
    }

    private void saveUserToken( String accessToken,String refreshToken,User user) {
        Token token=new Token();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        token.setLoggedOut(false);
        //token.setExpired(false);
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

   public void alertIncorrectTentativeCnx(String username){
       System.out.println("the username received is : "+username);
       User user = userRepo.findByUsername(username).orElseThrow();
       System.out.println("the user is : "+user.getFirstName());
       emailService.sendSimpleEmail("hanen.azzouz@esprit.tn","Bonjour,\n L'utlisateur  : "+" "
                       +user.getFirstName()+" "+user.getLastName() +
                       "\n  Trop de tentatives de connexion infructueuses."+
                       "\n Cordialement\"\n"
               ,"Connexion infructueuses "  );
       System.out.println("Mail was sent : ");

   }




}
