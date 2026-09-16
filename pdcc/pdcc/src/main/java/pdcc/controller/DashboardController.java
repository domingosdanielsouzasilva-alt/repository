package pdcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pdcc.repository.ClienteRepository;
import pdcc.repository.ServicoRepository;
import pdcc.repository.VeiculoRepository;

@Controller
public class DashboardController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalClientes", clienteRepository.count());
        model.addAttribute("totalVeiculos", veiculoRepository.count());
        model.addAttribute("totalServicos", servicoRepository.count());
        model.addAttribute("servicos", servicoRepository.findAll());

        return "dashboard";
    }
}
