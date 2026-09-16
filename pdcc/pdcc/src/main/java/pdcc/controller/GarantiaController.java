package pdcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pdcc.repository.ServicoRepository;

import java.time.LocalDate;
@Controller
public class GarantiaController {

    @Autowired
    private ServicoRepository servicoRepository;

    @GetMapping("/garantias")
    public String garantias(Model model) {
        model.addAttribute("servicos", servicoRepository.findAll());
        model.addAttribute("hoje", LocalDate.now());

        return "garantias";
    }
}
