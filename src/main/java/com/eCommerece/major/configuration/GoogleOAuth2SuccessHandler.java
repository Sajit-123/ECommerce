package com.eCommerece.major.configuration;

import com.eCommerece.major.model.Role;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.eCommerece.major.model.User;
import com.eCommerece.major.repository.RoleRepository;
import com.eCommerece.major.repository.UserRepository;

@Component
public class GoogleOAuth2SuccessHandler implements AuthenticationSuccessHandler{

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public GoogleOAuth2SuccessHandler(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest arg0, HttpServletResponse arg1, Authentication arg2)
            throws IOException, ServletException {
                OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) arg2;
                String email = token.getPrincipal().getAttributes().get("email").toString();
                if (!userRepository.findUserByEmail(email).isPresent()){
                    User user = new User();
                    user.setFirstName(token.getPrincipal().getAttributes().get("given_name").toString());
                    user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
                    user.setEmail(email);
                    List<Role> roles = new ArrayList<>();
                    roles.add(roleRepository.findById(2).get());
                    user.setRoles(roles);
                    userRepository.save(user); 
                }
                redirectStrategy.sendRedirect(arg0,arg1,"/");
    }

    
}
