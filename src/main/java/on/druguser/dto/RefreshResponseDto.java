package on.druguser.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RefreshResponseDto {
		  private String accessToken;
		  private String refreshToken;
		  private String tokenType = "Bearer";
		  
		  public RefreshResponseDto(String accessToken, String refreshToken) {
		    this.accessToken = accessToken;
		    this.refreshToken = refreshToken;
	}
}
