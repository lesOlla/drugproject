package on.druguser.dto;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import on.druguser.model.UserRole;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NonNull
@ToString
public class UserHistoryDto {
	String username;
	String email;
	boolean isActive;
	List<UserRole> roleCalendar;
	
	public UserHistoryDto(String username, String email, boolean isActive) {
		this.username = username;
		this.email = email;
		this.isActive = isActive;
	}	
}
