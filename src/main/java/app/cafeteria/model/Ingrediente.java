package app.cafeteria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingredientes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ingrediente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do ingrediente (ex: Expresso, Leite, Chantilly)
    @Column(nullable = false, unique = true)
    private String nome;

    // Define se o ingrediente é do tipo BASE ou ADICIONAL
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoIngrediente tipo;

    // Custo unitário do ingrediente
    private Double preco;

    // Quantidade atual em estoque
    private Integer estoqueAtual;

    public String getPrecoFormatado() {
        return String.format("R$ %.2f", preco != null ? preco : 0.0);
    }
}