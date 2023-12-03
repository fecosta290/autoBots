package com.autobots.automanager.controles;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.autobots.automanager.service.DocumentoService;
import com.autobots.automanager.modelos.DocumentoAtualizador;

@RestController
@RequestMapping("/documento")
public class DocumentoController {
	
	@Autowired
	private DocumentoRepositorio repositorio;
	
	@Autowired
	private DocumentoService service ;
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
	@GetMapping("/documentos")
	public ResponseEntity<List<Documento>> ObterDocumentos(){
        List<Documento> documentos = repositorio.findAll();
        if (documentos.isEmpty()) {
            ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
        	service.adicionarLink(documentos);
            ResponseEntity<List<Documento>> resposta = new ResponseEntity<>(documentos, HttpStatus.FOUND);
            return resposta;
        }
	}
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
	@GetMapping("/documento/{id}")
	public ResponseEntity<Documento> ObterDocumento(@PathVariable Long id){
        List<Documento> documentos = repositorio.findAll();
        Documento documento =  service.selecionar(documentos, id);
        if (documento == null) {
            ResponseEntity<Documento> resposta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return resposta;
        } else {
        	service.adicionarLink(documento);
            ResponseEntity<Documento> resposta = new ResponseEntity<Documento>(documento, HttpStatus.FOUND);
            return resposta;
        }
	}
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @PostMapping("/documento_enviar")
    public ResponseEntity<?> CadastrarDocumento(@RequestBody Documento documento) {
        HttpStatus status = HttpStatus.CONFLICT;
        if (documento.getId() == null) {
            repositorio.save(documento);
            status = HttpStatus.CREATED;
        }
        return new ResponseEntity<>(status);
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN') or hasAnyAuthority('ROLE_VENDEDOR')")
    @PutMapping("/atualizar/{id}")
    public ResponseEntity<?> AtualizarDocumento(@RequestBody Documento atualizacao) {
        HttpStatus status = HttpStatus.CONFLICT;
        Documento documento = repositorio.getById(atualizacao.getId());
        if (documento != null) {
            DocumentoAtualizador atualizador = new DocumentoAtualizador();
            atualizador.atualizar(documento, atualizacao);
            repositorio.save(documento);
            status = HttpStatus.OK;
        } else {
            status = HttpStatus.BAD_REQUEST;
        }
        return new ResponseEntity<>(status);
    }
	@PreAuthorize("hasAnyAuthority('ROLE_GERENTE') or hasAnyAuthority('ROLE_ADMIN')")
    @DeleteMapping("/excluir")
    public ResponseEntity<?> ExcluirDocumento(@RequestBody Documento exclusao) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        Documento documento = repositorio.getById(exclusao.getId());
        if (documento != null) {
            repositorio.delete(documento);
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}