package pdcc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pdcc.model.Veiculo;
import pdcc.repository.ClienteRepository;
import pdcc.repository.VeiculoRepository;
import pdcc.repository.ServicoRepository;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;

@Controller
@RequestMapping("/veiculos")
public class VeiculoController {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ServicoRepository servicoRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("veiculos", veiculoRepository.findAll());
        model.addAttribute("clientes", clienteRepository.findAll());
        model.addAttribute("veiculo", new Veiculo());
        return "veiculos";
    }

    @GetMapping("/{id}/historico")
    public String historico(@PathVariable Long id, Model model) {
        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow();
        model.addAttribute("veiculo", veiculo);
        model.addAttribute("servicos", servicoRepository.findByVeiculo(veiculo));
        return "historico-veiculo";
    }

    @GetMapping("/{id}/consulta")
    public String consultaPublica(@PathVariable Long id, Model model) {
        Veiculo veiculo = veiculoRepository.findById(id).orElseThrow();
        model.addAttribute("veiculo", veiculo);
        model.addAttribute("servicos", servicoRepository.findByVeiculo(veiculo));
        return "consulta-veiculo";
    }

    @PostMapping
    public String salvar(@ModelAttribute Veiculo veiculo) {
        veiculoRepository.save(veiculo);
        return "redirect:/veiculos";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        veiculoRepository.deleteById(id);
        return "redirect:/veiculos";
    }

    @GetMapping("/{id}/qrcode")
    public void gerarQrCode(@PathVariable Long id, HttpServletResponse response) throws Exception {
        String url = baseUrl + "/veiculos/" + id + "/consulta";
        BitMatrix matrix = new MultiFormatWriter().encode(
                url,
                BarcodeFormat.QR_CODE,
                250,
                250
        );
        response.setContentType("image/png");
        OutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(matrix, "PNG", outputStream);
        outputStream.close();
    }

    @GetMapping("/qrcodes")
    public String telaQrCodes(Model model) {
        model.addAttribute("veiculos", veiculoRepository.findAll());
        return "qrcodes";
    }
}
