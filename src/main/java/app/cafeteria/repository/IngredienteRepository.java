package app.cafeteria.repository;

import app.cafeteria.model.Ingrediente;
import app.cafeteria.model.TipoIngrediente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    // Lista os ingredientes por tipo (BASE ou ADICIONAL)
    List<Ingrediente> findByTipo(TipoIngrediente tipo);

    // Busca por nome (utilizado para evitar duplicações)
    Optional<Ingrediente> findByNome(String nome);
}
