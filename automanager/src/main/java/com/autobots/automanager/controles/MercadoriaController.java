package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.service.EmpresaService;
import com.autobots.automanager.service.MercadoriaService;
import com.autobots.automanager.service.UsuarioService;
import com.autobots.automanager.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/mercadoria")
public class MercadoriaController {
	@Autowired
	private MercadoriaService mercadoriaService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	EmpresaService empresaService;
	
	@GetMapping("/mercadorias")
	public ResponseEntity<List<Mercadoria>> ObterMercadorias(){
		List<Mercadoria> mercadoria = mercadoriaService.findAll();
		if(mercadoria.isEmpty()) {
			ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<List<Mercadoria>>(HttpStatus.NOT_FOUND);
			return resposta;
		}
		mercadoriaService.adicionarLink(mercadoria);
		ResponseEntity<List<Mercadoria>> resposta = new ResponseEntity<List<Mercadoria>>(mercadoria, HttpStatus.FOUND);
		return resposta;
	}
	
	@GetMapping("/mercadoria/{id}")
	public ResponseEntity<Mercadoria> ObterMercadoria(@PathVariable long id){
		Mercadoria mercadoria = mercadoriaService.findById(id);
        if (mercadoria == null) {
            ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
        	mercadoriaService.adicionarLink(mercadoria);
            ResponseEntity<Mercadoria> resposta = new ResponseEntity<Mercadoria>(mercadoria, HttpStatus.FOUND);
            return resposta;
        }
	}
	
	@PostMapping("/mercadoria_enviar/{id}")
	public ResponseEntity<?> cadastrarMercadoria(@RequestBody Mercadoria mercadoria, @PathVariable Long id){
		HttpStatus status = HttpStatus.CONFLICT;
		Long mercadoria_id = mercadoriaService.create(mercadoria);
		Mercadoria nova_mercadoria = mercadoriaService.findById(mercadoria_id);
		Usuario usuario = usuarioService.findById(id);
		
		if(usuario != null) {	
			for(Empresa empresa: empresaService.findAll()) {
				for (Usuario usuarios: usuarioService.findAll()) {
					if(usuarios.getId().equals(usuario.getId())) {
						empresa.getMercadorias().add(nova_mercadoria);
						empresaService.create(empresa);
					}
				}
			}
			
			usuario.getMercadorias().add(nova_mercadoria);
			usuarioService.create(usuario);
			status = HttpStatus.CREATED;
			ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(status);
			return resposta;
		} else {
			status = HttpStatus.BAD_REQUEST;
		}
		
		ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(status);
		return resposta;
	}
	
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarMercadoria(@RequestBody Mercadoria mercadoria, @PathVariable Long id) {
        HttpStatus status = HttpStatus.CONFLICT;
        Mercadoria Mercadoria = mercadoriaService.findById(id);
        if (Mercadoria != null) {
        	Mercadoria.setId(id);
        	mercadoriaService.update(Mercadoria);
        	mercadoriaService.create(mercadoria);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(status);
        return resposta;
    }
    
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Mercadoria mercadoriaSelecionada = mercadoriaService.findById(id);
		if(mercadoriaSelecionada != null) {
		    List<Usuario> usuarios = usuarioService.findAll();
		    List<Empresa> empresas = empresaService.findAll();
		    List<Venda> vendas = vendaService.findAll();
		    
		    for(Usuario usuario: usuarios) {
		    	for(Mercadoria mercadoria: usuario.getMercadorias()) {
		    		if(mercadoria.getId() == id) {
		    			usuario.getMercadorias().remove(mercadoria);
						usuarioService.create(usuario);
		    		}
		    	}
		    }
		    
		    for(Empresa empresa: empresas) {
		    	for(Mercadoria mercadoria: empresa.getMercadorias()) {
		    		if(mercadoria.getId() == id) {
		    			empresa.getMercadorias().remove(mercadoria);
		    			empresaService.create(empresa);
		    		}
		    	}
		    }

		    for(Venda venda: vendas) {
		    	for(Mercadoria mercadoria: venda.getMercadorias()) {
		    		if(mercadoria.getId() == id) {
		    			venda.getMercadorias().remove(mercadoria);
		    			vendaService.create(venda);
		    		}
		    	}
		    }

		    mercadoriaService.delete(mercadoriaSelecionada);
			status = HttpStatus.OK;
		}
		status = HttpStatus.NOT_FOUND;
		ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(status);
		return resposta;
	}
}
