package tn.esprit.gestionhospitalierebackend.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tn.esprit.gestionhospitalierebackend.DAO.entities.AuthenticationResponse;
import tn.esprit.gestionhospitalierebackend.DAO.entities.User;
import tn.esprit.gestionhospitalierebackend.Security.CustomLogoutHandler;
import tn.esprit.gestionhospitalierebackend.Services.implementation.AuthenticationService;


@RestController
@CrossOrigin(origins = "*",allowedHeaders = "*")
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;
    private final CustomLogoutHandler logoutHandler;

    public AuthenticationController(AuthenticationService authService,
                                    CustomLogoutHandler logoutHandler) {
        this.authService = authService;
        this.logoutHandler = logoutHandler;
    }



    /*public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }*/


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
        return ResponseEntity.ok(authService.register(request));}



    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody User request){
        return ResponseEntity.ok(authService.authenticate(request));
    }


    @PostMapping("/refresh_token")
    public ResponseEntity refreshToken(
            HttpServletRequest request,
            HttpServletResponse response){
        return authService.refreshToken(request,response);
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request,
                  HttpServletResponse response,
                  Authentication authentication){
        logoutHandler.logout(request,response,authentication);
        System.out.println("logged out success");

    }

















}




