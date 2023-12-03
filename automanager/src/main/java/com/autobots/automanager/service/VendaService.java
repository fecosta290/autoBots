package com.autobots.automanager.service;

import com.autobots.automanager.controles.VendaController;
import com.autobots.automanager.entidades.Venda;
import com.autobots.automanager.modelos.AdicionadorLink;
import com.autobots.automanager.repositorios.VendaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendaService implements AdicionadorLink<Venda> {

    @Autowired
    private VendaRepositorio repositorio;
    
	@Override
	public void adicionarLink (List<Venda> lista) {
		for (Venda vendas : lista) {
			Link linkVenda = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VendaController.class)
							.ObterVenda(vendas.getId()))
					.withSelfRel();
			vendas.add(linkVenda);
		}
	}
	
	@Override
	public void adicionarLink (Venda venda) {
		Link linkVenda = WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(VendaController.class)
						.ObterVendas())
				.withRel("Todas Vendas");
		venda.add(linkVenda);
	}

    public Long create(Venda venda) {
        return repositorio.save(venda).getId();
    }

    public List<Venda> findAll(){
        List<Venda> vendas = repositorio.findAll();
        return vendas;
    }

    public Venda findById(Long id) {
        Venda venda = repositorio.findById(id).orElse(null);
        return venda;
    }

    public Venda update(Venda vendaAtualizacao) {
        Venda vendaExistente = findById(vendaAtualizacao.getId());
        vendaAtualizacao.setId(vendaExistente.getId());
        vendaAtualizacao.setCadastro(vendaExistente.getCadastro());
        vendaAtualizacao.setIdentificacao(vendaExistente.getIdentificacao());
        vendaAtualizacao.setFuncionario(vendaExistente.getFuncionario());

        if (vendaAtualizacao.getMercadorias() == null){
            vendaAtualizacao.setMercadorias(vendaExistente.getMercadorias());
        }
        if (vendaAtualizacao.getServicos() == null){
            vendaAtualizacao.setServicos(vendaExistente.getServicos());
        }
        if (vendaAtualizacao.getVeiculo() == null){
            vendaAtualizacao.setVeiculo(vendaExistente.getVeiculo());
        }
        return repositorio.save(vendaAtualizacao);
    }

    public void delete(Venda venda) {
        repositorio.delete(venda);
    }
}
