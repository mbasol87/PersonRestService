package com.mbasol.personrestservice;

import com.mbasol.personrestservice.model.Parent;
import com.mbasol.personrestservice.model.Person;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Collections;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PersonRestServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonRestServiceApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void contextLoads() {
    }

    @Test
    public void testGetAllPersons() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<String>(null, headers);

        ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/persons",
                HttpMethod.GET, entity, String.class);

        Assert.assertNotNull(response.getBody());
    }

    @Test
    public void testGetPersonById() {
        restTemplate.postForEntity(getRootUrl() + "/persons", createPerson(), Person.class);
        Person person = restTemplate.getForObject(getRootUrl() + "/persons/1", Person.class);
        System.out.println(person.getFirstName());
        Assert.assertNotNull(person);
    }

    private static Person createPerson(){
        Person person = new Person();
        person.setFirstName("Bart");
        person.setLastName("Simpson");
        LocalDate date = LocalDate.parse("2000-01-01");
        person.setBirthday(date);
        person.setGender("Male");

        Parent parentPerson1 = new Parent();
        parentPerson1.setFirstName("Marge");
        parentPerson1.setLastName("Simpson");
        LocalDate date2 = LocalDate.parse("1984-11-20");
        parentPerson1.setBirthday(date);

        person.setParents(Collections.singletonList(parentPerson1));
        return person;
    }

    @Test
    public void testCreatePerson() {
        ResponseEntity<Person> postResponse = restTemplate.postForEntity(getRootUrl() + "/persons", createPerson(), Person.class);
        Assert.assertNotNull(postResponse);
        Assert.assertNotNull(postResponse.getBody());
    }

    @Test
    public void testUpdatePost() {
        int id = 1;
        Person person = restTemplate.getForObject(getRootUrl() + "/persons/" + id, Person.class);
        person.setFirstName("updated1");
        person.setLastName("updated2");

        restTemplate.put(getRootUrl() + "/persons/" + id, person);

        Person updatedPerson = restTemplate.getForObject(getRootUrl() + "/persons/" + id, Person.class);
        Assert.assertNotNull(updatedPerson);
    }

    @Test
    public void testDeletePost() {
        int id = 1;
        restTemplate.postForEntity(getRootUrl() + "/persons", createPerson(), Person.class);
        Person person = restTemplate.getForObject(getRootUrl() + "/persons/" + id, Person.class);
        Assert.assertNotNull(person);

        restTemplate.delete(getRootUrl() + "/persons/" + id);

        try {
            person = restTemplate.getForObject(getRootUrl() + "/persons/" + id, Person.class);
        } catch (final HttpClientErrorException e) {
            Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
        }
    }

}
