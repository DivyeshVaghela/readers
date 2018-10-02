package com.learning.readers.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.learning.readers.entity.Role;
import com.learning.readers.entity.User;

public class CustomUserDetails extends User implements UserDetails {

	private static final long serialVersionUID = 1L;

	public CustomUserDetails(final User user) {
		super(user);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Set<Role> roles = getRoles();
		List<SimpleGrantedAuthority> authList = new ArrayList<>();
		
		for (Role role : roles) {
			//new SimpleGrantedAuthority("ROLE_" + role.getName())) //if we are using hasRole() or hasAnyRole() method for authorization
			authList.add(new SimpleGrantedAuthority(role.getName())); //if we are using hasAuthority() or hasAnyAuthority() method for authorization
		}
		
		return authList;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		return super.getEnabled();
	}

}
