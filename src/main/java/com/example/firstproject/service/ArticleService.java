package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.SysexMessage;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    // 모든 게시글 조회
    public List<Article> index(){

        return articleRepository.findAll();
    }

    // 단일 게시글 조회
    public Article show(Long id) {

        return articleRepository.findById(id).orElse(null);
    }

    // 게시글 생성
    public Article create(ArticleForm dto){
        Article article = dto.toEntity();
        if(article.getId() != null){
            return null;
        }
        return articleRepository.save(article);
    }

    // 게시글 수정
    public Article update(Long id, ArticleForm dto) {

        //1. DTO -> 엔티티 변환하기
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());
        //2. 타깃 조회하기
        Article target =articleRepository.findById(id).orElse(null);
        //3. 잘못된 요청 처리하기
        if(target == null || id != article.getId()){
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            return null;
        }
        //4. 업데이트 및 정상 응답하기
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;

    }

    public Article delete(Long id) {
        //1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        //2. 잘못된 요청 처리하기
        if(target == null){
            return null;
        }
        //3. 대상 삭제하기
        articleRepository.delete(target);
        return target;
    }


    // @Transactional 어노테이션은 선언된 메서드를 트랜잭션으로 묶어준다. 클래스에 이 어노테이션을 선언하면 클래스의 모든 메서드별로 각각의 트랜잭션이 부여된다
    // 트랜잭션으로 묶인 메서드는 완전히 실행되거나 실행되지 않거나 둘 중 하나로 동작한다. 중간에 실패하면 롤백되어 처음상태로 되돌아가기 때문
    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        //1. dto 묶음(리스트)을 엔티티 묶음(리스트)으로 변환하기
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());
        //2. 엔티티 묶음(리스트)을 DB에 저장하기
        articleList.stream()
                .forEach(article -> articleRepository.save(article));
        //3. 강제로 에러를 발생시키기
        articleRepository.findById(-1L)
                .orElseThrow(() -> new IllegalArgumentException("결제 실패"));
        //4. 결과 값 반환하기
        return articleList;
    }
}
