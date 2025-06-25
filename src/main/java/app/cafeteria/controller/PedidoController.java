package app.cafeteria.controller;

import app.cafeteria.dto.PedidoDTO;
import app.cafeteria.model.*;
import app.cafeteria.repository.IngredienteRepository;
import app.cafeteria.repository.PedidoRepository;
import app.cafeteria.service.CafeService;
import app.cafeteria.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final IngredienteRepository ingredienteRepository;
    private final PedidoRepository pedidoRepository;
    private final CafeService cafeService;
    private final PedidoService pedidoService;

    private Set<Ingrediente> ingredientesBaseSelecionados = new HashSet<>();
    private Set<Ingrediente> ingredientesAdicionaisSelecionados = new HashSet<>();
    private CafePersonalizado cafeMontado = null;

    @GetMapping("/montar")
    public ModelAndView montarCafe() {
        ModelAndView mv = new ModelAndView("pedidos/montar-cafe");
        mv.addObject("ingredientesBase", ingredienteRepository.findByTipo(TipoIngrediente.BASE));
        mv.addObject("ingredientesAdicionais", ingredienteRepository.findByTipo(TipoIngrediente.ADICIONAL));
        mv.addObject("cafe", cafeMontado);
        return mv;
    }

    @PostMapping("/base")
    public ModelAndView selecionarBase(@RequestParam("idsBase") List<Long> idsSelecionados) {
        ingredientesBaseSelecionados = ingredienteRepository.findAllById(idsSelecionados)
                .stream().collect(Collectors.toSet());

        // Atualiza o preview
        cafeMontado = cafeService.montarCafePreview(ingredientesBaseSelecionados, ingredientesAdicionaisSelecionados);
        return new ModelAndView("redirect:/pedidos/montar");
    }

    @PostMapping("/adicionais")
    public ModelAndView selecionarAdicionais(@RequestParam("idsAdicionais") List<Long> idsSelecionados) {
        ingredientesAdicionaisSelecionados = ingredienteRepository.findAllById(idsSelecionados)
                .stream().collect(Collectors.toSet());

        // Atualiza o preview
        cafeMontado = cafeService.montarCafePreview(ingredientesBaseSelecionados, ingredientesAdicionaisSelecionados);
        return new ModelAndView("redirect:/pedidos/montar");
    }

    @PostMapping("/finalizar")
    public ModelAndView montarFinalCafe(@RequestParam("nomeCliente") String nomeCliente) {
        ModelAndView mv;
        try {
            cafeMontado = cafeService.montarCafe(ingredientesBaseSelecionados, ingredientesAdicionaisSelecionados);
            pedidoService.confirmarPedido(nomeCliente, cafeMontado);
            mv = new ModelAndView("resumo");
            mv.addObject("cafeConfirmado", cafeMontado);
            ingredientesBaseSelecionados.clear();
            ingredientesAdicionaisSelecionados.clear();
        } catch (IllegalArgumentException e) {
            mv = new ModelAndView("pedidos/montar-cafe");
            mv.addObject("erro", e.getMessage());
            mv.addObject("ingredientesBase", ingredienteRepository.findByTipo(TipoIngrediente.BASE));
            mv.addObject("ingredientesAdicionais", ingredienteRepository.findByTipo(TipoIngrediente.ADICIONAL));
            mv.addObject("cafe", cafeMontado);
        }
        return mv;
    }

    @GetMapping("/meus-pedidos")
    public ModelAndView listarPedidosDoUsuario(Principal principal) {
        List<Pedido> pedidos = pedidoRepository.findByNomeCliente(principal.getName());

        List<PedidoDTO> lista = pedidos.stream()
                .map(p -> {
                    List<String> base = p.getCafe().getIngredientesBase()
                            .stream()
                            .map(Ingrediente::getNome)
                            .toList();

                    List<String> adicionais = p.getCafe().getIngredientesAdicionais()
                            .stream()
                            .map(Ingrediente::getNome)
                            .toList();

                    return PedidoDTO.builder()
                            .id(p.getId())
                            .nomeCliente(p.getNomeCliente())
                            .ingredientesBase(base)
                            .ingredientesAdicionais(adicionais)
                            .precoTotal(p.getCafe().getPrecoTotal())
                            .saborIdentificado(p.getCafe().getSaborClassicoIdentificado())
                            .dataHora(p.getDataHora())
                            .status(p.getStatus())
                            .build();
                })
                .toList();

        ModelAndView mv = new ModelAndView("pedidos/meus-pedidos");
        mv.addObject("pedidos", lista);
        return mv;
    }

}
