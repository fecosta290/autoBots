package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelos.EnderecoAtualizador;
import com.autobots.automanager.repositorios.EndereçoRepositorio;
import com.autobots.automanager.service.EndereçoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EndereçoController{
    @Autowired
    private EndereçoRepositorio repositorio;
    
    @Autowired
    private EndereçoService service;
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @GetMapping("/enderecos")
    public ResponseEntity<List<Endereco>> ObterEnderecos(){
        List<Endereco> enderecos = repositorio.findAll();
        if (enderecos.isEmpty()) {
            ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
        	service.adicionarLink(enderecos);
            ResponseEntity<List<Endereco>> resposta = new ResponseEntity<>(enderecos, HttpStatus.FOUND);
            return resposta;
        }
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @GetMapping("/endereco/{id}")
    public ResponseEntity<Endereco> ObterEndereco(@PathVariable Long id){
        List<Endereco> enderecos = repositorio.findAll();
        Endereco endereco = service.selecionar(enderecos, id);
        if (endereco == null) {
            ResponseEntity<Endereco> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
        	service.adicionarLink(endereco);
            ResponseEntity<Endereco> resposta = new ResponseEntity<Endereco>(endereco, HttpStatus.FOUND);
            return resposta;
        }
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @PostMapping("/endereco_enviar")
    public ResponseEntity<?> CadastrarEndereco(@RequestBody Endereco endereco) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (endereco.getId() == null) {
            repositorio.save(endereco);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @PutMapping("/atualizar")
    public ResponseEntity<?> AtualizarEndereco(@RequestBody Endereco atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Endereco endereco = repositorio.getById(atualizacao.getId());
        if (endereco != null) {
            EnderecoAtualizador atualizador = new EnderecoAtualizador();
            atualizador.atualizar(endereco, atualizacao);
            repositorio.save(endereco);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);

    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/excluir")
    public ResponseEntity<?> ExcluirEndereco(@RequestBody Endereco exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Endereco endereco = repositorio.getById(exclusao.getId());
        if (endereco != null) {
            repositorio.delete(endereco);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}