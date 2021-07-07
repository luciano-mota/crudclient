package com.lucianodeveloper.crudclient.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lucianodeveloper.crudclient.dto.ClientDTO;
import com.lucianodeveloper.crudclient.entity.Client;
import com.lucianodeveloper.crudclient.repository.ClientRepository;

import javassist.NotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(PageRequest pageRequest) {
		Page<Client> list = clientRepository.findAll(pageRequest);
		return list.map(x -> new ClientDTO(x));
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> objClient = clientRepository.findById(id);
		Client entityClient = objClient.orElseThrow(() -> new RuntimeException("ID de usuário não encontrado"));
		return new ClientDTO(entityClient);

	}

	@Transactional
	public ClientDTO insertDataInClient(ClientDTO clientDTO) {
		Client clientEntity = new Client();
		insertOrUpdateInCustomerAttributes(clientDTO, clientEntity);
		clientEntity = clientRepository.save(clientEntity);
		return new ClientDTO(clientEntity);
	}

	@Transactional
	public ClientDTO updateDataInClient(Long id, ClientDTO clientDTO) throws Exception {
		try {
			Client clientEntity = clientRepository.getOne(id);
			insertOrUpdateInCustomerAttributes(clientDTO, clientEntity);
			clientEntity = clientRepository.save(clientEntity);
			return new ClientDTO(clientEntity);
		} catch (Exception e) {
			throw new NotFoundException("ID note found: " + id);
		}

	}

	public void delete(Long id) throws Exception {
		try {
			clientRepository.deleteById(id);
		} catch (Exception e) {
			throw new NotFoundException("id not found " + id);
		}

	}

	public void insertOrUpdateInCustomerAttributes(ClientDTO clientDTO, Client client) {
		client.setName(clientDTO.getName());
		client.setCpf(clientDTO.getCpf());
		client.setIncome(clientDTO.getIncome());
		client.setBirthData(clientDTO.getBirthData());
		client.setChildren(clientDTO.getChildren());
		client = clientRepository.save(client);
	}
}
