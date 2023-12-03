package com.autobots.automanager.service;

import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.controles.VeiculoController;
import com.autobots.automanager.repositorios.VeiculoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import com.autobots.automanager.modelos.AdicionadorLink;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VeiculoService implements AdicionadorLink<Veiculo> {

    @Autowired
    private VeiculoRepositorio repositorio;
    
	@Override
	public void adicionarLink( List<Veiculo> lista ) {
		for (Veiculo veiculo : lista) {
			Link linkVeiculo =  WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoController.class)
							.ObterVeiculo(veiculo.getId()))
					.withSelfRel();
			veiculo.add(linkVeiculo);
			
		}
	}
	
	@Override
	public void adicionarLink( Veiculo veiculo ) {
			Link linkVeiculo =  WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(VeiculoController.class)
							.ObterVeiculos())
					.withRel("Todos Clientes");
			veiculo.add(linkVeiculo);	
	}

    public Long create(Veiculo veiculo) {
        return repositorio.save(veiculo).getId();
    }

    public List<Veiculo> findAll(){
        List<Veiculo> veiculos = repositorio.findAll();
        return veiculos;
    }

    public Veiculo findById(Long id) {
        Veiculo veiculo = repositorio.findById(id).orElse(null);
        return veiculo;
    }

    public Veiculo update(Veiculo veiculoAtualizacao) {
        Veiculo veiculoExistente = findById(veiculoAtualizacao.getId());
        veiculoAtualizacao.setId(veiculoExistente.getId());

        if (veiculoAtualizacao.getTipo() == null){
            veiculoAtualizacao.setTipo(veiculoExistente.getTipo());
        }
        if (veiculoAtualizacao.getModelo() == null){
            veiculoAtualizacao.setModelo(veiculoExistente.getModelo());
        }
        if (veiculoAtualizacao.getPlaca() == null){
            veiculoAtualizacao.setPlaca(veiculoExistente.getPlaca());
        }
        if (veiculoAtualizacao.getProprietario() == null){
            veiculoAtualizacao.setProprietario(veiculoExistente.getProprietario());
        }
        if (veiculoAtualizacao.getVendas() == null){
            veiculoAtualizacao.setVendas(veiculoExistente.getVendas());
        }
        return repositorio.save(veiculoAtualizacao);
    }

    public void delete(Veiculo veiculo) {
        repositorio.delete(veiculo);
    }
}
