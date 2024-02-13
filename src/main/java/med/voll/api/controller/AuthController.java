package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.DadosAutenticacao;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJWT;
import med.voll.api.infra.security.TokenService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity  login(@RequestBody @Valid DadosAutenticacao dados){
        
        var authToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        var authenticate = manager.authenticate(authToken);

        var tokenGeradoJWT = tokenService.gerarToken((Usuario) authenticate.getPrincipal());

        return ResponseEntity.ok(new DadosTokenJWT(tokenGeradoJWT));
    }
}
