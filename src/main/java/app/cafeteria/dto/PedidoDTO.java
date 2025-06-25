package app.cafeteria.dto;

import lombok.Builder;
import lombok.Data;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PedidoDTO {
    private Long id;
    private String nomeCliente;
    private List<String> ingredientesBase;
    private List<String> ingredientesAdicionais;
    private Double precoTotal;
    private String saborIdentificado;
    private LocalDateTime dataHora;
    private String status;

    public String getPrecoFormatado() {
        return String.format("R$ %.2f", precoTotal != null ? precoTotal : 0.0);
    }

    public String getDataHoraFormatada() {
        if (dataHora == null)
            return "-";
        return dataHora.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }
}
