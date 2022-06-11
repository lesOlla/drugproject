package on.druguser.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NonNull
@ToString
public class UserLoginDto {
	private String token;
	private String type = "Bearer";
	private String refreshToken;
	private String username;
	private Long id;
	private String role;
	private Set <String> routes;
	
	
	public UserLoginDto(String token, String refreshToken, String username, Long id, String role, Set<String> temp) {
		this.token = token;
		this.refreshToken=refreshToken;
		this.username=username;
		this.id = id;
		this.role = role;
		this.routes = temp;
	}

	public UserLoginDto(String username, Long id, String role, Set<String> temp) {
		this.username=username;
		this.id = id;
		this.role = role;
		this.routes = temp;
	}
	
}
