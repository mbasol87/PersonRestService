package com.mbasol.personrestservice.controller;

import com.mbasol.personrestservice.exception.PersonCrudException;
import com.mbasol.personrestservice.model.Parent;
import com.mbasol.personrestservice.model.Person;
import com.mbasol.personrestservice.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PersonController {

    Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    private PersonRepository personRepository;


    @GetMapping("/persons")
    public ResponseEntity<List<Person>> getAllPersons() {
        try {
            List<Person> list = personRepository.findAll();
            if (list.isEmpty() || list.size() == 0) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/persons/{id}")
    public ResponseEntity<Person> retrievePerson(@PathVariable long id) {
        Optional<Person> person = personRepository.findById(id);

        if (!person.isPresent()) {
            logger.info("Not found person with id-" + id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(person.get(), HttpStatus.OK);
    }

    @PostMapping("/persons")
    public ResponseEntity<Person> addPerson(@Valid @RequestBody Person person) {
        try{
            Person newPerson = new Person();
            newPerson.setFirstName(person.getFirstName());
            newPerson.setLastName(person.getLastName());
            newPerson.setBirthday(person.getBirthday());
            newPerson.setGender(person.getGender());

            if (!person.getParents().isEmpty()) {
                List<Parent> parentList = new ArrayList<>();
                for (Parent possibleParent : person.getParents()) {
                    if(possibleParent.getBirthday().isAfter(newPerson.getBirthday())){
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                    Parent newParent = new Parent();
                    newParent.setFirstName(possibleParent.getFirstName());
                    newParent.setLastName(possibleParent.getLastName());
                    newParent.setBirthday(possibleParent.getBirthday());
                    parentList.add(newParent);
                }
                newPerson.setParents(parentList);
            }
            Person savedPerson = personRepository.save(newPerson);
            return new ResponseEntity<>(savedPerson, HttpStatus.CREATED);
        }catch(Exception e){
            logger.error("Unexpected error during create person",new PersonCrudException(e));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/persons/{id}")
    public ResponseEntity<Person> updatePerson(@RequestBody Person newPerson, @PathVariable long id) {
        Optional<Person> personOptional = personRepository.findById(id);

        if (!personOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        try {
            personRepository.findById(id)
                    .map(person -> {
                        person.setFirstName(newPerson.getFirstName());
                        person.setLastName(newPerson.getLastName());
                        person.setGender(newPerson.getGender());
                        person.setBirthday(newPerson.getBirthday());

                        if (!person.getParents().isEmpty()) {
                            for (int i = 0; i < person.getParents().size(); i++) {
                                if( person.getParents().get(i).getBirthday().isAfter(newPerson.getBirthday())){
                                    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                                }
                                person.getParents().get(i).setFirstName(newPerson.getParents().get(i).getFirstName());
                                person.getParents().get(i).setLastName(newPerson.getParents().get(i).getLastName());
                                person.getParents().get(i).setBirthday(newPerson.getParents().get(i).getBirthday());
                            }
                        }
                        return new ResponseEntity<>(personRepository.save(person), HttpStatus.OK);
                    });

        } catch (Exception e) {
            logger.error("Unexpected issue while updating the person entity", new PersonCrudException(e));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/person/{id}")
    public ResponseEntity<HttpStatus> deletePerson(@PathVariable long id) {
        try {
            Optional<Person> person = personRepository.findById(id);
            if (person.isPresent()) {
                personRepository.delete(person.get());
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Unexpected issue while deleting the person entity", new PersonCrudException(e));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

