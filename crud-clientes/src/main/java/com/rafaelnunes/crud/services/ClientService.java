package com.rafaelnunes.crud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rafaelnunes.crud.dto.ClientDTO;
import com.rafaelnunes.crud.entities.Client;
import com.rafaelnunes.crud.repositories.ClientRepository;
import com.rafaelnunes.crud.services.exceptions.DatabaseException;
import com.rafaelnunes.crud.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {
	
	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public List<ClientDTO> findAll() {
		List<Client> list = repository.findAll();
		return list.stream().map(client -> new ClientDTO(client)).collect(Collectors.toList());
	}
	
	@Transactional(readOnly = true)
	public ClientDTO findById(Long id) {
		Optional<Client> objClient = repository.findById(id);
		Client client = objClient.orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado. Id: " + id));
		return new ClientDTO(client);
	}
	
	@Transactional
	public ClientDTO insert(ClientDTO clientDto) {
		Client client = new Client();
		client.setName(clientDto.getName());
		client.setCpf(clientDto.getCpf());
		client.setIncome(clientDto.getIncome());
		client.setBirthDate(clientDto.getBirthDate());
		client.setChildren(clientDto.getChildren());
		
		client = repository.save(client);
		
		return new ClientDTO(client);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO clientDto) {
		try {
			Client client = repository.getReferenceById(id);
			client.setName(clientDto.getName());
			client.setCpf(clientDto.getCpf());
			client.setIncome(clientDto.getIncome());
			client.setBirthDate(clientDto.getBirthDate());
			client.setChildren(clientDto.getChildren());
			
			client = repository.save(client);
			
			return new ClientDTO(client);
		}
		catch(EntityNotFoundException e) {
			throw new ResourceNotFoundException("Cliente não encontrado. Id: " + id);
		}
	}
	
	public void delete(Long id) {
		try {
			repository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Cliente não encontrado. Id: " + id);
		}
		catch(DataIntegrityViolationException e) {
			throw new DatabaseException("Integrity violation");
		}
	}
	
}
