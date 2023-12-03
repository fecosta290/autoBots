package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.service.EmpresaService;
import com.autobots.automanager.service.ServicoService;
import com.autobots.automanager.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/servico")
public class ServicoController {

	@Autowired
	private ServicoService servicoService;

	@Autowired
	private VendaService vendaService;

	@Autowired
	EmpresaService empresaService;
	
	@GetMapping("/servicos")
	public ResponseEntity<List<Servico>> ObterServicos(){
        List<Servico> servicos = servicoService.findAll();
        if (servicos.isEmpty()) {
            ResponseEntity<List<Servico>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
        	servicoService.adicionarLink(servicos);
            ResponseEntity<List<Servico>> resposta = new ResponseEntity<List<Servico>>(servicos, HttpStatus.FOUND);
            return resposta;
        }
	}
	
	@GetMapping("/servico/{id}")
	public ResponseEntity<Servico> ObterServico(@PathVariable long id){
		HttpStatus status = HttpStatus.CONFLICT;
		Servico servico = servicoService.findById(id);
        if (servico == null) {
        	status = HttpStatus.NOT_FOUND;
            ResponseEntity<Servico> resposta = new ResponseEntity<>(status);
            return resposta;
        } else {
        	status = HttpStatus.FOUND;
        	servicoService.adicionarLink(servico);
            ResponseEntity<Servico> resposta = new ResponseEntity<Servico>(servico, status);
            return resposta;
        }
	}
	
    @PostMapping("/servico_enviar")
    public ResponseEntity<?> CadastrarServico(@RequestBody Servico servico, @PathVariable Long id ) {
        HttpStatus status = HttpStatus.CONFLICT;
    	Empresa empresa = empresaService.findById(id);
        if (empresa != null) {
    		empresa.getServicos().add(servico);
    		empresaService.create(empresa);
            status = HttpStatus.CREATED;
        } else {
        	status = HttpStatus.BAD_REQUEST;
        }
        ResponseEntity<Servico> resposta = new ResponseEntity<>(status);
        return resposta;
    }	
	
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> Atualizar(@RequestBody Servico servico , @PathVariable Long id) {
        HttpStatus status = HttpStatus.CONFLICT;
        Servico Servico = servicoService.findById(id);
        if (Servico != null) {
    		servico.setId(id);
    		servicoService.update(servico);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(status);
        return resposta;
    }
	
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
		Servico servico_escolhido = servicoService.findById(id);
		
		if(servico_escolhido == null) {
			ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
			return resposta; 
		}
		
		List<Empresa> empresas = empresaService.findAll();
	    List<Venda> vendas = vendaService.findAll();
	    for (Empresa empresa: empresas) {
	        for (Servico servico: empresa.getServicos()) {
	          if (servico.getId().equals(id)) {
	        	  empresa.getServicos().remove(servico);
	          }
	        }
	      }
	    
	      for (Venda venda: vendas) {
	        for (Servico servico: venda.getServicos()) {
	          if (servico.getId().equals(id)) {
	        	  venda.getServicos().remove(servico);
	          }
	        }
	      }
	
	    servicoService.delete(servico_escolhido);
		ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(HttpStatus.OK);
		return resposta; 
	    
	}
}
