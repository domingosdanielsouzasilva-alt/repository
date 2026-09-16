package pdcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import pdcc.model.Cliente;
import pdcc.repository.ClienteRepository;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("clientes", repository.findAll());
        model.addAttribute("cliente", new Cliente());

        return "clientes";
    }

    @PostMapping
    public String salvar(@ModelAttribute Cliente cliente) {

        repository.save(cliente);

        return "redirect:/clientes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        Cliente cliente = repository.findById(id).orElseThrow();

        model.addAttribute("cliente", cliente);
        model.addAttribute("clientes", repository.findAll());

        return "clientes";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        repository.deleteById(id);
        return "redirect:/clientes";
    }
}
