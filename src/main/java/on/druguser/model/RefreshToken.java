package on.druguser.model;

import java.time.Instant;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "id" })
@Entity
@Table(schema = "sign", name = "refresh_token")
public class RefreshToken {
	@Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  private long id;
	
	  @OneToOne
	  @JoinColumn(name = "user_id", referencedColumnName = "id")
	  private User user;
	  @Column(nullable = false, unique = true)
	  private String token;
	  @Column(nullable = false)
	  private Instant expiryDate;
}
