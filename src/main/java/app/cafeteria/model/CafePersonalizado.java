package app.cafeteria.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CafePersonalizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeFinal;

    @ManyToMany
    @JoinTable(
        name = "cafe_ingredientes_base",
        joinColumns = @JoinColumn(name = "cafe_id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private Set<Ingrediente> ingredientesBase;

    @ManyToMany
    @JoinTable(
        name = "cafe_ingredientes_adicionais",
        joinColumns = @JoinColumn(name = "cafe_id"),
        inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private Set<Ingrediente> ingredientesAdicionais;

    private String saborClassicoIdentificado;

    private Double precoTotal;

    public String getPrecoFormatado() {
        return String.format("R$ %.2f", precoTotal != null ? precoTotal : 0.0);
    }
}
