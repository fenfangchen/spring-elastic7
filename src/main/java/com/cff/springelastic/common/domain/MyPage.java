package com.cff.springelastic.common.domain;

import lombok.Data;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MyPage<T> implements Serializable {

    /**
     * 总条数
     */
    private Long total;

    /**
     * 当前页
     */
    private Long current;
    /**
     * 内容
     */
    private List<T> content;

    /**
     * 总页数
     */
    private Long pages;

    public MyPage(SearchHits<T> searchHits) {
        this.total = searchHits.getTotalHits();
        this.content = searchHits.getSearchHits().stream().map(SearchHit::getContent).collect(Collectors.toList());
    }

}
