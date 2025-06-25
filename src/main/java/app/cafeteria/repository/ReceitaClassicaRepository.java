package app.cafeteria.repository;

import app.cafeteria.model.ReceitaClassica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReceitaClassicaRepository extends JpaRepository<ReceitaClassica, Long> {

    // Busca por nome exato da receita
    Optional<ReceitaClassica> findByNome(String nome);
}
