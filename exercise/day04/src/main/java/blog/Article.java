package blog;

import java.util.ArrayList;
import java.util.List;

public class Article {
    private final String name;
    private final String content;
    private final ArrayList<Comment> comments;

    public Article(String name, String content) {
        this.name = name;
        this.content = content;
        this.comments = new ArrayList<>();
    }

    public void add(Comment comment) throws CommentAlreadyExistException {
        if (comments.contains(comment))
            throw new CommentAlreadyExistException();

        comments.add(comment);
    }

    public List<Comment> comments() {
        return comments;
    }
}

