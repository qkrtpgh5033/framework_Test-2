package com.ll.exam;

import com.ll.exam.article.dto.ArticleDto;
import com.ll.exam.article.service.ArticleService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArticleServiceTest {

    @Test
    public void articleService가_존재한다(){
        ArticleService articleService = Container.getObj(ArticleService.class);
        assertThat(articleService).isNotNull();
    }

    @Test
    public void getArticles(){
        ArticleService articleService = Container.getObj(ArticleService.class);

        List<ArticleDto> articles = articleService.getArticles();
        assertThat(articles.size()).isEqualTo(3);
    }
}
