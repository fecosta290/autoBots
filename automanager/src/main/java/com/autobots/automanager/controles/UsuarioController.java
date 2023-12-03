package com.autobots.automanager.controles;


import com.autobots.automanager.entidades.*;
import com.autobots.automanager.service.EmpresaService;
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
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired
	private VendaService vendaService;
	
	@Autowired
	private VeiculoService veiculoService;
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN')")	
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> ObterUsuarios(){
		HttpStatus status = HttpStatus.CONFLICT;
        List<Usuario> usuario = usuarioService.findAll();
        if (usuario.isEmpty()) {
           	status = HttpStatus.NOT_FOUND;
            ResponseEntity<List<Usuario>> resposta = new ResponseEntity<>(status);
            return resposta;
        } else {
        	status = HttpStatus.FOUND;
        	usuarioService.adicionarLink(usuario);
            ResponseEntity<List<Usuario>> resposta = new ResponseEntity<List<Usuario>>(usuario, status);
            return resposta;
        }
	}
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN')")	
	@GetMapping("/usuario/{id}")
	public ResponseEntity<Usuario> ObterUsuario(@PathVariable long id){
		Usuario usuario = usuarioService.findById(id);
        if (usuario == null) {
            ResponseEntity<Usuario> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
        	usuarioService.adicionarLink(usuario);
            ResponseEntity<Usuario> resposta = new ResponseEntity<Usuario>(usuario, HttpStatus.FOUND);
            return resposta;
        }
	}
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN')")
    @PostMapping("/usuario_enviar/{id}")
    public ResponseEntity<?> CadastrarUsuario(@RequestBody Usuario usuario, @PathVariable Long id) {
        HttpStatus status = HttpStatus.CONFLICT;
        Empresa Empresa = empresaService.findById(id);
        if (Empresa != null) {
	        if(usuario.getPerfis().toString().contains("Fornecedor")) {
	        	if(usuario.getMercadorias().size() > 0)
	        		Empresa.getMercadorias().addAll(usuario.getMercadorias());
	        }	
	        
	        usuarioService.create(usuario);
	        Empresa.getUsuarios().add(usuario);
	        empresaService.create(Empresa);

            status = HttpStatus.CREATED;
        } else {
        	status = HttpStatus.NOT_FOUND;
        }
        ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(status);
        return resposta;
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> atualizarUsuario(@PathVariable Long id, @RequestBody CredencialUsuarioSenha credencialUsuario) {
        HttpStatus status = HttpStatus.CONFLICT;
        Usuario usuario = usuarioService.findById(id);
        if (usuario != null) {
    		usuario.getCredenciais().add(credencialUsuario);
    		usuarioService.create(usuario);
            status = HttpStatus.CREATED;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(status);
        return resposta;
    }
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletarr(@PathVariable Long id){
		HttpStatus status = HttpStatus.BAD_REQUEST;
		Usuario usuario_escolhido = usuarioService.findById(id);
		if (usuario_escolhido == null) {
			status = HttpStatus.NOT_FOUND;
			ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(status);
			return resposta;
		}
		
		for (Empresa empresa: empresaService.findAll()) {
			for(Usuario funcionario: empresa.getUsuarios()) {
				if(funcionario.getId() == usuario_escolhido.getId()) {
					empresa.getUsuarios().remove(funcionario);
				}
			}
		}
		
		for(Venda venda: vendaService.findAll()) {
			if(venda.getFuncionario().getId() == usuario_escolhido.getId()) {
				 venda.setFuncionario(null);
			}
			if(venda.getCliente().getId() == usuario_escolhido.getId()) {
				 venda.setCliente(null);
			}
		}
		
		for (Veiculo veiculo: veiculoService.findAll()) {
			if(veiculo.getProprietario().getId() == usuario_escolhido.getId()) {
				veiculo.setProprietario(null);
			}
		}
		
		usuario_escolhido.getDocumentos().removeAll(usuario_escolhido.getDocumentos());
		usuario_escolhido.getTelefones().removeAll(usuario_escolhido.getTelefones());
		usuario_escolhido.getEmails().removeAll(usuario_escolhido.getEmails());
		usuario_escolhido.getCredenciais().removeAll(usuario_escolhido.getCredenciais());
		usuario_escolhido.getMercadorias().removeAll(usuario_escolhido.getMercadorias());
		usuario_escolhido.getVeiculos().removeAll(usuario_escolhido.getVeiculos());
		usuario_escolhido.getVendas().removeAll(usuario_escolhido.getVendas());
		usuario_escolhido.setEndereco(null);
		
		usuarioService.delete(usuario_escolhido);
		
		status = HttpStatus.OK;
		ResponseEntity<Mercadoria> resposta = new ResponseEntity<>(status);
		return resposta;
			
		}

}
