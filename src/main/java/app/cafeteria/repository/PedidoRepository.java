package app.cafeteria.repository;

import app.cafeteria.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // Opcional: listar todos os pedidos de um cliente específico
    List<Pedido> findByNomeCliente(String nomeCliente);
}
