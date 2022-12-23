package jean.jerome.caramazinlease.option;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jean.jerome.caramazinlease.invoice.Invoice;

@RestController
@RequestMapping("/api/v1/options")
@CrossOrigin(origins = "http://localhost:3000")
public class OptionController {
	@Autowired
	OptionService optionService;
	
	@GetMapping("/")
	public ResponseEntity<List<Option>> getAllOption(){
		try {
			return new ResponseEntity<>(optionService.getAll(),HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Option> getOption(@PathVariable("id") long id){
		Option option= optionService.getById(id);
		
		if(option != null)
			return new ResponseEntity<>(option, HttpStatus.OK);
		else 
			return new  ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@PostMapping("/")
	public ResponseEntity<Option> createOption(@RequestBody Option option){
		try {
			Option createdOption = optionService.save(option);
			return new ResponseEntity<Option>(createdOption, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/{id}")
	  public ResponseEntity<Option> updateOption(@PathVariable("id") long id, @RequestBody Option option) {
	    Option existingOption= optionService.getById(id);

	    if (existingOption != null) {
	      Option updatedOption= new Option();
	      updatedOption.setId(option.getId());
	      updatedOption.setName(option.getName());
	      updatedOption.setDescription(option.getDescription());
	      updatedOption.setCars(option.getCars());
	      return new ResponseEntity<>(optionService.save(updatedOption), HttpStatus.OK);
	    } else {
	      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
}
