package com.example.firstproject.api;


import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.service.CommentService;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
public class CommentApiController {
    @Autowired
    private CommentService commentService;

    //1. 댓글 조회
    @GetMapping("/api/articles/{articleid}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleid){
        // 서비스에 위임
        List<CommentDto> dtos = commentService.comments(articleid);
        // 결과 응답 
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    //2. 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto){
        //1. 서비스에 위임
        CommentDto createDto = commentService.create(articleId, dto);
        //2. 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createDto);
    }

    //3. 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto){
        //서비스에 위임
        CommentDto updatedto = commentService.update(id,dto);
        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedto);
    }

    //4. 댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id){
        //1. 서비스에 위임
        CommentDto deleteDto = commentService.delete(id);
        //2. 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }
}
