package on.druguser.dao;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import on.druguser.model.RefreshToken;
import on.druguser.model.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

	
	//@Query(value ="from RefreshToken s where s.id=:id")
	//Optional<RefreshToken> findById(Long id);
	
	@Query(value ="from RefreshToken s where s.token=:token")
	Optional<RefreshToken> findByToken(String token);
	
	//@Query(value ="from RefreshToken s where s.user_id=:user_id")
	//Optional<RefreshToken> findByUserId(Long user_id);
	
    @Modifying
    int deleteByUser(User user);
}
