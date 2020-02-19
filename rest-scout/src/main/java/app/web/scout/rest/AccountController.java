package app.web.scout.rest;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import app.web.scout.model.pojo.security.Privilegio;
import app.web.scout.model.pojo.security.UserAccount;
import app.web.scout.model.repository.security.PrivilegioRepo;
import app.web.scout.services.AccountService;

@RestController
@RequestMapping(path="/api/account")
public class AccountController extends Controller {

    @Autowired private AccountService accountService;
    @Autowired private PrivilegioRepo privilegioRepo;

    @GetMapping("/details")
    public ResponseEntity<?> getAccountDetails(Principal principal) {
    	UserAccount account = accountService.findAccountByUsername(principal.getName());
        return ResponseEntity.ok(account);
    }

    @GetMapping("/all-privileges")
    public ResponseEntity<?> getAllPrivileges(Principal principal) {
    	List<Privilegio> retorno = privilegioRepo.findAll();
        return ResponseEntity.ok(retorno);
    }

}

