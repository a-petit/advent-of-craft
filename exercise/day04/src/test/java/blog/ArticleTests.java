package blog;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ArticleTests {

    @Test
    void adding_a_comment_make_it_available_in_the_list_of_comments() throws CommentAlreadyExistException {
        var article = ArticleBuilder.any();
        var commentToAdd = CommentBuilder.any();

        article.addComment(commentToAdd);

        assertThat(article.getComments())
            .hasSize(1)
            .anyMatch(comment -> comment == commentToAdd);
    }

    @Test
    void adding_another_comment_make_it_available_in_the_list_of_comments() throws CommentAlreadyExistException {
        var article = ArticleBuilder.any();
        var initialComment = CommentBuilder.from("author 1");
        var anotherComment = CommentBuilder.from("author 2");
        article.addComment(initialComment);

        article.addComment(anotherComment);

        assertThat(article.getComments())
            .hasSize(2)
            .anyMatch(comment -> comment == initialComment)
            .anyMatch(comment -> comment == anotherComment);
    }

    @Test
    void adding_a_same_comment_twice_fails() throws CommentAlreadyExistException {
        var article = ArticleBuilder.any();
        var sameComment = CommentBuilder.any();
        article.addComment(sameComment);

        assertThatThrownBy(
            () -> article.addComment(sameComment)
        ).isInstanceOf(CommentAlreadyExistException.class);
    }
}
