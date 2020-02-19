package app.web.scout.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import app.web.scout.model.repository.UsuarioRepository;

/**
 * LVT Nueva version de la clase.
 * @author ltorres
 *
 */
@Service
@Transactional
public class SecurityService implements UserDetailsService {
	
	@Autowired private UsuarioRepository usuarioRepository;
	
	public boolean isTokenValido(String token) {
		return true;
//		if (token != null) {
//			String tokenAux = token.replace("Bearer ", "");
//			Usuario usuario = usuarioRepository.findByToken(tokenAux);
//			return usuario != null;
//		}
//		return false;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

}
