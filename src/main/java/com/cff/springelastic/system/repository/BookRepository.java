package com.cff.springelastic.system.repository;

import com.cff.springelastic.system.domain.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface BookRepository extends ElasticsearchRepository<Book,Long> {
    /**
     * 通过id获取
     * @param id
     * @return
     */
    Book getById(Long id);

}
