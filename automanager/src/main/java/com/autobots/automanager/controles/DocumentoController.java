package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.repositorios.ClienteRepositorio;
import com.autobots.automanager.repositorios.DocumentoRepositorio;
import com.autobots.automanager.modelo.DocumentoAtualizador;
import com.autobots.automanager.modelo.DocumentoSelecionar;

@RestController
@RequestMapping("/documento")
public class DocumentoController {
	
	@Autowired
	private DocumentoRepositorio repositorio;
	
	@Autowired
	private DocumentoSelecionar selecionar;
	
	@GetMapping("/documentos")
	public List<Documento> ObterDocumentos(){
		List<Documento> documentos = repositorio.findAll();
		return documentos;
	}
	
	@GetMapping("/documento/{id}")
	public Documento ObterDocumento(@PathVariable long id){
        List<Documento> documento = repositorio.findAll();
        return selecionar.selecionar(documento, id);
	}
	
    @PostMapping("/documento_enviar")
    public void CadastrarDocumento(@RequestBody Documento documento) {
        repositorio.save(documento);
    }

    @PutMapping("/atualizar")
    public void AtualizarDocumento(@RequestBody Documento atualizacao) {
        Documento documento = repositorio.getById(atualizacao.getId());
        DocumentoAtualizador atualizador = new DocumentoAtualizador();
        atualizador.atualizar(documento, atualizacao);
        repositorio.save(documento);
    }

    @DeleteMapping("/excluir")
    public void ExcluirDocumento(@RequestBody Documento exclusao) {
        Documento documento = repositorio.getById(exclusao.getId());
        repositorio.delete(documento);
    }
}