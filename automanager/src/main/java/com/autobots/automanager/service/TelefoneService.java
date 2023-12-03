package com.autobots.automanager.service;
import com.autobots.automanager.controles.TelefoneController;

import com.autobots.automanager.entidades.Telefone;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;
import com.autobots.automanager.modelos.AdicionadorLink;
import java.util.List;

@Component
public class TelefoneService implements AdicionadorLink<Telefone>{

    @Override
    public void adicionarLink(List<Telefone> lista) {
        for (Telefone telefone : lista) {
            long id = telefone.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(TelefoneController.class)
                            .ObterTelefone(id))
                    .withSelfRel();
            telefone.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Telefone objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(TelefoneController.class)
                        .ObterTelefones())
                .withRel("telefones");
        objeto.add(linkProprio);
    }
    
    public Telefone selecionar(List<Telefone> telefones, long id) {
        Telefone selecionado = null;
        for (Telefone telefone : telefones) {
            if (telefone.getId() == id) {
                selecionado = telefone;
            }
        }
        return selecionado;
    }

}