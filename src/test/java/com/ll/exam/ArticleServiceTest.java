package com.ll.exam;

import com.ll.exam.article.dto.ArticleDto;
import com.ll.exam.article.service.ArticleService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleServiceTest {

    static ArticleService articleService;
    @BeforeAll
    static void beforeAll(){
        articleService = Container.getObj(ArticleService.class);
    }
    @Test
    public void articleService가_존재한다(){
        assertThat(articleService).isNotNull();
    }

    @Test
    public void getArticles(){
        List<ArticleDto> articles = articleService.getArticles();
        assertThat(articles.size()).isEqualTo(3);
    }

    @Test
    public void getArticleById(){
        ArticleDto articleDto = articleService.getArticleById(1);
        assertThat(articleDto.getId()).isEqualTo(1L);
        assertThat(articleDto.getTitle()).isEqualTo("제목1");
        assertThat(articleDto.getBody()).isEqualTo("내용1");
        assertThat(articleDto.getCreatedDate()).isNotNull();
        assertThat(articleDto.getModifiedDate()).isNotNull();
        assertThat(articleDto.isBlind()).isFalse();

        ArticleDto articleDto2 = articleService.getArticleById(2);
        assertThat(articleDto2.getId()).isEqualTo(2L);
        assertThat(articleDto2.getTitle()).isEqualTo("제목2");
        assertThat(articleDto2.getBody()).isEqualTo("내용2");
        assertThat(articleDto2.getCreatedDate()).isNotNull();
        assertThat(articleDto2.getModifiedDate()).isNotNull();
        assertThat(articleDto2.isBlind()).isFalse();
    }

    @Test
    public void getArticlesCount(){
        long articlesCount = articleService.getArticlesCount();
        assertThat(articlesCount).isEqualTo(3);
    }


}
