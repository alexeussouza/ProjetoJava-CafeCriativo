package app.cafeteria.util;

import app.cafeteria.model.CafePersonalizado;
import app.cafeteria.model.Ingrediente;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class IdentificadorDeReceita {

    public String identificarSabor(CafePersonalizado cafe) {
        Set<String> nomes = cafe.getIngredientesBase().stream()
                .map(Ingrediente::getNome)
                .map(String::toLowerCase)
                .collect(java.util.stream.Collectors.toSet());

        cafe.getIngredientesAdicionais().stream()
                .map(Ingrediente::getNome)
                .map(String::toLowerCase)
                .forEach(nomes::add);

        if (nomes.contains("expresso") && nomes.contains("leite") && nomes.contains("espuma")) {
            return "Capuccino";
        }
        if (nomes.contains("coado") && nomes.contains("chantilly") && nomes.contains("canela")) {
            return "Doce Colonial";
        }
        if (nomes.contains("expresso") && nomes.contains("caramelo")) {
            return "Macchiato Caramelo";
        }

        return "Criação Exclusiva";
    }
}
