package com.example.firstproject.repository;

import com.example.firstproject.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment ,Long> {
    // 특정 게시글의 모든 댓글 조회
    // value 속성에 실행하려는 쿼리 작성
    // nativeQuery 속성을 true로 설정하여 JPQL이 아닌 SQL문을 그대로 사용 가능하다.
    // 주의할 점 SQL문의 WHERE 절에 조건을 쓸 때 매개변수 앞에는 꼭 콜론(:)을 붙여 메서드의 매개변수와 바인딩된다.
    @Query(value = "SELECT * FROM comment WHERE article_id = :articleId",
            nativeQuery = true)
    List<Comment> findByArticleId(Long articleId);


    // 특정 닉네임의 모든 댓글 조회
    List<Comment> findByNickname(String nickname);
}

