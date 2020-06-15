package com.cff.springelastic;

import com.cff.springelastic.common.domain.MyPage;
import com.cff.springelastic.system.domain.Book;
import com.cff.springelastic.system.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@Slf4j
public class BookTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;
    @Autowired
    private SearchOperations searchOperations;

    @Test
    void testAdd(){
        /*for (int i = 0; i < 5; i++) {
            Book book = new Book();
            book.setId((long) i);
            book.setName("白雪公主"+i);
            book.setType("童话");
            book.setBuyDate(new Date());
            book.setPrice(15.9F);
            bookRepository.save(book);
        }
        for (int i = 5; i < 10; i++) {
            Book book = new Book();
            book.setId((long) i);
            book.setName("老人与海"+i);
            book.setType("寓言");
            book.setBuyDate(new Date());
            book.setPrice(10.9F);
            bookRepository.save(book);
        }*/
        Book book = new Book();
        book.setId((long) 10);
        book.setName("蓝雪公主");
        book.setType("童话");
        book.setBuyDate(new Date());
        book.setPrice(15.9F);
        bookRepository.save(book);
    }
    @Test
    void testUpdate() {
        Book book = new Book();
        book.setId(1L);
        book.setPrice(22F);
        book.setName("白雪公主21");
        /*book.setType("童话");
        book.setBuyDate(new Date());*/
        Book bookView = bookRepository.getById(book.getId());
        if (bookView!=null) {
            BeanUtils.copyProperties(book,bookView,getNullPropertyNames(book));
            bookRepository.save(bookView);
        }

    }
    /**
     * 获取需要忽略的属性
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            // 此处判断可根据需求修改
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    @Test
    void testDelete() {
        bookRepository.deleteById(0L);
    }

    void testGetList(){
        Pageable pageable = PageRequest.of(0,10);
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.termQuery("name","白雪"));
    }
    @Test
    void testPageList() {
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        Book book = new Book();
        book.setType("童话");
        QueryBuilder query = this.getQueryCondition(book);
        nativeSearchQueryBuilder.withQuery(query);
        int page = 0;
        int size = 10;
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page,size));
        SortBuilder sortBuilder = SortBuilders.fieldSort("price").order(SortOrder.ASC);
        nativeSearchQueryBuilder.withSort(sortBuilder);
        SearchHits<Book> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilder.build(),Book.class);
        MyPage<Book> myPage = new MyPage<>(searchHits);
        System.out.println("22222"+searchHits);
    }

    /**
     *
     * @param book
     * @return
     */
    private BoolQueryBuilder getQueryCondition(Book book) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(book != null){
            /*if (StringUtils.hasText(book.getType())) {
                boolQueryBuilder.must(QueryBuilders.termQuery("type.keyword",book.getType()));
            }
            if (StringUtils.hasText(book.getName())) {
                boolQueryBuilder.must(QueryBuilders.termQuery("name.keyword",book.getName()));
            }*/
            boolQueryBuilder.must(QueryBuilders.matchQuery("name","白"));
        }
        return boolQueryBuilder;
    }

}
