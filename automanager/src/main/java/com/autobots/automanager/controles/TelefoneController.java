package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelos.AdicionadorLinkTelefone;
import com.autobots.automanager.modelos.TelefoneAtualizador;
import com.autobots.automanager.modelos.TelefoneSelecionar;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefone")
public class TelefoneController {

    @Autowired
    private TelefoneRepositorio repositorio;

    @Autowired
    private TelefoneSelecionar selecionar;
    
    @Autowired
    private AdicionadorLinkTelefone adicionadorLink;

    @GetMapping("/telefones")
    public ResponseEntity<List<Telefone>> ObterTelefones(){
        List<Telefone> telefones = repositorio.findAll();
        if (telefones.isEmpty()) {
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLink.adicionarLink(telefones);
            ResponseEntity<List<Telefone>> resposta = new ResponseEntity<>(telefones, HttpStatus.FOUND);
            return resposta;
        }
    }

    @GetMapping("/telefone/{id}")
    public ResponseEntity<Telefone> ObterTelefone(@PathVariable Long id){
        List<Telefone> telefones = repositorio.findAll();
        Telefone telefone = selecionar.selecionar(telefones, id);
        if (telefone == null) {
            ResponseEntity<Telefone> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
            adicionadorLink.adicionarLink(telefone);
            ResponseEntity<Telefone> resposta = new ResponseEntity<Telefone>(telefone, HttpStatus.FOUND);
            return resposta;
        }
    }

    @PostMapping("/telefone_enviar")
    public ResponseEntity<?> CadastrarTelefone(@RequestBody Telefone telefone) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (telefone.getId() == null) {
            repositorio.save(telefone);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }

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