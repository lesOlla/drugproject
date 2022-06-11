package on.druguser.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import on.druguser.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	
	//@Query(value ="from Role r left join Route a where a.id =:routeid", nativeQuery = true)
	//Stream <Role> findRoleByRoutesId(@Param("routeid") Long routeid);

	//Stream<Role> findByStart(List <User> users);
	//<Role> findByRoutes(List <Route> routes);
	@Query(value ="from Role s where s.name=:name")
	Optional <Role> findByName(String name);
	Optional<Role> findById(long i);	
	
}
