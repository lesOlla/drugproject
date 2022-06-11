package on.druguser.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserRoleId implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6841826776341968193L;
	
	Long roleId;
	Long userId;
	OffsetDateTime roleStart = OffsetDateTime.now();
	OffsetDateTime roleEnd = roleStart.plusYears(100L);

}
