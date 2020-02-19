package app.web.scout.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.web.scout.model.repository.UsuarioRepository;
import app.web.scout.model.service.SecurityService;

@RestController
@RequestMapping("/app")
@CrossOrigin
public class AppController {

//	@Autowired private UsuarioRepository usuarioRepository;
//	@Autowired private SecurityService securityService;
//
//	@RequestMapping(value="/buscarModulos/{usuario}", 
//			method=RequestMethod.GET)
//	public ResponseEntity<?> buscarModulos(@RequestHeader(value="Authorization") String authorization, 
//			@PathVariable("usuario") String usuario) {
//		if (!securityService.isTokenValido(authorization)) {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
//		return ResponseEntity.ok(usuarioRepository.buscarUsuarioModulos(usuario));
//	}
//	
}
