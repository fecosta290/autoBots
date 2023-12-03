package com.autobots.automanager.entidades;

import javax.persistence.Column;
import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class CredencialUsuario extends Credencial {
	@Column(nullable = false, unique = true)
	private String nomeUsuario;
	@Column(nullable = false)
	private String senha;
}