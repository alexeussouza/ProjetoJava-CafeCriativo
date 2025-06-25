package app.cafeteria.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import app.cafeteria.model.Usuario;
import app.cafeteria.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/cadastrar")
    public ModelAndView mostrarFormulario() {
        return new ModelAndView("usuarios/cadastrar");
    }

    @PostMapping("/cadastrar")
    public ModelAndView processarCadastro(
            @RequestParam String login,
            @RequestParam String senha,
            @RequestParam String nome) {

        if (usuarioRepository.existsByLogin(login)) {
            ModelAndView mv = new ModelAndView("usuarios/cadastrar");
            mv.addObject("erro", "Usuário já existe.");
            return mv;
        }

        Usuario novo = new Usuario();
        novo.setLogin(login);
        novo.setSenha(passwordEncoder.encode(senha));
        novo.setNome(nome);
        novo.setAdministrador(false);

        {/* Teste para verificar valor de login, nome e senha */}
        System.out.println("DEBUG >> login: " + login); 
        System.out.println("DEBUG >> nome: " + nome);
        System.out.println("DEBUG >> senha: " + senha);

        usuarioRepository.save(novo);

        return new ModelAndView("redirect:/login");
    }

    @PostMapping("/seed")
    public ResponseEntity<?> criarAdmin() {
        if (usuarioRepository.findByLogin("admin").isEmpty()) {
            Usuario admin = Usuario.builder()
                    .login("admin")
                    .senha(passwordEncoder.encode("1234"))
                    .nome("Administrador")
                    .administrador(true)
                    .build();
            usuarioRepository.save(admin);
            return ResponseEntity.ok("Usuário admin criado!");
        }
        return ResponseEntity.ok("Usuário já existe.");
    }
}
