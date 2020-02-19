package app.web.scout.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * Rest utilities
 * @author Luis
 *
 */
public class RestUtil {

	public static ResponseEntity<?> preconditionFailedError(String errorText) {
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
				 .contentType(MediaType.TEXT_PLAIN)
				 .body(errorText); 
	}

	public static ResponseEntity<?> notFoundError() {
		return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED)
				 .contentType(MediaType.TEXT_PLAIN)
				 .body("No se encontró información");
	}

}
