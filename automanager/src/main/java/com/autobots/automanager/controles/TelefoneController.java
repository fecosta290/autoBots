package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.TelefoneAtualizador;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import com.autobots.automanager.service.TelefoneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefone")
public class TelefoneController {

    @Autowired
    private TelefoneRepositorio repositorio;

    @Autowired
    private TelefoneService service;
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @GetMapping("/telefones")
    public ResponseEntity<List<Telefone>> ObterTelefones(){
        List<Telefone> telefones = repositorio.findAll();
        if (telefones.isEmpty()) {
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
        	service.adicionarLink(telefones);
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.FOUND);
            return resposta;
        }
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @GetMapping("/telefone/{id}")
    public ResponseEntity<Telefone> ObterTelefone(@PathVariable Long id){
        List<Telefone> telefones = repositorio.findAll();
        Telefone telefone = service.selecionar(telefones, id);
        if (telefone == null) {
            ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
        	service.adicionarLink(telefone);
            ResponseEntity<Telefone> resposta = new ResponseEntity<Telefone>(telefone, HttpStatus.FOUND);
            return resposta;
        }
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @PostMapping("/telefone_enviar")
    public ResponseEntity<?> CadastrarTelefone(@RequestBody Telefone telefone) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (telefone.getId() == null) {
            repositorio.save(telefone);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @PutMapping("/atualizar")
    public ResponseEntity<?> AtualizarTelefone(@RequestBody Telefone atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Telefone telefone = repositorio.getById(atualizacao.getId());
        if (telefone != null) {
            TelefoneAtualizador atualizador = new TelefoneAtualizador();
            atualizador.atualizar(telefone, atualizacao);
            repositorio.save(telefone);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/excluir")
    public ResponseEntity<?> ExcluirTelefone(@RequestBody Documento exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Telefone telefone = repositorio.getById(exclusao.getId());
        if (telefone != null) {
            repositorio.delete(telefone);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}