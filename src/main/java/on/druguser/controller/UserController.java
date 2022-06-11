package on.druguser.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;

import on.druguser.dto.RefreshResponseDto;
import on.druguser.dto.RefreshRequestDto;
import on.druguser.dto.UserHistoryDto;
import on.druguser.dto.UserLoginDto;
import on.druguser.dto.UserPublicDto;
import on.druguser.dto.UserRegisterDto;
import on.druguser.dto.UserToLoginDto;
import on.druguser.dto.UserUpdateDto;
import on.druguser.exceptions.TokenRefreshException;
import on.druguser.model.RefreshToken;
import on.druguser.model.User;
import on.druguser.model.UserRole;
import on.druguser.security.JwtUtils;
import on.druguser.service.RefreshTokenService;
import on.druguser.service.UserAccountService;
import on.druguser.service.UserDetailsImpl;

@RestController
@RequestMapping("/accounting")
public class UserController {
	@Autowired
	AuthenticationManager authenticationManager;
	UserAccountService userAccountService;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	RefreshTokenService refreshTokenService;

	@Autowired
	public UserController(UserAccountService userAccountService) {
		this.userAccountService = userAccountService;
	}

	@PostMapping("/registration")
	public void regist(@RequestBody UserRegisterDto userRegisterDto) {
		userAccountService.addUser(userRegisterDto);
	}

	@PostMapping("/login")
	public UserLoginDto authenticateUser(@Validated @RequestBody UserToLoginDto userToLoginDto) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(userToLoginDto.getUsername(), userToLoginDto.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		String jwt = jwtUtils.generateJwtToken(authentication);
		RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

		UserLoginDto res = userAccountService.login(userDetails);
		List<GrantedAuthority> autt = new ArrayList<>();
		autt.add(new SimpleGrantedAuthority(res.getRole()));
		userDetails.setAuthorities(autt);
		res.setToken(jwt);
		res.setRefreshToken(refreshToken.getToken());
		return res;
	}

	@DeleteMapping("/delete/{username}")
	public UserPublicDto removeUser(@PathVariable String username) {
		return userAccountService.removeUser(username);
	}

	@PutMapping("/user/{username}/role/{role}/period/{period}")
	public UserPublicDto changeRole(@PathVariable String username, @PathVariable String role,
			@PathVariable int period) {

		return userAccountService.changeRole(username, role, period);
	}

	@PutMapping("/password")
	public void changePassword(@RequestHeader("Authorization") String token,
			@RequestHeader("X-Password") String value) {
		String temp = token.substring(7, token.length());
		String username = jwtUtils.getUserNameFromJwtToken(temp);
		userAccountService.changePassword(username, value);
	}

	@PutMapping("/update*")
	public UserPublicDto updateUser(@RequestHeader("Authorization") String token,
			@RequestBody UserUpdateDto userUpdateDto) {
		String temp = token.substring(7, token.length());
		String username = jwtUtils.getUserNameFromJwtToken(temp);
		return userAccountService.updateUser(username, userUpdateDto);
	}

	@GetMapping("/username/{username}")
	public UserPublicDto getUser(@PathVariable String username) {
		return userAccountService.getUser(username);
	}

	@GetMapping("/dates/{from}/{to}")
	public List<UserRole> findByCreationDate(@PathVariable int from, @PathVariable int to) {
		return userAccountService.findByCreationDate(from, to);
	}

	@GetMapping("/role/{role}")
	public List<UserRole> findByRole(@PathVariable String role) {
		return userAccountService.findByRole(role);
	}

	@GetMapping("/users/{username}")
	public UserHistoryDto findByUser(@PathVariable String username) {
		return userAccountService.findByUser(username);
	}

	@PostMapping("/refreshtoken")
	public RefreshResponseDto refreshtoken(@Valid @RequestBody RefreshRequestDto request) {
		String requestRefreshToken = request.getRefreshToken();
		RefreshToken temp = refreshTokenService.findByToken(requestRefreshToken)
				.orElseThrow(() -> new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!"));
		temp = refreshTokenService.verifyExpiration(temp);
		User user = temp.getUser();
		String token = jwtUtils.generateJwtTokenFromUsername(user.getUsername());
		return new RefreshResponseDto(token, requestRefreshToken);
	}
}
