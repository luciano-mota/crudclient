package com.lucianodeveloper.crudclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lucianodeveloper.crudclient.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
