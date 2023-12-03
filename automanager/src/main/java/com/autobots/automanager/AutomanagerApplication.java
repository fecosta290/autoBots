package com.autobots.automanager;

import java.util.Calendar;
import java.util.Date;

import com.autobots.automanager.entidades.*;
import com.autobots.automanager.enumeracoes.*;
import com.autobots.automanager.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class AutomanagerApplication{



	public static void main(String[] args) {
		SpringApplication.run(AutomanagerApplication.class, args);
	}


	@Component
	public static class Runner implements ApplicationRunner {

		@Autowired
		private EmpresaRepositorio repositorioEmpresa;

		@Autowired
		public ClienteRepositorio repositorio;

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

			Usuario funcionario = new Usuario();
			funcionario.setNome("Pedro Alcântara de Bragança e Bourbon");
			funcionario.setNomeSocial("Dom Pedro");
			funcionario.getPerfis().add(PerfilUsuario.Funcionario);

			Email emailFuncionario = new Email();
			emailFuncionario.setEndereco("a@a.com");

			funcionario.getEmails().add(emailFuncionario);

			Endereco enderecoFuncionario = new Endereco();
			enderecoFuncionario.setEstado("São Paulo");
			enderecoFuncionario.setCidade("São Paulo");
			enderecoFuncionario.setBairro("Jardins");
			enderecoFuncionario.setRua("Av. São Gabriel");
			enderecoFuncionario.setNumero("00");
			enderecoFuncionario.setCodigoPostal("01435-001");

			funcionario.setEndereco(enderecoFuncionario);

			empresa.getUsuarios().add(funcionario);

			Telefone telefoneFuncionario = new Telefone();
			telefoneFuncionario.setDdd("011");
			telefoneFuncionario.setNumero("9854633728");

			funcionario.getTelefones().add(telefoneFuncionario);

			Documento cpf2 = new Documento();
			cpf2.setDataEmissao(new Date());
			cpf2.setNumero("856473819229");
			cpf2.setTipo(TipoDocumento.CPF);

			funcionario.getDocumentos().add(cpf2);

			CredencialUsuario credencialFuncionario = new CredencialUsuario();
			credencialFuncionario.setInativo(false);
			credencialFuncionario.setNomeUsuario("dompedrofuncionario");
			credencialFuncionario.setSenha("123456");
			credencialFuncionario.setCriacao(new Date());
			credencialFuncionario.setUltimoAcesso(new Date());

			funcionario.getCredenciais().add(credencialFuncionario);

			Usuario fornecedor = new Usuario();
			fornecedor.setNome("Componentes varejo de partes automotivas ltda");
			fornecedor.setNomeSocial("Loja do carro, vendas de componentes automotivos");
			fornecedor.getPerfis().add(PerfilUsuario.Fornecedor);

			Email emailFornecedor = new Email();
			emailFornecedor.setEndereco("f@f.com");

			fornecedor.getEmails().add(emailFornecedor);

			CredencialUsuario credencialFornecedor = new CredencialUsuario();
			credencialFornecedor.setInativo(false);
			credencialFornecedor.setNomeUsuario("dompedrofornecedor");
			credencialFornecedor.setSenha("123456");
			credencialFornecedor.setCriacao(new Date());
			credencialFornecedor.setUltimoAcesso(new Date());

			fornecedor.getCredenciais().add(credencialFornecedor);

			Documento cnpj = new Documento();
			cnpj.setDataEmissao(new Date());
			cnpj.setNumero("00014556000100");
			cnpj.setTipo(TipoDocumento.CNPJ);

			fornecedor.getDocumentos().add(cnpj);

			Endereco enderecoFornecedor = new Endereco();
			enderecoFornecedor.setEstado("Rio de Janeiro");
			enderecoFornecedor.setCidade("Rio de Janeiro");
			enderecoFornecedor.setBairro("Centro");
			enderecoFornecedor.setRua("Av. República do chile");
			enderecoFornecedor.setNumero("00");
			enderecoFornecedor.setCodigoPostal("20031-170");

			fornecedor.setEndereco(enderecoFornecedor);

			empresa.getUsuarios().add(fornecedor);

			Mercadoria rodaLigaLeve = new Mercadoria();
			rodaLigaLeve.setCadastro(new Date());
			rodaLigaLeve.setFabricao(new Date());
			rodaLigaLeve.setNome("Roda de liga leva modelo toyota etios");
			rodaLigaLeve.setValidade(new Date());
			rodaLigaLeve.setQuantidade(30);
			rodaLigaLeve.setValor(300.0);
			rodaLigaLeve.setDescricao("Roda de liga leve original de fábrica da toyta para modelos do tipo hatch");

			empresa.getMercadorias().add(rodaLigaLeve);

			fornecedor.getMercadorias().add(rodaLigaLeve);

			Usuario cliente2 = new Usuario();
			cliente2.setNome("Pedro Alcântara de Bragança e Bourbon");
			cliente2.setNomeSocial("Dom pedro cliente");
			cliente2.getPerfis().add(PerfilUsuario.Cliente);

			Email emailCliente = new Email();
			emailCliente.setEndereco("c@c.com");

			cliente2.getEmails().add(emailCliente);

			Documento cpfCliente = new Documento();
			cpfCliente.setDataEmissao(new Date());
			cpfCliente.setNumero("12584698533");
			cpfCliente.setTipo(TipoDocumento.CPF);

			cliente2.getDocumentos().add(cpfCliente);

			CredencialUsuario credencialCliente = new CredencialUsuario();
			credencialCliente.setInativo(false);
			credencialCliente.setNomeUsuario("dompedrocliente");
			credencialCliente.setSenha("123456");
			credencialCliente.setCriacao(new Date());
			credencialCliente.setUltimoAcesso(new Date());

			cliente2.getCredenciais().add(credencialCliente);

			Endereco enderecoCliente = new Endereco();
			enderecoCliente.setEstado("São Paulo");
			enderecoCliente.setCidade("São José dos Campos");
			enderecoCliente.setBairro("Centro");
			enderecoCliente.setRua("Av. Dr. Nelson D'Ávila");
			enderecoCliente.setNumero("00");
			enderecoCliente.setCodigoPostal("12245-070");

			cliente2.setEndereco(enderecoCliente);

			Veiculo veiculo = new Veiculo();
			veiculo.setPlaca("ABC-0000");
			veiculo.setModelo("corolla-cross");
			veiculo.setTipo(TipoVeiculo.SUV);
			veiculo.setProprietario(cliente2);

			cliente2.getVeiculos().add(veiculo);

			empresa.getUsuarios().add(cliente2);

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

			Venda venda = new Venda();
			venda.setCadastro(new Date());
			venda.setCliente(cliente2);
			venda.getMercadorias().add(rodaLigaLeve);
			venda.setIdentificacao("1234698745");
			venda.setFuncionario(funcionario);
			venda.getServicos().add(trocaRodas);
			venda.getServicos().add(alinhamento);
			venda.setVeiculo(veiculo);
			veiculo.getVendas().add(venda);

			empresa.getVendas().add(venda);

			repositorioEmpresa.save(empresa);

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

			Venda venda2 = new Venda();
			venda2.setCadastro(new Date());
			venda2.setCliente(cliente2);
			venda2.getMercadorias().add(rodaLigaLeve2);
			venda2.setIdentificacao("1234698749");
			venda2.setFuncionario(funcionario);
			venda2.getServicos().add(balanceamento);
			venda2.getServicos().add(alinhamento2);
			venda2.setVeiculo(veiculo);
			veiculo.getVendas().add(venda2);

			empresa.getVendas().add(venda2);

			repositorioEmpresa.save(empresa);

		}
	}

}
