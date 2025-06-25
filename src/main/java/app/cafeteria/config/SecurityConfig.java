package app.cafeteria.config;

import app.cafeteria.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final UsuarioRepository usuarioRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                // .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/login",
                                "/seed",
                                "/init-admin",
                                "/css/**", // libera CSS
                                "/js/**", // se tiver JS
                                "/images/**", // se tiver imagens
                                "/webjars/**", // libs front-end
                                "/cadastrar",
                                "/usuarios/cadastrar")
                        .permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/home", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            //System.out.println("🔐 Tentando autenticar login: " + username);

            return usuarioRepository.findByLogin(username)
                    .map(usuario -> {
                        //System.out.println("👤 Login encontrado no banco: " + usuario.getLogin());
                        //System.out.println("🔑 Senha criptografada: " + usuario.getSenha());
                        return User.withUsername(usuario.getLogin())
                                .password(usuario.getSenha())
                                .roles(usuario.isAdministrador() ? "ADMIN" : "USER")
                                .build();
                    })
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}