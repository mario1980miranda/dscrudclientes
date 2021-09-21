package com.devsuperior.dscrudclientes.dscrudclientes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.dscrudclientes.dscrudclientes.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
