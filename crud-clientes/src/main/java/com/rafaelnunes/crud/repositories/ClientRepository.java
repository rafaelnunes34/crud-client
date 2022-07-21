package com.rafaelnunes.crud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rafaelnunes.crud.entities.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
