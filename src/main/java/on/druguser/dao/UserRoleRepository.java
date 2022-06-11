package on.druguser.dao;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import on.druguser.model.UserRole;
import on.druguser.model.UserRoleId;

public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId> {

	@Query(value = "from UserRole s where s.roleId=:id order by roleStart desc")
	Stream<UserRole> findByRoleId(@Param("id") Long id);

	@Query(value = "from UserRole s where s.userId=:id  order by roleStart desc")
	Stream<UserRole> findByUserId(@Param("id") Long id);

	@Query(value = "from UserRole s where s.userId=:id and s.roleStart<:before and s.roleEnd>:after order by roleStart desc")
	Stream<UserRole> findByUserIdAndRoleStartBeforeAndRoleEndAfter(@Param("id") Long id,
			@Param("before") OffsetDateTime before, @Param("after") OffsetDateTime after);

	Optional<UserRole> findFirstByUserIdAndRoleStartBeforeAndRoleEndAfter(Long id, OffsetDateTime before,
			OffsetDateTime after);
	
	@Query(value = "from UserRole s where s.userId=:id and s.roleStart<:role_start")
	Stream<UserRole> getByUserIdAndRoleStartBefore(@Param("id") Long id,
			@Param("role_start") OffsetDateTime role_start);

	@Query(value = "from UserRole s where s.roleStart>:after and s.roleStart<:before")
	Stream<UserRole> findByRoleStartAfterAndRoleStartBefore(@Param("after") OffsetDateTime after,
			@Param("before") OffsetDateTime before);

}