package on.druguser.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@NonNull
@ToString
public class UserRegisterDto {
	String username;
	String email;
	String password;
	}

