package app.web.scout.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.web.scout.model.pojo.Usuario;
import app.web.scout.model.repository.security.UsuarioRepo;
import app.web.scout.util.Constant;

@Service
@Transactional
public class UsuarioService {

    @Autowired private UsuarioRepo usuarioRepo;
    @Autowired private PasswordEncoder passwordEncoder;
    
    public void saveUser(Usuario usuario) {
    	
		Usuario userToSave;
		
		if (usuario.getId() == null) {
			if (usuario.getUsuario() == null) {
				throw new RuntimeException("El atributo Usuario es obligatorio.");
			}

			// New Record
			userToSave = new Usuario();
			userToSave.setUsuario(usuario.getUsuario());
			userToSave.setClave(passwordEncoder.encode(usuario.getUsuario()));
		} else {
			// Update record.
			userToSave = usuarioRepo.getOne(usuario.getId()); 
			if (userToSave == null) {
				throw new RuntimeException("No se encontró el usuario con el ID:" + usuario.getId());
			}
		}
		
		usuarioRepo.save(userToSave);
    }

	public void deleteUser(Integer userId) {

		Usuario userToSave = usuarioRepo.getOne(userId); 
		if (userToSave == null) {
			throw new RuntimeException("No se encontró el usuario con el ID:" + userId);
		}
		
		userToSave.setEstado(Constant.DELETED_STATUS);
		
		usuarioRepo.save(userToSave);
		
	}


}
