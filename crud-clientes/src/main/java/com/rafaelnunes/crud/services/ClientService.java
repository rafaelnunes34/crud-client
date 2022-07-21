package com.rafaelnunes.crud.services;

import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	public Page<ClientDTO> findAllPaged(PageRequest request) {
		Page<Client> list = repository.findAll(request);
		return list.map(client -> new ClientDTO(client));
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
		copyDtoToEntity(client, clientDto);
		client = repository.save(client);
		
		return new ClientDTO(client);
	}
	
	@Transactional
	public ClientDTO update(Long id, ClientDTO clientDto) {
		try {
			Client client = repository.getReferenceById(id);
			copyDtoToEntity(client, clientDto);
			
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
	
	private void copyDtoToEntity(Client client, ClientDTO clientDto) {
		client.setName(clientDto.getName());
		client.setCpf(clientDto.getCpf());
		client.setIncome(clientDto.getIncome());
		client.setBirthDate(clientDto.getBirthDate());
		client.setChildren(clientDto.getChildren());
	}
}
