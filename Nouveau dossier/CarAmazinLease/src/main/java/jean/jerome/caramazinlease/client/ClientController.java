package jean.jerome.caramazinlease.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jean.jerome.caramazinlease.contract.Contract;

@RestController
@RequestMapping("/api/v1/clients")
@CrossOrigin(origins = "http://localhost:3000")
public class ClientController {
	@Autowired
	ClientService clientService;
	
	@GetMapping("/")
	public ResponseEntity<List<Client>> getAllClient(){
		List<Client> clients = clientService.getAll();
		return new ResponseEntity<>(clients, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Client> getById(@PathVariable("id") long id){
		Client client = clientService.getById(id);
		System.out.println(client);
		if(client != null) 
			return new ResponseEntity<>(client, HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@PostMapping
	public ResponseEntity<Client> createClient(@RequestBody Client client){
		try {
			Client savedClient = clientService.save(client);
			return new ResponseEntity<Client>(savedClient, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Client> deleteClient(@PathVariable("id") long id){
		try {
			Client deletedClient = clientService.deleteById(id);
			return new ResponseEntity<>(deletedClient, HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping
	public ResponseEntity<Client> deleteAll(){
		try {
			clientService.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/tutorials")
	  public ResponseEntity<List<Client>> getAllClient(@RequestParam(required = false) String fname,
			                                           @RequestParam(required = false) String lname) {
	    try {
	      List<Client> clients = clientService.getByFnameAndLname(fname, lname);

	      if (clients.isEmpty()) {
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	      }

	      return new ResponseEntity<>(clients, HttpStatus.OK);
	    } catch (Exception e) {
	      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	  }
	
	@PutMapping("/{id}")
	  public ResponseEntity<Client> updateClient(@PathVariable("id") long id, @RequestBody Client client) {
	    Client existingClient= clientService.getById(id);

	    if (existingClient != null) {
	      Client updatedClient = new Client();
	      updatedClient.setId(client.getId());
	      updatedClient.setFname(client.getFname());
	      updatedClient.setLname(client.getLname());
	      updatedClient.setAddress(client.getAddress());
	      updatedClient.setDob(client.getDob());
	      updatedClient.setFidelity(client.getFidelity());
	      updatedClient.setContracts(client.getContracts());
	      return new ResponseEntity<>(clientService.save(updatedClient), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	

	}
}
