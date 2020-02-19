package app.web.scout.ws;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import app.web.scout.model.pojo.Usuario;
import app.web.scout.model.repository.UsuarioRepository;


@RestController
@RequestMapping("/autenticacion")
@CrossOrigin
public class AutenticacionController {

	@Autowired private UsuarioRepository usuarioRepository;

	@RequestMapping(value="/autenticar", method=RequestMethod.POST)
	
	public ResponseEntity<?> autenticar(@RequestBody Usuario usuario) {
		if (usuario != null && usuario.getUsuario() != null && usuario.getClaveAuxiliar() != null) {
			Usuario usuarioAux = usuarioRepository.findByUsuario(usuario.getUsuario());
			
			// Si lo encontro y no tiene token, genera uno.
			if (usuarioAux != null) {
				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
				if (encoder.matches(usuario.getClaveAuxiliar(), usuarioAux.getClave())) {
					if (usuarioAux.getToken() == null) {
						usuarioAux.setToken(UUID.randomUUID().toString());//se puede generar una firma digital con la clase
						usuarioRepository.save(usuarioAux);
					}
					//usuarioaux.setToken()==null
					return ResponseEntity.ok(usuarioAux); //limpiar la clave, se puede limitar con el json ignore en el pojo
				}
			}
		}
		return ResponseEntity.notFound().build();
	}
}
