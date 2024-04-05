package com.example.firstproject.entity;

import com.example.firstproject.dto.CommentDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="article_id") // 형식 => @JoinColumn(name="FK 이름") -> 조인 테이블의 PK값에 맵핑됨
    private Article article;
    @Column
    private String nickname;
    @Column
    private String body;




    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 발생
        if(dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        if(dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못됐습니다.");
        // 엔티티 생성 및 반환
        return new Comment(
                dto.getId(),
                article,
                dto.getNickname(),
                dto.getBody()
        );
    }

    public void patch(CommentDto dto) {
        //1. 예외 발생
        if(this.id != dto.getId())throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력됐습니다.");
        //2. 객체 갱신
        if(dto.getNickname() != null)
            this.nickname = dto.getNickname(); // 내용 반영

        if(dto.getBody() != null)
            this.body = dto.getBody();
    }
}
