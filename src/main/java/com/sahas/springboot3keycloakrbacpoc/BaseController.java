package com.sahas.springboot3keycloakrbacpoc;

import jakarta.annotation.security.RolesAllowed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.sahas.springboot3keycloakrbacpoc.config.Constants.KC_ROLE_ADMIN;
import static com.sahas.springboot3keycloakrbacpoc.config.Constants.KC_ROLE_USER;

@Slf4j
@RestController
@RequestMapping(path = "${app.api.prefix.version}/students")
public class BaseController
{
    @RolesAllowed({KC_ROLE_USER, KC_ROLE_ADMIN}) // Allow for both user and admin roles
    @GetMapping
    public ResponseEntity<String> getAllStudents()
    {
        // TODO: Your logic here
        return ResponseEntity.status(HttpStatus.OK).body("All Students");
    }

    @RolesAllowed(KC_ROLE_USER) // Only allow for user role
    @GetMapping("/{id}")
    public ResponseEntity<String> getStudentById(@PathVariable String id)
    {
        // TODO: Your logic here
        return ResponseEntity.status(HttpStatus.OK).body("Student Id: " + id);
    }

    @RolesAllowed(KC_ROLE_ADMIN) //Only allow for admin role
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable String id)
    {
        // TODO: Your logic here
        return ResponseEntity.status(HttpStatus.OK).body("Deleted Student. Id: " + id);
    }
}