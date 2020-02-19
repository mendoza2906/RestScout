package app.web.scout.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import app.web.scout.model.pojo.Usuario;
import app.web.scout.model.pojo.security.UserAccount;
import app.web.scout.model.repository.security.UsuarioRepo;

@Service
@Transactional
public class AccountService implements UserDetailsService {

    @Autowired private UsuarioRepo usuarioRepo;
    @Autowired private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepo.findByUsuario(username);
        if ( usuario.isPresent() ) {
        	UserAccount userAccount = new UserAccount(usuario.get());
            return userAccount;
        } else {
            throw new UsernameNotFoundException(String.format("Usuario[%s] no encontrado", username));
        }
    }

    public UserAccount findAccountByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuario = usuarioRepo.findByUsuario(username);
        if ( usuario.isPresent() ) {
            return new UserAccount(usuario.get());
        } else {
            throw new UsernameNotFoundException(String.format("Usuario[%s] no encontrado", username));
        }
    }

	public void changePassword(Usuario usuario, String nuevaClave) {

		usuario = usuarioRepo.findByUsuario(usuario.getUsuario()).get();
		
		usuario.setClave(passwordEncoder.encode(nuevaClave));
		
		usuarioRepo.save(usuario);
		
	}

}
