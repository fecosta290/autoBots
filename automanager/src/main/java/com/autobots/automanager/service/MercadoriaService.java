package com.autobots.automanager.service;

import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.controles.MercadoriaController;
import com.autobots.automanager.modelos.AdicionadorLink;
import com.autobots.automanager.repositorios.MercadoriaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Service
public class MercadoriaService implements AdicionadorLink<Mercadoria>  {

    @Autowired
    private MercadoriaRepositorio repositorio;
    
	@Override
	public void adicionarLink (List<Mercadoria> lista) {
		for (Mercadoria mercadoria : lista) {
			Link linkMercadoria = WebMvcLinkBuilder
					.linkTo(WebMvcLinkBuilder
							.methodOn(MercadoriaController.class)
							.ObterMercadoria(mercadoria.getId()))
					.withSelfRel();
			mercadoria.add(linkMercadoria);
		}
	}
	
	@Override
	public void adicionarLink( Mercadoria mercadoria ) {
		Link linkMercadoria =  WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder
						.methodOn(MercadoriaController.class)
						.ObterMercadorias())
				.withRel("Todas Mercadorias");
		mercadoria.add(linkMercadoria);
}

    public Long create(Mercadoria cadastro) {
        return repositorio.save(cadastro).getId();
    }

    public List<Mercadoria> findAll(){
        List<Mercadoria> mercadorias = repositorio.findAll();
        return mercadorias;
    }

    public Mercadoria findById(Long id) {
        Mercadoria mercadoria = repositorio.findById(id).orElse(null);
        return mercadoria;
    }

    public Mercadoria update(Mercadoria mercadoriaAtualizacao) {
        Mercadoria mercadoriaExistente = findById(mercadoriaAtualizacao.getId());
        mercadoriaAtualizacao.setId(mercadoriaExistente.getId());
        mercadoriaAtualizacao.setCadastro(mercadoriaExistente.getCadastro());

        if (mercadoriaAtualizacao.getValidade() == null){
            mercadoriaAtualizacao.setValidade(mercadoriaExistente.getValidade());
        }
        if (mercadoriaAtualizacao.getFabricao() == null){
            mercadoriaAtualizacao.setFabricao(mercadoriaExistente.getFabricao());
        }
        if (mercadoriaAtualizacao.getNome() == null){
            mercadoriaAtualizacao.setNome(mercadoriaExistente.getNome());
        }
        if (mercadoriaAtualizacao.getDescricao() == null){
            mercadoriaAtualizacao.setDescricao(mercadoriaExistente.getDescricao());
        }
        return repositorio.save(mercadoriaAtualizacao);
    }

    public void delete(Mercadoria mercadoria) {
        repositorio.delete(mercadoria);
    }


}
