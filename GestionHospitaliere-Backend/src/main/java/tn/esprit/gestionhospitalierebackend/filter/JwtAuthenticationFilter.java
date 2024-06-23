package tn.esprit.gestionhospitalierebackend.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tn.esprit.gestionhospitalierebackend.DAO.repositories.TokenRepository;
import tn.esprit.gestionhospitalierebackend.Services.implementation.JWTService;
import tn.esprit.gestionhospitalierebackend.Services.implementation.UserDetailsServiceImpl;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTService jwtService;

    private final UserDetailsServiceImpl userDetailsService;
   // private final TokenRepository tokenRepo;

    public JwtAuthenticationFilter(JWTService jwtService, UserDetailsServiceImpl userDetails) {
        this.jwtService = jwtService;
        this.userDetailsService = userDetails;
        //this.tokenRepo = tokenRepo;
    }


    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain)
                                    throws ServletException, IOException {

        String authHeader=request.getHeader("Authorization");
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            //System.out.println("No need for  token here");
            return;
        }
        String token=authHeader.substring(7);
        String username= jwtService.extractUsername(token);
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

        UserDetails userDetails=userDetailsService.loadUserByUsername(username);

       //from here update
        /*var isTokenValid= tokenRepo.findByAccessToken(token)
                .map(t->!t.isExpired() && !t.isLoggedOut())
                .orElse(false);*/
        //To here

        if(jwtService.isValid(token,userDetails)){

            UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(
                    userDetails,null,userDetails.getAuthorities());

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("username is :"+username+" , his role is :"+userDetails.getAuthorities());
        }
        }
        filterChain.doFilter(request,response);

    }



}
