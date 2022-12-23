package jean.jerome.caramazinlease.contract;

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
import org.springframework.web.bind.annotation.RestController;

import jean.jerome.caramazinlease.car.Car;

@RestController
@RequestMapping("/api/v1/contracts")
@CrossOrigin(origins = "http://localhost:3000")
public class ContractController {
	@Autowired
	ContractService contractService;
	
	@GetMapping("/")
	public List<Contract> getAllContract(){
		return contractService.getAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Contract> getContratById(@PathVariable("id") long id){
		Contract contract = contractService.getById(id);

		if (contract != null)
			return new ResponseEntity<>(contract,HttpStatus.OK);
		else
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") long id){
		if (contractService.deleteById(id) == true)
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		else
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/")
	public ResponseEntity<Contract> createContract(@RequestBody Contract contract) {
		try {
			Contract createdContract = contractService.save(contract);
			return new ResponseEntity<>(createdContract, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}")
	  public ResponseEntity<Contract> updateContract(@PathVariable("id") long id, @RequestBody Contract contract) {
	    Contract existingContract= contractService.getById(id);

	    if (existingContract != null) {
	      Contract updatedContrat = new Contract();
	      updatedContrat.setId(existingContract.getId());
	      updatedContrat.setDate(contract.getDate());
	      updatedContrat.setStart(contract.getStart());
	      updatedContrat.setEnd(contract.getEnd());
	      updatedContrat.setTotalPrice(contract.getTotalPrice());
	      updatedContrat.setAdvance(contract.getAdvance());
	      updatedContrat.setLeftToPay(contract.getLeftToPay());
	      updatedContrat.setPlaceOfReturn(contract.getPlaceOfReturn());
	      updatedContrat.setCar(contract.getCar());
	      updatedContrat.setInvoice(contract.getInvoice());
	      updatedContrat.setClient(contract.getClient());
	      return new ResponseEntity<>(contractService.save(updatedContrat), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	

	}
}
