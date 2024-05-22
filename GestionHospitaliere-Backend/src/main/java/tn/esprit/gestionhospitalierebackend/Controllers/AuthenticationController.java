package tn.esprit.gestionhospitalierebackend.Controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.gestionhospitalierebackend.DAO.entities.AuthenticationResponse;
import tn.esprit.gestionhospitalierebackend.DAO.entities.User;
import tn.esprit.gestionhospitalierebackend.Services.implementation.AuthenticationService;


@RestController
//@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authService;



    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody User request){
        return ResponseEntity.ok(authService.register(request));

    }
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

















}




