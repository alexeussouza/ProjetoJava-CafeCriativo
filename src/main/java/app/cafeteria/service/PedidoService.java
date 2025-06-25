package app.cafeteria.service;

import app.cafeteria.model.CafePersonalizado;
import app.cafeteria.model.Pedido;
import app.cafeteria.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    /**
     * //Cria e persiste um novo pedido com base no café personalizado.
     *
     * @param nomeCliente //nome informado na finalização
     * @param cafe // cafe montado com base nas escolhas do cliente
     * @return //objeto Pedido persistido
     */
    public Pedido confirmarPedido(String nomeCliente, CafePersonalizado cafe) {
        Pedido pedido = Pedido.builder()
                .nomeCliente(nomeCliente)
                .cafe(cafe)
                .dataHora(LocalDateTime.now())
                .status("AGUARDANDO_PREPARO") // status inicial (pode ser expandido depois)
                .build();

        return pedidoRepository.save(pedido);
    }
}
