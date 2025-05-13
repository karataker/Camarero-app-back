package com.es.tfm.ms_camarero_usuarios.repository;

import com.es.tfm.ms_camarero_usuarios.model.Rol;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolRepository extends JpaRepository<Rol, Integer> {
}