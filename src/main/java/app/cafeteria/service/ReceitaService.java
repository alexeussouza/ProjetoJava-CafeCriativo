package app.cafeteria.service;

import app.cafeteria.model.Ingrediente;
import app.cafeteria.model.ReceitaClassica;
import app.cafeteria.repository.ReceitaClassicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ReceitaService {

    private final ReceitaClassicaRepository receitaRepository;

    /**
     * Retorna todas as receitas clássicas cadastradas no sistema.
     */
    public List<ReceitaClassica> listarTodas() {
        return receitaRepository.findAll();
    }

    /**
     * Cadastra uma nova receita clássica.
     *
     * @param nome nome da receita (ex: "Latte")
     * @param ingredientesBase conjunto exato de ingredientes base
     */
    public ReceitaClassica cadastrarReceita(String nome, Set<Ingrediente> ingredientesBase, Double precoBase) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da receita não pode ser vazio.");
        }
        if (ingredientesBase == null || ingredientesBase.isEmpty()) {
            throw new IllegalArgumentException("Uma receita clássica deve ter pelo menos um ingrediente base.");
        }

        // Verifica duplicidade de nome
        Optional<ReceitaClassica> existente = receitaRepository.findByNome(nome);
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Já existe uma receita com esse nome.");
        }

        ReceitaClassica receita = ReceitaClassica.builder()
                .nome(nome)
                .ingredientesBase(ingredientesBase)
                .precoBase(precoBase)
                .build();

        return receitaRepository.save(receita);
    }

    /**
     * Remove uma receita clássica por ID.
     */
    public void remover(Long id) {
        if (!receitaRepository.existsById(id)) {
            throw new IllegalArgumentException("Receita não encontrada.");
        }
        receitaRepository.deleteById(id);
    }

    /**
     * Atualiza o nome, ingredientes e preço de uma receita já existente.
     */
    public ReceitaClassica atualizar(Long id, String novoNome, Set<Ingrediente> novosIngredientes, Double novoPreco) {
        ReceitaClassica receita = receitaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Receita não encontrada."));

        if (novoNome != null && !novoNome.trim().isEmpty()) {
            receita.setNome(novoNome);
        }

        if (novosIngredientes != null && !novosIngredientes.isEmpty()) {
            receita.setIngredientesBase(novosIngredientes);
        }

        receita.setPrecoBase(novoPreco);
        return receitaRepository.save(receita);
    }
}