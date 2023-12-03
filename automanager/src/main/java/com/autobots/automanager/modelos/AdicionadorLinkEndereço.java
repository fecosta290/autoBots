package com.autobots.automanager.modelos;

import com.autobots.automanager.controles.EndereçoController;
import com.autobots.automanager.entidades.Endereco;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdicionadorLinkEndereço implements AdicionadorLink<Endereco> {
    @Override
    public void adicionarLink(List<Endereco> lista) {
        for (Endereco endereco : lista) {
            long id = endereco.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(EndereçoController.class)
                            .ObterEndereco(id))
                    .withSelfRel();
            endereco.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Endereco objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(EndereçoController.class)
                        .ObterEnderecos())
                .withRel("enderecos");
        objeto.add(linkProprio);
    }
}