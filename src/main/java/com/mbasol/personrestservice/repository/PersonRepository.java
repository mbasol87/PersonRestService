package com.mbasol.personrestservice.repository;

import com.mbasol.personrestservice.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    /**
     * Find persons by first and last name.
     */
    List<Person> findByFirstNameAndLastName(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
