package com.cff.springelastic.system.controller;

import com.cff.springelastic.system.domain.Book;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/book")
public class BookController {

    public void addBook(@RequestBody Book book) {

    }
}
