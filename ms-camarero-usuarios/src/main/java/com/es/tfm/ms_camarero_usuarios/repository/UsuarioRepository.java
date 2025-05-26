package com.es.tfm.ms_camarero_usuarios.repository;

import com.es.tfm.ms_camarero_usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByMatricula(String matricula);
    List<Usuario> findByBarId(int barId);
}