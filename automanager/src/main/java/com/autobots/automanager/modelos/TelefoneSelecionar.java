package com.autobots.automanager.modelos;

import com.autobots.automanager.entidades.Telefone;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TelefoneSelecionar {

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