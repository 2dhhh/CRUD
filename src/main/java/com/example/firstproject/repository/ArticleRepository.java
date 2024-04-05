package com.example.firstproject.repository;

import com.example.firstproject.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.List;

// CrudRepository<T, ID> T: 관리 대상 엔티티의 클래스 타입, ID: 엔티티의 대푯값, JPA에서 제공하는 인터페이스로 이를 상속하여
// 엔티티를 관리할 수 있다
public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Override
    List<Article> findAll(); // Iterable -> ArrayList 수정

}
















