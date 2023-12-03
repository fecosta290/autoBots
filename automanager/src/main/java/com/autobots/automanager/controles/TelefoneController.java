package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.modelo.TelefoneAtualizador;
import com.autobots.automanager.modelo.TelefoneSelecionar;
import com.autobots.automanager.repositorios.TelefoneRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/telefone")
public class TelefoneController {

    @Autowired
    private TelefoneRepositorio repositorio;

    @Autowired
    private TelefoneSelecionar selecionar;

    @GetMapping("/telefones")
    public List<Telefone> ObterTelefones(){
        List<Telefone> telefones = repositorio.findAll();
        return telefones;
    }

    @GetMapping("/telefone/{id}")
    public Telefone ObterTelefone(@PathVariable Long id){
        List<Telefone> telefone = repositorio.findAll();
        return selecionar.selecionar(telefone, id);
    }

    @PostMapping("/telefone_enviar")
    public void CadastrarTelefone(@RequestBody Telefone telefone) {
        repositorio.save(telefone);
    }

    @PutMapping("/atualizar")
    public void AtualizarTelefone(@RequestBody Telefone atualizacao) {
        Telefone telefone = repositorio.getById(atualizacao.getId());
        TelefoneAtualizador atualizador = new TelefoneAtualizador();
        atualizador.atualizar(telefone, atualizacao);
        repositorio.save(telefone);
    }

    @DeleteMapping("/excluir")
    public void ExcluirTelefone(@RequestBody Documento exclusao) {
        Telefone telefone = repositorio.getById(exclusao.getId());
        repositorio.delete(telefone);
    }
}