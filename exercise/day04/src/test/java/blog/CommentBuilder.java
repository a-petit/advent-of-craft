package blog;

import java.time.LocalDate;

public class CommentBuilder {
    public static Comment any() {
        return from("author1");
    }


    public static Comment from(String author) {
        return new Comment("comment 1", author, LocalDate.now());
    }
}
