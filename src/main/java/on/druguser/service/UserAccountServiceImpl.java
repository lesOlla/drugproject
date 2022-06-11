package on.druguser.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import on.druguser.dao.RoleRepository;
import on.druguser.dao.RouteRepository;
import on.druguser.dao.UserRepository;
import on.druguser.dao.UserRoleRepository;
import on.druguser.dto.UserHistoryDto;
import on.druguser.dto.UserLoginDto;
import on.druguser.dto.UserPublicDto;
import on.druguser.dto.UserRegisterDto;
import on.druguser.dto.UserUpdateDto;
import on.druguser.exceptions.EntityExistException;
import on.druguser.exceptions.UserNotExistException;
import on.druguser.model.Role;
import on.druguser.model.User;
import on.druguser.model.UserRole;
import on.druguser.security.JwtUtils;

@Service
public class UserAccountServiceImpl implements UserAccountService {
	ModelMapper modelMapper;
	UserRepository userRepository;
	UserRoleRepository userRoleRepository;
	RoleRepository roleRepository;
	RouteRepository routeRepository;
	String token;
	private final Role basic;
	int addition = 0;

	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	public UserAccountServiceImpl(ModelMapper modelMapper, UserRepository userRepository,
			UserRoleRepository userRoleRepository, RoleRepository roleRepository, RouteRepository routeRepository) {
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
		this.userRoleRepository = userRoleRepository;
		this.roleRepository = roleRepository;
		this.routeRepository = routeRepository;
		this.basic = roleRepository.findById(0).get();
	}

	@Override
	@Transactional
	public void addUser(UserRegisterDto userRegisterDto) {
		if (!userRepository.findByEmail(userRegisterDto.getEmail().toLowerCase()).isEmpty()
				|| !userRepository.findByUsername(userRegisterDto.getUsername().toLowerCase()).isEmpty()) {
			throw new EntityExistException();
		} else {
			User user = new User(userRegisterDto.getUsername().toLowerCase(), userRegisterDto.getEmail().toLowerCase(),
					encoder.encode(userRegisterDto.getPassword()));
			userRepository.save(user);
			UserRole newUserRole = new UserRole(basic.getId(), user.getId());
			userRoleRepository.save(newUserRole);
		}
	}

	@Override
	@Transactional
	public UserLoginDto login(UserDetails userDetails) {
		UserRole current = findCurrentUserRole(userDetails.getUsername());
		Set<String> routes = roleRepository.findById(current.getRoleId()).get().getRoutes().stream()
				.map(a -> a.getRouteName()).collect(Collectors.toSet());
		UserLoginDto res = new UserLoginDto(userDetails.getUsername(), current.getUserId(),
				roleRepository.findById(current.getRoleId()).get().getName(), routes);
		return res;
	}

	@Override
	@Transactional
	public UserPublicDto getUser(String username) {
		User user = findUserIfExist(username.toLowerCase());
		UserRole current = findCurrentUserRole(username.toLowerCase());
		UserPublicDto res = new UserPublicDto(user.getUsername(), user.getEmail(), user.isActive());
		res.setRole(roleRepository.getById(current.getRoleId()).getName());
		res.setRoleEnd(current.getRoleEnd());
		return res;
	}

	private UserRole findCurrentUserRole(String username) {
		User user = findUserIfExist(username.toLowerCase());
		// return
		// userRoleRepository.findFirstByUserIdAndRoleStartBeforeAndRoleEndAfterOrderByRoleStartDesc(user.getId(),
		// OffsetDateTime.now(), OffsetDateTime.now()).get();
		return userRoleRepository
				.findByUserIdAndRoleStartBeforeAndRoleEndAfter(user.getId(), OffsetDateTime.now(), OffsetDateTime.now())
				.findFirst().get();
	}

	@Override
	@Transactional
	public UserPublicDto removeUser(String login) {
		User user = findUserIfExist(login);
		if (user.isActive()) {
			user.setActive(false);
		} else {
			user.setActive(true);
		}
		userRepository.save(user);
		return modelMapper.map(user, UserPublicDto.class);
	}

	private User findUserIfExist(String login) {
		User user = new User();
		if (login.contains("@")) {
			user = userRepository.findByEmail(login.toLowerCase()).orElseThrow(() -> new UserNotExistException());
		} else {
			user = userRepository.findByUsername(login.toLowerCase()).orElseThrow(() -> new UserNotExistException());
		}
		return user;
	}

	@Override
	@Transactional
	public UserPublicDto changeRole(String username, String role, int period) {
		User user = findUserIfExist(username.toLowerCase());
		UserRole current = findCurrentUserRole(username);

		Role newRole = roleRepository.findByName(role.toUpperCase()).get();
		OffsetDateTime newEnd = OffsetDateTime.now().plusDays(period);

		List<UserRole> temp = userRoleRepository
				.findByUserIdAndRoleStartBeforeAndRoleEndAfter(user.getId(), OffsetDateTime.now(), OffsetDateTime.now())
				.sorted(Comparator.comparing(s -> s.getRoleStart())).map(m -> new UserRole(m.getRoleId(), m.getUserId(),
						OffsetDateTime.now().plusMinutes(addition++), m.getRoleEnd().plusDays(period)))
				.collect(Collectors.toList());
		userRoleRepository.saveAll(temp);

		UserRole newUserRole = new UserRole(newRole.getId(), current.getUserId(), newEnd);
		userRoleRepository.save(newUserRole);
		UserPublicDto res = new UserPublicDto(user.getUsername(), user.getEmail(), role, user.isActive(),
				newUserRole.getRoleEnd());
		return res;
	}

	@Override
	public void changePassword(String username, String password) {
		User user = userRepository.findByUsername(username.toLowerCase()).get();
		String pass = encoder.encode(password);
		user.setPassword(pass);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public List<UserRole> findByCreationDate(int from, int to) {
		OffsetDateTime fromRole = OffsetDateTime.of(from, 1, 1, 0, 0, 0, 0, ZoneOffset.UTC);
		OffsetDateTime tillRole = fromRole.plusYears(to - from);
		return userRoleRepository.findByRoleStartAfterAndRoleStartBefore(fromRole, tillRole)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public List<UserRole> findByRole(String role) {
		return userRoleRepository.findByRoleId(roleRepository.findByName(role.toUpperCase()).get().getId())
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public UserHistoryDto findByUser(String username) {
		User user = findUserIfExist(username.toLowerCase());
		final long userId = user.getId();
		String nick = user.getUsername();
		String email = user.getEmail();
		Boolean vivid = user.isActive();
		List<UserRole> temp = userRoleRepository.findByUserId(userId).collect(Collectors.toList());
		UserHistoryDto res = new UserHistoryDto(nick, email, vivid);
		res.setRoleCalendar(temp);
		return res;
	}

	@Override
	@Transactional
	public UserPublicDto updateUser(String token, UserUpdateDto userUpdateDto) {
		User user = findUserIfExist(token.toLowerCase());
		if (userUpdateDto.getUsername() != null) {
			user.setUsername(userUpdateDto.getUsername().toLowerCase());
		}
		if (userUpdateDto.getEmail() != null) {
			user.setEmail(userUpdateDto.getEmail().toLowerCase());
		}
		userRepository.save(user);

		UserRole current = findCurrentUserRole(user.getUsername());
		Long roleId = current.getRoleId();
		String role = roleRepository.findById(roleId).get().getName();
		return new UserPublicDto(user.getUsername(), user.getEmail(), role, user.isActive(), current.getRoleEnd());
	}

}
