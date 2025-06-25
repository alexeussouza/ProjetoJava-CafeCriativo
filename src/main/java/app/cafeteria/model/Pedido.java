package app.cafeteria.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Representação do cliente (iremos simplificar neste momento)
    private String nomeCliente;

    // Café montado no momento do pedido
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cafe_id", referencedColumnName = "id")
    private CafePersonalizado cafe;

    // Data/hora do pedido
    private LocalDateTime dataHora;

    // Status do pedido (opcional para futura expansão)
    private String status;
}
