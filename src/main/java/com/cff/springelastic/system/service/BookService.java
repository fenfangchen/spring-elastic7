package com.cff.springelastic.system.service;

import com.cff.springelastic.system.domain.Book;

public interface BookService {

    /**
     * 新增
     * @param book
     * @return
     */
    public Book addBook(Book book);

    /**
     * 编辑
     * @param book
     * @return
     */
    public Book updateBook(Book book);

    /**
     * 删除
     * @param id
     * @return
     */
    public Integer deleteBook(Long id);

}
