package tn.esprit.gestionhospitalierebackend.Services.implementation;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import tn.esprit.gestionhospitalierebackend.DAO.entities.User;
import tn.esprit.gestionhospitalierebackend.DAO.repositories.TokenRepository;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JWTService {


    private final TokenRepository tokenRepo;
    @Value("${application.security.jwt.secret-key}")
    private String secretKEY;
    @Value("${application.security.jwt.access-token-expiration}")
    private long accessTokenExpire;

    @Value("${application.security.jwt.refresh-token-expiration}")
    private long refreshTokenExpire;

    public JWTService(TokenRepository tokenRepo) {
        this.tokenRepo = tokenRepo;
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public boolean isValid(String token, UserDetails user){
        String username=extractUsername(token);
        boolean isValidToken= tokenRepo.findByAccessToken(token)
                .map(t->!t.isLoggedOut()).orElse(false);

        return (username.equals(user.getUsername()))&& !isTokenExpired(token) && isValidToken;
    }

    public boolean isValidRefreshToken(String token, User user) {
        String username=extractUsername(token);
        boolean validRefreshToken=tokenRepo.findByRefreshToken(token)
                .map(t->!t.isLoggedOut())
                .orElse(false);


        return (username.equals(user.getUsername()))&& !isTokenExpired(token) && validRefreshToken ;

    }

    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String generateAccessToken(User user){
        return generateToken(user,accessTokenExpire);//86400000

    }

    public String generateRefreshToken(User user){
        return generateToken(user,refreshTokenExpire);//604800000

    }
    private String generateToken(User user,long expiredTime){
        String token= Jwts
                .builder()
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+expiredTime))
                .signWith(getSigningKey())
                .compact();
        return token;

    }




    private SecretKey getSigningKey(){
        byte[] keyBites= Decoders.BASE64URL.decode(secretKEY);
        return Keys.hmacShaKeyFor(keyBites);

    }



}
