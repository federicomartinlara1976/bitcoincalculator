package net.bounceme.chronos.bitcoincalculator.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class X509CustomUser implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9116733622056243347L;
	
	private String name;
	
	private String password;
	
	private List<GrantedAuthority> grantedAuths;
	

	public X509CustomUser(String name, String password, List<GrantedAuthority> grantedAuths) {
		this.name = name;
		this.password = password;
		this.grantedAuths = grantedAuths;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return grantedAuths;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return name;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
