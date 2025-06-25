package app.cafeteria.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "receitas_classicas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReceitaClassica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nome do café clássico (ex: Mocha, Latte)
    @Column(nullable = false, unique = true)
    private String nome;

    // Ingredientes base que definem a receita clássica
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "receita_ingredientes",
        joinColumns = @JoinColumn(name = "receita_id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private Set<Ingrediente> ingredientesBase;

    // Preço base da receita (opcional)
    private Double precoBase;

    public String getPrecoFormatado() {
        return String.format("R$ %.2f", precoBase != null ? precoBase : 0.0);
    }
}
