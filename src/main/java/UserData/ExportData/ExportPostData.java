package UserData.ExportData;

import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;
import exception.Util;
import posts.IPostService;
import posts.Post;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExportPostData implements ExportStrategy{
    private static final Logger logger = Logger.getLogger(ExportPostData.class.getName());

    private final IPostService postService;

    GivePath givePath = new GivePath();

    public ExportPostData(IPostService postService) {
        this.postService = postService;
    }
    @Override
    public void exportData(String userId, String folderPath) throws SystemBusyException, BadRequestException, NotFoundException {
        Util.validateUserName(userId);

        try {
            List<Post> userPosts = postService.getPosts(userId);

            if (userPosts != null && !userPosts.isEmpty()) {
                String fileName = folderPath + givePath.path(userId,"POST");

                try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                    writer.println("Post Data for User: " + userId);
                    writer.println("----------------------------------");

                    for (Post post : userPosts) {
                        writer.println("Post ID: " + post.getId());
                        writer.println("Title: " + post.getTitle());
                        writer.println("Body: " + post.getBody());
                        writer.println("Author: " + post.getAuthor());
                        writer.println("Date: " + post.getDate());
                        writer.println("----------------------");
                    }

                    logger.log(Level.INFO, "Post data exported to file: {0}", fileName);
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Error exporting post data for user " +userId + ": {0}", e.getMessage());
                }
            } else {
                logger.log(Level.INFO, "No posts found for User ID: {0}", userId);
            }
        } catch (SystemBusyException e) {
            logger.log(Level.SEVERE, "Error: System is busy. Please try again later. {0}", e);
        } catch (NotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}