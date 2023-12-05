package blog;

public class ArticleBuilder {
    public static Article any() {
        return new Article(
            "Article 1",
            "Content of the article"
        );
    }
}
