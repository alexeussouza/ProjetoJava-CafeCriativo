package app.cafeteria.service;

import app.cafeteria.model.*;
import app.cafeteria.repository.ReceitaClassicaRepository;
import app.cafeteria.util.IdentificadorDeReceita;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CafeService {

    private final ReceitaClassicaRepository receitaRepository;

    @Autowired
    private IdentificadorDeReceita identificadorDeReceita;

    /**
     * Gera um objeto CafePersonalizado com base nos ingredientes selecionados.
     * Também identifica se os ingredientes formam uma receita clássica.
     */
    public CafePersonalizado montarCafe(Set<Ingrediente> base, Set<Ingrediente> adicionais) {
        if (base == null || base.isEmpty()) {
            throw new IllegalArgumentException("É necessário selecionar pelo menos um ingrediente base.");
        }

        CafePersonalizado cafe = new CafePersonalizado();
        cafe.setIngredientesBase(base);
        cafe.setIngredientesAdicionais(adicionais);

        String nomeFinal = base.stream()
                .map(Ingrediente::getNome)
                .collect(Collectors.joining(" + "));
        cafe.setNomeFinal(nomeFinal);

        double precoBase = base.stream().mapToDouble(Ingrediente::getPreco).sum();
        double precoAdicionais = adicionais != null ? adicionais.stream().mapToDouble(Ingrediente::getPreco).sum()
                : 0.0;
        cafe.setPrecoTotal(precoBase + precoAdicionais);

        String sabor = identificadorDeReceita.identificarSabor(cafe);
        cafe.setSaborClassicoIdentificado(sabor);

        return cafe;
    }

    public CafePersonalizado montarCafePreview(Set<Ingrediente> base, Set<Ingrediente> adicionais) {
        if (base == null || base.isEmpty()) {
            return null; // ou crie um café vazio pra exibir algo padrão
        }

        // Monta uma instância temporária de café
        CafePersonalizado cafe = new CafePersonalizado();
        cafe.setIngredientesBase(new HashSet<>(base));
        cafe.setIngredientesAdicionais(new HashSet<>(adicionais));

        // Calcula nome e sabor se quiser que apareça no preview
        String nome = base.stream()
                .map(Ingrediente::getNome)
                .collect(Collectors.joining(" + "));
        cafe.setNomeFinal("Preview: " + nome);

        // Simula preço
        double precoBase = base.stream().mapToDouble(Ingrediente::getPreco).sum();
        double precoExtras = adicionais.stream().mapToDouble(Ingrediente::getPreco).sum();
        // 🍯 Aqui está o toque que faltava!
        String sabor = identificadorDeReceita.identificarSabor(cafe);

        cafe.setPrecoTotal(precoBase + precoExtras);

        // Simula sabor identificado (pode usar a mesma lógica real, se tiver)
        cafe.setSaborClassicoIdentificado(null); // Ou calcule se quiser

        return cafe;
    }

    private void validarQuantidade(Set<Ingrediente> lista, int limite, String tipo) {
        if (tipo.contains("base") && lista.isEmpty()) {
            throw new IllegalArgumentException("É necessário selecionar ao menos 1 ingrediente base.");
        }
        if (tipo.contains("adicional") && lista.size() > limite) {
            throw new IllegalArgumentException(
                    "Você pode selecionar no máximo " + limite + " ingredientes adicionais.");
        }
    }

    /**
     * Verifica se os ingredientes base correspondem a uma receita clássica
     * cadastrada.
     */
    private String identificarSaborClassico(Set<Ingrediente> selecionados) {
        List<ReceitaClassica> receitas = receitaRepository.findAll();

        for (ReceitaClassica receita : receitas) {
            Set<String> nomesSelecionados = nomes(selecionados);
            Set<String> nomesReceita = nomes(receita.getIngredientesBase());

            if (nomesSelecionados.equals(nomesReceita)) {
                return receita.getNome(); // sabor clássico reconhecido
            }
        }

        return null; // não identificado
    }

    private Set<String> nomes(Set<Ingrediente> ingredientes) {
        return ingredientes.stream()
                .map(i -> i.getNome().toLowerCase())
                .collect(Collectors.toSet());
    }

    /**
     * Calcula o preço total do café, considerando o preço base (se clássico) e
     * adicionais.
     */
    private Double calcularPrecoTotal(Set<Ingrediente> base, Set<Ingrediente> extras, String saborIdentificado) {
        double precoBase = 0.0;

        if (saborIdentificado != null) {
            Optional<ReceitaClassica> receita = receitaRepository.findByNome(saborIdentificado);
            precoBase = receita.map(r -> r.getPrecoBase() != null ? r.getPrecoBase() : 0.0).orElse(0.0);
        } else {
            precoBase = base.stream()
                    .mapToDouble(i -> i.getPreco() != null ? i.getPreco() : 0.0)
                    .sum();
        }

        double precoExtras = extras.stream()
                .mapToDouble(i -> i.getPreco() != null ? i.getPreco() : 0.0)
                .sum();

        return precoBase + precoExtras;
    }
}
