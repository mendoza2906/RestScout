package app.web.scout.model.pojo.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import app.web.scout.model.pojo.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public class UserAccount implements UserDetails {

	private static final long serialVersionUID = 3210866715261224564L;
	
    @Getter @Setter private Integer id;
	
    @Getter @Setter private String username;
    
    @JsonIgnore
    @Getter @Setter private String password;
    
    @Getter @Setter private String firstName;
    
    @Getter @Setter private String lastName;
    
    @Getter @Setter private String email;

    @Getter @Setter private String notificationsUUID;
    
    @Getter @Setter private String notificationsToken;

    @Getter @Setter private List<String> roles = new ArrayList<>();
    
    @Getter @Setter private boolean accountNonExpired;
    
    @Getter @Setter private boolean accountNonLocked;
    
    @Getter @Setter private boolean credentialsNonExpired;
    
    @Getter @Setter private boolean enableNotifications;

    @Getter @Setter private boolean enabled;

    public UserAccount(Usuario usuario) {
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        
        this.id = usuario.getId();
        this.username = usuario.getUsuario();
        this.password = usuario.getClave();
        this.firstName = usuario.getScout().getNombres();
        this.lastName = usuario.getScout().getApellidos();
        this.accountNonLocked = true;
        this.enableNotifications = true;
        
        this.setNotificationsUUID(null);
        this.setNotificationsToken(null);
        
//        if (usuario.getModuloRolUsuario() != null) {
//        	for (ModuloRolUsuario usuarioPrivilegio : usuario.getModuloRolUsuario()) {
//        		roles.add(usuarioPrivilegio.getModuloRol().getRol().getNombre());
//			}
//        }
        
    }

    @Override
    public List<GrantedAuthority> getAuthorities(){
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
        return authorities;
    }

    @Override
    public String toString() {
        String toStr = String.format("Account[%s]",id);
        toStr += String.format("\n username[%s]",username);
        toStr += String.format("\n | firstName[%s]", firstName);
        toStr += String.format("\n | lastName[%s]", lastName);
        toStr += String.format("\n | email[%s]", email);
        return toStr;
    }
}
