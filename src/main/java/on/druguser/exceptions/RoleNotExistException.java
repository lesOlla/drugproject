package on.druguser.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class RoleNotExistException extends RuntimeException {/**
	 * 
	 */
	private static final long serialVersionUID = -7520815752104241896L;

}
