package com.ll.exam;

import com.ll.exam.article.dto.ArticleDto;
import com.ll.exam.article.service.ArticleService;
import com.ll.exam.mymap.MyMap;
import com.ll.exam.util.Ut;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ArticleServiceTest {

    static ArticleService articleService;
    @BeforeAll
    public void beforeAll(){
        articleService = Container.getObj(ArticleService.class);
        MyMap myMap = Container.getObj(MyMap.class);
        myMap.setDevMode(true);
    }

    @BeforeEach
    public void beforeEach() {
        // 게시물 테이블을 깔끔하게 삭제한다.
        // DELETE FROM article; // 보다 TRUNCATE article; 로 삭제하는게 더 깔끔하고 흔적이 남지 않는다.
        truncateArticleTable();
        // 게시물 3개를 만든다.
        // 테스트에 필요한 샘플데이터를 만든다고 보면 된다.
        makeArticleTestData();
    }

    private void makeArticleTestData() {
        MyMap myMap = Container.getObj(MyMap.class);

        IntStream.rangeClosed(1, 3).forEach(no -> {
            boolean isBlind = false;
            String title = "제목%d".formatted(no);
            String body = "내용%d".formatted(no);

            myMap.run("""
                    INSERT INTO article
                    SET createdDate = NOW(),
                    modifiedDate = NOW(),
                    title = ?,
                    `body` = ?,
                    isBlind = ?
                    """, title, body, isBlind);
        });
    }

    private void truncateArticleTable() {
        MyMap myMap = Container.getObj(MyMap.class);
        // 테이블을 깔끔하게 지워준다.
        myMap.run("TRUNCATE article");
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

    @Test
    public void write() {

        long newArticleId = articleService.write("제목 new", "내용 new", false);

        ArticleDto articleDto = articleService.getArticleById(newArticleId);

        assertThat(articleDto.getId()).isEqualTo(newArticleId);
        assertThat(articleDto.getTitle()).isEqualTo("제목 new");
        assertThat(articleDto.getBody()).isEqualTo("내용 new");
        assertThat(articleDto.getCreatedDate()).isNotNull();
        assertThat(articleDto.getModifiedDate()).isNotNull();
        assertThat(articleDto.isBlind()).isEqualTo(false);
    }

    @Test
    public void modify() {
//        Ut.sleep(5000);
        articleService.modify(1, "제목 new", "내용 new", true);

        ArticleDto articleDto = articleService.getArticleById(1);

        assertThat(articleDto.getId()).isEqualTo(1);
        assertThat(articleDto.getTitle()).isEqualTo("제목 new");
        assertThat(articleDto.getBody()).isEqualTo("내용 new");
        assertThat(articleDto.getCreatedDate()).isNotNull();
        assertThat(articleDto.getModifiedDate()).isNotNull();
        assertThat(articleDto.isBlind()).isEqualTo(true);
    }
    @Test
    public void delete() {

        articleService.deleteById(1);

        ArticleDto articleDto = articleService.getArticleById(1);
        assertThat(articleDto).isNull();

    }

}
