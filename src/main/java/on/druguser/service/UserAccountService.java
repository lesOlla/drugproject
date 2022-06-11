package on.druguser.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;

import on.druguser.dto.UserHistoryDto;
import on.druguser.dto.UserLoginDto;
import on.druguser.dto.UserPublicDto;
import on.druguser.dto.UserRegisterDto;
import on.druguser.dto.UserUpdateDto;
import on.druguser.model.UserRole;

public interface UserAccountService {
void addUser(UserRegisterDto userRegisterDto);
	
	UserPublicDto getUser(String login);
	
	UserPublicDto removeUser(String login);
		
	UserPublicDto changeRole(String username, String role, int period);
	UserPublicDto updateUser(String username, UserUpdateDto userUpdateDto);
	void changePassword(String username, String password);
	
	List<UserRole> findByCreationDate(int from, int to);
	List<UserRole> findByRole(String role);
	UserHistoryDto findByUser(String username);

	UserLoginDto login(UserDetails userDetails);

	//Set<String> getRoutes(long role);

}
