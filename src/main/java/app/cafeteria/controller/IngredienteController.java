package app.cafeteria.controller;

import app.cafeteria.model.Ingrediente;
import app.cafeteria.model.TipoIngrediente;
import app.cafeteria.repository.IngredienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/ingredientes")
@RequiredArgsConstructor
public class IngredienteController {

    private final IngredienteRepository ingredienteRepository;

    @GetMapping("/novo")
    public ModelAndView exibirFormulario() {
        ModelAndView mv = new ModelAndView("ingredientes/form");
        mv.addObject("ingrediente", new Ingrediente());
        mv.addObject("tipos", TipoIngrediente.values());
        return mv;
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Ingrediente ingrediente) {
        ingredienteRepository.save(ingrediente);
        return "redirect:/ingredientes/listar?sucesso";
    }

    @GetMapping("/listar")
    public ModelAndView listarIngredientes() {
        ModelAndView mv = new ModelAndView("ingredientes/listar");
        mv.addObject("ingredientes", ingredienteRepository.findAll());
        return mv;
    }

    @GetMapping("/editar/{id}")
    public ModelAndView editar(@PathVariable Long id) {
        Ingrediente ingrediente = ingredienteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ingrediente não encontrado"));

        ModelAndView mv = new ModelAndView("ingredientes/form");
        mv.addObject("ingrediente", ingrediente);
        mv.addObject("tipos", TipoIngrediente.values());
        return mv;
    }

}
