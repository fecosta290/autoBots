package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.service.EmpresaService;
import com.autobots.automanager.service.UsuarioService;
import com.autobots.automanager.service.VeiculoService;
import com.autobots.automanager.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/venda")

public class VendaController {

	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private VeiculoService veiculoService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@GetMapping("/vendas")
	public ResponseEntity<List<Venda>> ObterVendas(){
		HttpStatus status = HttpStatus.CONFLICT;
        List<Venda> venda = vendaService.findAll();
        if (venda.isEmpty()) {
           	status = HttpStatus.NOT_FOUND;
            ResponseEntity<List<Venda>> resposta = new ResponseEntity<>(status);
            return resposta;
        } else {
        	status = HttpStatus.FOUND;
        	vendaService.adicionarLink(venda);
            ResponseEntity<List<Venda>> resposta = new ResponseEntity<List<Venda>>(venda, status);
            return resposta;
        }
	}
	
	@GetMapping("/venda/{id}")
	public ResponseEntity<Venda> ObterVenda(@PathVariable Long id){
		Venda venda = vendaService.findById(id);
        if (venda == null) {
            ResponseEntity<Venda> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
        	vendaService.adicionarLink(venda);
            ResponseEntity<Venda> resposta = new ResponseEntity<Venda>(venda, HttpStatus.FOUND);
            return resposta;
        }
	}
	
	@PostMapping("/venda_enviar/{id}")
	public ResponseEntity<?> cadastrarVenda(@RequestBody Venda venda, @PathVariable Long id){
	    HttpStatus status = HttpStatus.CONFLICT;
		Empresa empresa_escolhido = empresaService.findById(id);
		if(empresa_escolhido != null) {
			Usuario funcionarioSelecionado = usuarioService.findById(venda.getFuncionario().getId());
			Usuario clienteSelecionado = usuarioService.findById(venda.getCliente().getId());
			Veiculo veiculoSelecionado = veiculoService.findById(venda.getVeiculo().getId());
			venda.setVeiculo(veiculoSelecionado);
			venda.setCliente(clienteSelecionado);
			venda.setFuncionario(funcionarioSelecionado);
			usuarioService.create(funcionarioSelecionado);

			empresa_escolhido.getVendas().add(venda);
			empresaService.create(empresa_escolhido);

			vendaService.create(venda);
			
			status = HttpStatus.CREATED;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
	    ResponseEntity<Venda> resposta = new ResponseEntity<>(status);
	    return resposta;
	}
	
    @PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizarVenda(@RequestBody Venda venda, @PathVariable Long id){
        HttpStatus status = HttpStatus.CONFLICT;
        Venda Venda = vendaService.findById(id);
        if (Venda != null) {
			venda.setId(id);
			vendaService.update(venda);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        ResponseEntity<Venda> resposta = new ResponseEntity<>(status);
        return resposta;
    }
    
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Venda venda = vendaService.findById(id);
		if(venda != null) {
			vendaService.delete(venda);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.NOT_FOUND;	
		}
		ResponseEntity<Venda> resposta = new ResponseEntity<>(status);
		return resposta;
	}
	
}
