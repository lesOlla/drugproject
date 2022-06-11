package on.druguser.service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import on.druguser.dao.RoleRepository;
import on.druguser.dao.RouteRepository;
import on.druguser.dao.UserRepository;
import on.druguser.dao.UserRoleRepository;
import on.druguser.model.User;
import on.druguser.model.UserRole;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	UserRoleRepository userRoleRepository;
	@Autowired
	RoleRepository roleRepository;
	RouteRepository routeRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = new User();
		if (username.contains("@")) {
			user = userRepository.findByEmail(username.toLowerCase())
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		} else {
			user = userRepository.findByUsername(username.toLowerCase())
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		}
		UserRole lg = userRoleRepository
				.findByUserIdAndRoleStartBeforeAndRoleEndAfter(user.getId(), OffsetDateTime.now(), OffsetDateTime.now())
				.findFirst().get();
		List<GrantedAuthority> authority = new ArrayList<>();
		authority.add(new SimpleGrantedAuthority(roleRepository.findById(lg.getRoleId()).get().getName()));
		UserDetailsImpl temp = UserDetailsImpl.build(user);
		temp.setAuthorities(authority);
		return temp;
	}

}
