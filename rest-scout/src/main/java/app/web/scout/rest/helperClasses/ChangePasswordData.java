package app.web.scout.rest.helperClasses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Helper class to password change.
 * @author Luis
 *
 */
@NoArgsConstructor
public class ChangePasswordData {
	
	@Getter @Setter private String claveActual;
	@Getter @Setter private String nuevaClave;
	@Getter @Setter private String nuevaClaveConfirmacion;

}
