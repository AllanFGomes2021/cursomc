package com.allangomes.cursomc.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.allangomes.cursomc.domain.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

}
