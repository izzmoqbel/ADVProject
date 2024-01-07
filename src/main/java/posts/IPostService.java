package posts;

import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;

import java.util.List;

public interface IPostService {
    void addPost(Post post);

    List<Post> getPosts(String author) throws SystemBusyException, BadRequestException, NotFoundException;

    void deletePost(String author, String id) throws SystemBusyException, BadRequestException, NotFoundException;
}
