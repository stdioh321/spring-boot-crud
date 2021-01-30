package com.stdioh321.crud.controller.api;

import com.stdioh321.crud.exception.RestGenericExecption;
import com.stdioh321.crud.repository.StateRepository;
import com.stdioh321.crud.service.StateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/temp")
public class TempController {
    @Autowired
    private StateService stateService;

    @Value("${db1.url}")
    private String dbUrl;


    @Autowired
    private StateRepository stateRepository;

    @GetMapping
    public ResponseEntity get() {
        return ResponseEntity.ok(stateService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity getById(@PathVariable("id") String id) {
        return ResponseEntity.ok(stateService.getById(id));
    }


    @GetMapping("/pagination")
    public ResponseEntity getByPagination(
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(defaultValue = "id") String sort
    ) {

        return ResponseEntity.ok(stateService.getPaginated(page,size));
    }

    @GetMapping("/db")
    public ResponseEntity db(){
        return ResponseEntity.ok(dbUrl);
    }


}
