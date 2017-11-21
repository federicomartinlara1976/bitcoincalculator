package net.bounceme.chronos.bitcoincalculator.security;

import java.util.ArrayList;
import java.util.List;

import java.security.cert.X509Certificate;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class X509CustomAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
        //Do nothing
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) {
        List<GrantedAuthority> grantedAuths = new ArrayList<>();

        X509Certificate certificate = (X509Certificate) authentication.getPrincipal();

        // Convert in a DTO that can be exploited
        return new X509CustomUser(certificate.getSubjectDN().getName(), "", grantedAuths);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (X509AuthenticationToken.class.isAssignableFrom(authentication));
    }
}