package pdcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pdcc.model.Servico;
import pdcc.repository.ServicoRepository;
import pdcc.repository.VeiculoRepository;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoRepository servicoRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("servicos", servicoRepository.findAll());
        model.addAttribute("veiculos", veiculoRepository.findAll());
        model.addAttribute("servico", new Servico());

        return "servicos";
    }

    @PostMapping
    public String salvar(@ModelAttribute Servico servico) {
        servicoRepository.save(servico);
        return "redirect:/servicos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        servicoRepository.deleteById(id);
        return "redirect:/servicos";
    }
}
