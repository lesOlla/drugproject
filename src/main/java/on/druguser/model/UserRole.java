package on.druguser.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(schema = "sign", name = "user_role")
@IdClass(UserRoleId.class)
public class UserRole implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4959394399588654706L;

	@Id
	@Column(name = "role_id")
	Long roleId;
	
	@Id
	@Column(name = "user_id")
	Long userId;
	
	@Id
	@Column(name = "role_start")
	OffsetDateTime roleStart = OffsetDateTime.now();
	
	@Id
	@Column(name = "role_end")
	OffsetDateTime roleEnd = roleStart.plusYears(100);
	
	public void setEnd(OffsetDateTime end) {
		this.roleEnd = end;
	}

	public UserRole(long roleId, long userId) {
		this.roleId = roleId;
		this.userId = userId;		
	}
	
	public UserRole(long roleId, long userId, OffsetDateTime end) {
		this.roleId = roleId;
		this.userId = userId;
		this.roleEnd =end;
	}

	public UserRole(Long userId, OffsetDateTime roleStart, OffsetDateTime roleEnd) {
		this.userId = userId;
		this.roleStart = roleStart;
		this.roleEnd = roleEnd;
	}
		
	
}
