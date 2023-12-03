package com.autobots.automanager.service;

import com.autobots.automanager.controles.ServicoController;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.modelos.AdicionadorLink;
import com.autobots.automanager.repositorios.ServicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoService implements AdicionadorLink<Servico>{
	
    @Autowired
    private ServicoRepositorio repositorio;
    
	@Override
	public void adicionarLink( List<Servico> lista ) {
		for (Servico servico : lista) {
			Link linkServico =  WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoController.class)
							.ObterServico(servico.getId()))
					.withSelfRel();
			servico.add(linkServico);
			
		}
	}
	
	@Override
	public void adicionarLink( Servico servico ) {
			Link linkServico =  WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(ServicoController.class)
							.ObterServicos())
					.withRel("Todos Serviços");
			servico.add(linkServico);	
	}

    public Long create(Servico servico) {
        return repositorio.save(servico).getId();
    }

    public List<Servico> findAll(){
        List<Servico> servicos = repositorio.findAll();
        return servicos;
    }

    public Servico findById(Long id) {
        Servico servico = repositorio.findById(id).orElse(null);
        return servico;
    }

    public Servico update(Servico servicoAtualizacao) {
        Servico servicoExistente = findById(servicoAtualizacao.getId());
        servicoAtualizacao.setId(servicoExistente.getId());

        if (servicoAtualizacao.getNome() == null){
            servicoAtualizacao.setNome(servicoExistente.getNome());
        }
        if (servicoAtualizacao.getDescricao() == null){
            servicoAtualizacao.setDescricao(servicoExistente.getDescricao());
        }
        return repositorio.save(servicoAtualizacao);
    }

    public void delete(Servico servico) {
        repositorio.delete(servico);
    }

}
