package app.web.scout.rest;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.web.scout.model.pojo.Usuario;
import app.web.scout.model.repository.security.UsuarioRepo;
import app.web.scout.services.UsuarioService;
import app.web.scout.util.RestUtil;

/**
 * Controller for users.
 * @author Luis
 *
 */
@RestController
@RequestMapping(path="/api/users")
public class UserController extends Controller {

	@Autowired private UsuarioService usuarioService;
    @Autowired private UsuarioRepo usuarioRepo;

    @GetMapping("/get-user")
    public ResponseEntity<?> getUser(Principal principal) {
    	Usuario retorno = usuarioRepo.findByUsuario(principal.getName()).get();
    	if (retorno == null) {
    		return RestUtil.notFoundError();
    	}
    	return ResponseEntity.ok(retorno);
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<?> deleteUser(Principal principal, @PathVariable("userId") Integer userId) {
    	if (userId == null) {
    		return RestUtil.notFoundError();
    	}
    	
    	usuarioService.deleteUser(userId);
    	
    	return ResponseEntity.ok().build();
    }

}
