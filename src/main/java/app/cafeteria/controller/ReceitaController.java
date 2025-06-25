package app.cafeteria.controller;

import app.cafeteria.model.Ingrediente;
import app.cafeteria.model.ReceitaClassica;
import app.cafeteria.repository.IngredienteRepository;
import app.cafeteria.service.ReceitaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
@RequestMapping("/receitas")
@RequiredArgsConstructor
public class ReceitaController {

    private final ReceitaService receitaService;
    private final IngredienteRepository ingredienteRepository;

    @GetMapping
    public ModelAndView listar() {
        var mv = new ModelAndView("receitas/listar");
        mv.addObject("receitas", receitaService.listarTodas());
        return mv;
    }

    @GetMapping("/nova")
    public ModelAndView nova() {
        var mv = new ModelAndView("receitas/form");
        mv.addObject("ingredientes", ingredienteRepository.findAll());
        mv.addObject("receita", new ReceitaClassica());
        return mv;
    }

    @PostMapping
    public String salvar(@RequestParam String nome,
                         @RequestParam Double precoBase,
                         @RequestParam List<Long> ingredientesIds) {

        Set<Ingrediente> ingredientesSelecionados = new HashSet<>(ingredienteRepository.findAllById(ingredientesIds));
        receitaService.cadastrarReceita(nome, ingredientesSelecionados, precoBase);
        return "redirect:/receitas";
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        var mv = new ModelAndView("receitas/form");
        var receita = receitaService.listarTodas()
            .stream().filter(r -> r.getId().equals(id)).findFirst().orElse(null);
        mv.addObject("receita", receita);
        mv.addObject("ingredientes", ingredienteRepository.findAll());
        return mv;
    }

    @PostMapping("/atualizar/{id}")
    public String atualizar(@PathVariable Long id,
                            @RequestParam String nome,
                            @RequestParam Double precoBase,
                            @RequestParam List<Long> ingredientesIds) {

        Set<Ingrediente> novosIngredientes = new HashSet<>(ingredienteRepository.findAllById(ingredientesIds));
        receitaService.atualizar(id, nome, novosIngredientes, precoBase);
        return "redirect:/receitas";
    }

    @GetMapping("/remover/{id}")
    public String remover(@PathVariable Long id) {
        receitaService.remover(id);
        return "redirect:/receitas";
    }
}
