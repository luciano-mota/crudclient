package com.lucianodeveloper.crudclient.cointroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lucianodeveloper.crudclient.dto.ClientDTO;
import com.lucianodeveloper.crudclient.service.ClientService;

@RestController
@RequestMapping(value = "/clients")
public class ClientController {

	@Autowired
	private ClientService clientService;

	@GetMapping
	public ResponseEntity<Page<ClientDTO>> findAll(@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "6") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "name") String orderBy) {

		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		Page<ClientDTO> list = clientService.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(list);

	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> findById(@PathVariable Long id) {
		ClientDTO clientDTO = clientService.findById(id);
		return ResponseEntity.ok().body(clientDTO);

	}

	@PostMapping
	public ResponseEntity<ClientDTO> inserDataClient(@RequestBody ClientDTO clientDTO) {
		clientDTO = clientService.insertDataInClient(clientDTO);
		return ResponseEntity.ok().body(clientDTO);
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<ClientDTO> updateDataClient(@PathVariable Long id, @RequestBody ClientDTO clientDTO)
			throws Exception {
		clientDTO = clientService.updateDataInClient(id, clientDTO);
		return ResponseEntity.ok().body(clientDTO);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) throws Exception {
		clientService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
