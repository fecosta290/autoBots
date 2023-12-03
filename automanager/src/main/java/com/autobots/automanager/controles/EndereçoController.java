package com.autobots.automanager.controles;

import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.modelo.EnderecoAtualizador;
import com.autobots.automanager.modelo.EndereçoSelecionar;
import com.autobots.automanager.repositorios.EndereçoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/endereco")
public class EndereçoController{
    @Autowired
    private EndereçoRepositorio repositorio;

    @Autowired
    private EndereçoSelecionar selecionar;

    @GetMapping("/enderecos")
    public List<Endereco> ObterEnderecos(){
        List<Endereco> enderecos = repositorio.findAll();
        return enderecos;
    }

    @GetMapping("/endereco/{id}")
    public Endereco ObterEndereco(@PathVariable Long id){
        List<Endereco> endereco = repositorio.findAll();
        return selecionar.selecionar(endereco, id);
    }

    @PostMapping("/endereco_enviar")
    public void CadastrarEndereco(@RequestBody Endereco endereco) {
        repositorio.save(endereco);
    }

    @PutMapping("/atualizar")
    public void AtualizarEndereco(@RequestBody Endereco atualizacao) {
        Endereco endereco = repositorio.getById(atualizacao.getId());
        EnderecoAtualizador atualizador = new EnderecoAtualizador();
        atualizador.atualizar(endereco, atualizacao);
        repositorio.save(endereco);
    }

    @DeleteMapping("/excluir")
    public void ExcluirEndereco(@RequestBody Endereco exclusao) {
        Endereco endereco = repositorio.getById(exclusao.getId());
        repositorio.delete(endereco);
    }
}