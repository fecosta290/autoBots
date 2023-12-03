package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.service.UsuarioService;
import com.autobots.automanager.service.VeiculoService;
import com.autobots.automanager.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/veiculo")
public class VeiculoController {
	
	@Autowired
	private VeiculoService veiculoService;
	
	@Autowired
	private VendaService vendaService;

	@Autowired
	private UsuarioService usuarioService;
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
	@GetMapping("/veiculos")
	public ResponseEntity<List<Veiculo>> ObterVeiculos(){
		HttpStatus status = HttpStatus.CONFLICT;
		List<Veiculo> veiculo = veiculoService.findAll();
        if (veiculo.isEmpty()) {
           	status = HttpStatus.NOT_FOUND;
            ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<>(status);
            return resposta;
        } else {
        	status = HttpStatus.FOUND;
        	veiculoService.adicionarLink(veiculo);
            ResponseEntity<List<Veiculo>> resposta = new ResponseEntity<List<Veiculo>>(veiculo, status);
            return resposta;
        }
	}
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
	@GetMapping("/veiculo/{id}")
	public ResponseEntity<Veiculo> ObterVeiculo(@PathVariable Long id){
		Veiculo veiculo = veiculoService.findById(id);
        if (veiculo == null) {
            ResponseEntity<Veiculo> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
        	veiculoService.adicionarLink(veiculo);
            ResponseEntity<Veiculo> resposta = new ResponseEntity<Veiculo>(veiculo, HttpStatus.FOUND);
            return resposta;
        }
	}

	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @PostMapping("/veiculo_enviar/{usuario_id}")
	public ResponseEntity<?> cadastrarVeiculo(@RequestBody Veiculo veiculo, @PathVariable Long usuario_id){
        HttpStatus status = HttpStatus.CONFLICT;
		Usuario usuario = usuarioService.findById(usuario_id);
        if (usuario != null) {
			usuario.getVeiculos().add(veiculo);
			veiculo.setProprietario(usuario);
			veiculoService.create(veiculo);
			usuarioService.create(usuario);
            status = HttpStatus.CREATED;
        } else {
        	status = HttpStatus.BAD_REQUEST;
        }
        ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(status);
        return resposta;
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @PutMapping("/atualizar/{id}")
	public ResponseEntity<?> atualizar(@RequestBody Veiculo veiculo, @PathVariable Long id){
        HttpStatus status = HttpStatus.CONFLICT;
        Veiculo Veiculo = veiculoService.findById(id);
        if (Veiculo != null) {
        	Veiculo.setId(id);
    		veiculoService.update(Veiculo);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        ResponseEntity<Veiculo> resposta = new ResponseEntity<>(status);
        return resposta;
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN')")
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
	    HttpStatus status = HttpStatus.BAD_REQUEST;
		Veiculo veiculo_escolhido = veiculoService.findById(id);
		if(veiculo_escolhido == null) {
			List<Usuario> usuarios = usuarioService.findAll();
			List<Venda> vendas = vendaService.findAll();
			
			for(Usuario usuario: usuarios) {
				for(Veiculo veiculo: usuario.getVeiculos()) {
					if(veiculo.getId() == id) {
						usuario.getVeiculos().remove(veiculo);
						usuarioService.create(usuario);
					}
				}
			}
			
			for(Venda venda: vendas) {
				if(venda.getVeiculo().getId() == id) {
					venda.setVeiculo(null);
					vendaService.create(venda);
				}
			}

			veiculoService.delete(veiculo_escolhido);			
			status = HttpStatus.OK;
			ResponseEntity<Veiculo> resposta = new ResponseEntity<>(status);
			return resposta;
		} else {
			status = HttpStatus.NOT_FOUND;
			ResponseEntity<Veiculo> resposta = new ResponseEntity<>(status);
			return resposta;
		}	

	}
}

