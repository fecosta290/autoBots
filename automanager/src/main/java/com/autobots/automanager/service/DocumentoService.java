package com.autobots.automanager.service;

import com.autobots.automanager.controles.DocumentoController;
import com.autobots.automanager.entidades.Documento;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.stereotype.Component;
import com.autobots.automanager.modelos.AdicionadorLink;

import java.util.List;

@Component
public class DocumentoService implements AdicionadorLink<Documento> {
    @Override
    public void adicionarLink(List<Documento> lista) {
        for (Documento documento : lista) {
            long id = documento.getId();
            Link linkProprio = WebMvcLinkBuilder
                    .linkTo(WebMvcLinkBuilder
                            .methodOn(DocumentoController.class)
                            .ObterDocumento(id))
                    .withSelfRel();
            documento.add(linkProprio);
        }
    }

    @Override
    public void adicionarLink(Documento objeto) {
        Link linkProprio = WebMvcLinkBuilder
                .linkTo(WebMvcLinkBuilder
                        .methodOn(DocumentoController.class)
                        .ObterDocumentos())
                .withRel("documentos");
        objeto.add(linkProprio);
    }
    
    public Documento selecionar(List<Documento> documentos, long id) {
        Documento selecionado = null;
        for (Documento documento : documentos) {
            if (documento.getId() == id) {
                selecionado = documento;
            }
        }
        return selecionado;
    }
}