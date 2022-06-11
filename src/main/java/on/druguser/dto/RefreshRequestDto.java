package on.druguser.dto;


import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RefreshRequestDto {
	@NotBlank
	  private String refreshToken;
	
	  public String getRefreshToken() {
	    return refreshToken;
	  }
	  
	  public void setRefreshToken(String refreshToken) {
	    this.refreshToken = refreshToken;
	   }
}
