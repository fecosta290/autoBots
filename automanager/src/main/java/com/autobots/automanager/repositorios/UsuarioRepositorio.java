package com.autobots.automanager.repositorios;

import com.autobots.automanager.entidades.Usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {
}
