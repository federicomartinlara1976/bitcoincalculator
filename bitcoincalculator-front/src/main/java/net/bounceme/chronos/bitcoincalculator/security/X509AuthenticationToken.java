package net.bounceme.chronos.bitcoincalculator.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class X509AuthenticationToken extends UsernamePasswordAuthenticationToken {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2158175917278423540L;

	public X509AuthenticationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public X509AuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}

