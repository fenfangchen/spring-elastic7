package com.cff.springelastic.system.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;

@Data
@Document(indexName = "index_book" ,replicas = 0 ,shards = 3)
public class Book {
    @Id
    private Long id;
    private String name;
    private Float price;
    private String type;
    private Date buyDate;
}
