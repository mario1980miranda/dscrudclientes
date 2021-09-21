package com.devsuperior.dscrudclientes.dscrudclientes.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.dscrudclientes.dscrudclientes.dto.ClientDTO;
import com.devsuperior.dscrudclientes.dscrudclientes.entities.Client;
import com.devsuperior.dscrudclientes.dscrudclientes.repositories.ClientRepository;
import com.devsuperior.dscrudclientes.dscrudclientes.services.exceptions.ResourceNotFoundException;

@Service
public class ClientService {

	@Autowired
	private ClientRepository repository;
	
	@Transactional(readOnly = true)
	public Page<ClientDTO> findAllPaged(final PageRequest pageRequest) {
		final Page<Client> list = this.repository.findAll(pageRequest);
		return list.map(x -> new ClientDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ClientDTO insert(final ClientDTO dto) {
		Client entity = new Client();
		this.copyDtoToEntity(dto, entity);
		this.repository.save(entity);
		return new ClientDTO(entity);
	}

	@Transactional(readOnly = true)
	public ClientDTO findById(final Long id) {
		final Optional<Client> obj = this.repository.findById(id);
		final Client entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ClientDTO(entity);
	}
	
	@Transactional
	public ClientDTO update(final Long id, final ClientDTO dto) {
		try {
			Client entity = this.repository.getOne(id);
			this.copyDtoToEntity(dto, entity);
			entity = this.repository.save(entity);
			return new ClientDTO(entity);
		} catch (Exception e) {
			throw new ResourceNotFoundException("Id not found : " + id);
		}
	}
	
	private void copyDtoToEntity(final ClientDTO dto, Client entity) {
		entity.setName(dto.getName());
		entity.setCpf(dto.getCpf());
		entity.setIncome(dto.getIncome());
		entity.setBirthDate(dto.getBirthDate());
		entity.setChildren(dto.getChildren());
	}

	public void delete(final Long id) {
		try {
			this.repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Id not found : " + id);
		} catch (DataIntegrityViolationException e) {
			throw new ResourceNotFoundException("Integrity violation");
		}
	}
}
