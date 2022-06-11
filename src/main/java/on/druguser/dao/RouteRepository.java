package on.druguser.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import on.druguser.model.Route;

public interface RouteRepository
		extends JpaRepository<Route, Long> {
	
		
	//@Query(value ="from Route r left join Role a where a.id =:roleId", nativeQuery = true)
	//Stream <Route> findRoutesByRolesId(@Param("roleId") Long roleId);
	
	
	/*
	 * @Query(value =
	 * "FROM route r where r.id = (select r.id from sign.role_route where role_id = id);"
	 * ) Set<Route> findRouteByRole(String role);
	 */

}
