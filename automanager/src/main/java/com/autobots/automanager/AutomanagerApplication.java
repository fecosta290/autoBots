package com.autobots.automanager;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.autobots.automanager.entidades.Cliente;
import com.autobots.automanager.entidades.Credencial;
import com.autobots.automanager.entidades.CredencialUsuarioSenha;
import com.autobots.automanager.entidades.Documento;
import com.autobots.automanager.entidades.Email;
import com.autobots.automanager.entidades.Empresa;
import com.autobots.automanager.entidades.Endereco;
import com.autobots.automanager.entidades.Mercadoria;
import com.autobots.automanager.entidades.Servico;
import com.autobots.automanager.entidades.Telefone;
import com.autobots.automanager.entidades.Usuario;
import com.autobots.automanager.entidades.Veiculo;
import com.autobots.automanager.enumeracoes.*;
import com.autobots.automanager.repositorios.*;

@SpringBootApplication
public class AutomanagerApplication implements CommandLineRunner {

	@Autowired
	private UsuarioRepositorio repositorio;

	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}
	
	@Component
	public static class Runner implements ApplicationRunner {
		@Autowired
		public ClienteRepositorio repositorio;
		
		@Autowired
		public EmpresaRepositorio empresaRepositorio;

		@Override
		public void run(ApplicationArguments args) throws Exception {
			Calendar calendario = Calendar.getInstance();
			calendario.set(2002, 05, 15);

			Cliente cliente = new Cliente();
			cliente.setNome("Pedro Alcântara de Bragança e Bourbon");
			cliente.setDataCadastro(Calendar.getInstance().getTime());
			cliente.setDataNascimento(calendario.getTime());
			cliente.setNomeSocial("Dom Pedro");
			
			Telefone telefone = new Telefone();
			telefone.setDdd("21");
			telefone.setNumero("981234576");
			cliente.getTelefones().add(telefone);
			
			Endereco endereco = new Endereco();
			endereco.setEstado("Rio de Janeiro");
			endereco.setCidade("Rio de Janeiro");
			endereco.setBairro("Copacabana");
			endereco.setRua("Avenida Atlântica");
			endereco.setNumero("1702");
			endereco.setCodigoPostal("22021001");
			endereco.setInformacoesAdicionais("Hotel Copacabana palace");
			cliente.setEndereco(endereco);
			
			Documento rg = new Documento();
			rg.setDataEmissao(new Date());
			rg.setTipo(TipoDocumento.RG);
			rg.setNumero("1500");
			
			Documento cpf = new Documento();
			cpf.setDataEmissao(new Date());
			cpf.setTipo(TipoDocumento.CPF);
			cpf.setNumero("00000000001");
			
			cliente.getDocumentos().add(rg);
			cliente.getDocumentos().add(cpf);
			
			repositorio.save(cliente);
			
			Empresa empresa = new Empresa();
			empresa.setRazaoSocial("Car service toyota ltda");
			empresa.setNomeFantasia("Car service manutenção veicular");
			empresa.setCadastro(new Date());

			Endereco enderecoEmpresa = new Endereco();
			enderecoEmpresa.setEstado("São Paulo");
			enderecoEmpresa.setCidade("São Paulo");
			enderecoEmpresa.setBairro("Centro");
			enderecoEmpresa.setRua("Av. São João");
			enderecoEmpresa.setNumero("00");
			enderecoEmpresa.setCodigoPostal("01035-000");

			empresa.setEndereco(enderecoEmpresa);

			Telefone telefoneEmpresa = new Telefone();
			telefoneEmpresa.setDdd("011");
			telefoneEmpresa.setNumero("986454527");

			empresa.getTelefones().add(telefoneEmpresa);

			Mercadoria rodaLigaLeve = new Mercadoria();
			rodaLigaLeve.setCadastro(new Date());
			rodaLigaLeve.setFabricao(new Date());
			rodaLigaLeve.setNome("Roda de liga leva modelo toyota etios");
			rodaLigaLeve.setValidade(new Date());
			rodaLigaLeve.setQuantidade(30);
			rodaLigaLeve.setValor(300.0);
			rodaLigaLeve.setDescricao("Roda de liga leve original de fábrica da toyta para modelos do tipo hatch");

			empresa.getMercadorias().add(rodaLigaLeve);

			Veiculo veiculo = new Veiculo();
			veiculo.setPlaca("ABC-0000");
			veiculo.setModelo("corolla-cross");
			veiculo.setTipo(TipoVeiculo.SUV);


			Servico trocaRodas = new Servico();
			trocaRodas.setDescricao("Troca das rodas do carro por novas");
			trocaRodas.setNome("Troca de rodas");
			trocaRodas.setValor(50);

			Servico alinhamento = new Servico();
			alinhamento.setDescricao("Alinhamento das rodas do carro");
			alinhamento.setNome("Alinhamento de rodas");
			alinhamento.setValor(50);

			empresa.getServicos().add(trocaRodas);
			empresa.getServicos().add(alinhamento);

			empresaRepositorio.save(empresa);
			
			Mercadoria rodaLigaLeve2 = new Mercadoria();
			rodaLigaLeve2.setCadastro(new Date());
			rodaLigaLeve2.setFabricao(new Date());
			rodaLigaLeve2.setNome("Roda de liga leva modelo toyota etios");
			rodaLigaLeve2.setValidade(new Date());
			rodaLigaLeve2.setQuantidade(30);
			rodaLigaLeve2.setValor(300.0);
			rodaLigaLeve2.setDescricao("Roda de liga leve original de fábrica da toyta para modelos do tipo hatch");

			Servico alinhamento2 = new Servico();
			alinhamento2.setDescricao("Alinhamento das rodas do carro");
			alinhamento2.setNome("Alinhamento de rodas");
			alinhamento2.setValor(50);

			Servico balanceamento = new Servico();
			balanceamento.setDescricao("balanceamento das rodas do carro");
			balanceamento.setNome("balanceamento de rodas");
			balanceamento.setValor(30);
			
			empresaRepositorio.save(empresa);
		}
	}

	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder codificador = new BCryptPasswordEncoder();
		Usuario usuario = new Usuario();
		usuario.setNome("administrador");
		usuario.getPerfis().add(Perfil.ROLE_ADMIN);
		Credencial credencial = new Credencial();
		credencial.setNomeUsuario("admin");
		String senha  = "123456";
		credencial.setSenha(codificador.encode(senha));
		usuario.setCredencial(credencial);
		repositorio.save(usuario);
	}
}