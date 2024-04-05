package com.example.firstproject.controller;


import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;


@Controller
@Slf4j
public class ArticleController {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;

    @GetMapping("/articles/new")
    public String newArticleForm(){

        return "/articles/new";
    }

    // 게시글 생성
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){
        log.info(form.toString());

        //1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        //2. 리파지터리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article); //
        log.info(saved.toString());

        //3. 상세페이지로 리다이렉트함 -> 단일 데이터 조회 요청
        return "redirect:/articles/" + saved.getId();

    }

    // 게시글 조회 Read
    @GetMapping("/articles/{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        // 1. id를 조회해 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);
        // 2. 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);
        // 3. 뷰 페이지 반환하기
        return "articles/show";
    }

    // 모든 게시글 목록 조회 Read
    @GetMapping("/articles")
    public String index(Model model){
        // 1. DB에서 모든 Article 데이터 가져오기
        List<Article> articleEntityList = articleRepository.findAll();
        // 2. 가져온 Article 묶음을 모델에 등록하기
        model.addAttribute("articleList", articleEntityList);
        // 3. 사용자에게 보여 줄 뷰 페이지 설정하기
        return "articles/index";
    }

    // 게시글 수정-1 Update
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        //수정할 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 모델에 데이터 등록하기
        model.addAttribute("article", articleEntity);

        // 뷰페이지 설정하기
        return "articles/edit";
    }
    // 게시글 수정-2 Update
    @PostMapping("/articles/{id}/update")
    public String update(ArticleForm form){
        log.info(form.toString());
        //1. DTO를 엔티티로 변환하기
        Article articleEntity = form.toEntity();
        log.info(articleEntity.toString());
        //2. 엔티티를 DB에 저장하기

        //2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //2-2. 기존 데이터 값을 갱신하기
        if(target != null){
            articleRepository.save(articleEntity);
        }
        //3. 수정 결과 페이지로 리다이렉트하기
        return "redirect:/articles/" + articleEntity.getId();
    }

    // 데이터 삭제 Delete
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다!!");
        //1. 삭제할 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        //2. 대상 엔티티 삭제하기
        if(target != null){
            articleRepository.delete(target);
            rttr.addFlashAttribute("msg", "삭제됐습니다!");
        }
        //3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }


}
