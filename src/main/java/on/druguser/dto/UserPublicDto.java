package on.druguser.dto;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class UserPublicDto {
	String username;
	String email;
	String role;
	boolean isActive;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	OffsetDateTime roleEnd;

	public UserPublicDto(String username, String email, boolean isActive) {
		this.username = username;
		this.email = email;
		this.isActive = isActive;
	}

}
