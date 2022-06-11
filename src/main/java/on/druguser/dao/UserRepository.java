package on.druguser.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import on.druguser.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	//Stream <User> findByActive(List <User> users);
	@Query(value ="from User s where s.username=:username")
	Optional <User> findByUsername(String username);
	@Query(value ="from User s where s.email=:email")
	Optional <User> findByEmail(String email);
	Optional <User> findById(Long id);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
}
