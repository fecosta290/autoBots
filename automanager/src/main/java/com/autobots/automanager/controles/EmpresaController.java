package com.autobots.automanager.controles;

import java.util.Date;
import java.util.List;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.modelos.DocumentoAtualizador;
import com.autobots.automanager.service.EmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
	
	@Autowired
	private EmpresaService empresaService;
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
	@GetMapping("/empresas")
	public ResponseEntity<List<Empresa>> ObterEmpresas(){
		HttpStatus status = HttpStatus.CONFLICT;
        List<Empresa> empresas = empresaService.findAll();
        if (empresas.isEmpty()) {
           	status = HttpStatus.NOT_FOUND;
            ResponseEntity<List<Empresa>> resposta = new ResponseEntity<>(status);
            return resposta;
        } else {
        	status = HttpStatus.FOUND;
        	empresaService.adicionarLink(empresas);
            ResponseEntity<List<Empresa>> resposta = new ResponseEntity<List<Empresa>>(empresas, status);
            return resposta;
        }
	}
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
	@GetMapping("/empresa/{id}")
	public ResponseEntity<Empresa> ObterEmpresa(@PathVariable Long id){
        Empresa empresa = empresaService.findById(id);
        if (empresa == null) {
            ResponseEntity<Empresa> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
        	empresaService.adicionarLink(empresa);
            ResponseEntity<Empresa> resposta = new ResponseEntity<Empresa>(empresa, HttpStatus.FOUND);
            return resposta;
        }
	}
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @PostMapping("/empresa_enviar")
    public ResponseEntity<?> CadastrarEmpresa(@RequestBody Empresa empresa) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (empresa.getId() != null) {
        	empresa.setCadastro(new Date());
        	empresaService.create(empresa);
            status = HttpStatus.CREATED;
        } else {
        	status = HttpStatus.BAD_REQUEST;
        }
        ResponseEntity<Empresa> resposta = new ResponseEntity<>(status);
        return resposta;
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> AtualizarEmpresa(@RequestBody Empresa empresa, @PathVariable Long id) {
        HttpStatus status = HttpStatus.CONFLICT;
        Empresa Empresa = empresaService.findById(id);
        if (Empresa != null) {
        	Empresa.setId(id);
    		empresaService.update(Empresa);
    		empresaService.create(empresa);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.NOT_FOUND;
        }
        ResponseEntity<Empresa> resposta = new ResponseEntity<>(status);
        return resposta;
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN')")
	@DeleteMapping("/deletar/{id}")
	public ResponseEntity<?> deletar(@PathVariable Long id){
        HttpStatus status = HttpStatus.BAD_REQUEST;
		Empresa empresa = empresaService.findById(id);
		if(empresa != null) {
			empresaService.delete(empresa);
			status = HttpStatus.OK;
		} else {
			status = HttpStatus.NOT_FOUND;	
		}
		ResponseEntity<Empresa> resposta = new ResponseEntity<>(status);
		return resposta;
	}
}