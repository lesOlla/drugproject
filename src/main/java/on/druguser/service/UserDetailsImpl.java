package on.druguser.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import on.druguser.model.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	private String email;
	@JsonIgnore
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	public static UserDetailsImpl build(User user) {
		  Set <String> roles = new HashSet<>();
		  roles.add("GUEST");
		List<GrantedAuthority> authorities = roles.stream()
	        .map(role -> new SimpleGrantedAuthority(role))
	        .collect(Collectors.toList());

	    return new UserDetailsImpl(
	        user.getId(), 
	        user.getUsername(), 
	        user.getEmail(),
	        user.getPassword(), 
	        authorities);
	  }

	  @Override
	  public Collection<? extends GrantedAuthority> getAuthorities() {
			/*
			 * Set <String> roles = new HashSet<>(); roles.add("ADMIN");
			 */
		 return authorities.stream()
	       // .map(role -> new SimpleGrantedAuthority(role))
	        .collect(Collectors.toList());
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

	  @Override
	  public boolean equals(Object o) {
	    if (this == o)
	      return true;
	    if (o == null || getClass() != o.getClass())
	      return false;
	    UserDetailsImpl user = (UserDetailsImpl) o;
	    return Objects.equals(id, user.id);
	  }

	public UserDetailsImpl(Long id, String username, String email, String password) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
	}
	}