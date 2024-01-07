package UserData.DeleteData;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import activity.IUserActivityService;
import activity.UserActivity;
import activity.UserActivityService;
import exception.BadRequestException;
import exception.NotFoundException;
import exception.SystemBusyException;
import payment.IPayment;
import payment.PaymentService;
import payment.Transaction;
import posts.IPostService;
import posts.Post;
import posts.PostService;

public class SoftDelete extends UserDataDeletionTemplate {
    private static final Logger logger = Logger.getLogger(SoftDelete.class.getName());

    @Override
    protected void deleteIamData(String userId) {
        // Soft delete, no need to delete IAM data
    }

    @Override
    protected void deleteAllUserActivities(String userId) {
        IUserActivityService userActivityService = new UserActivityService();
        int maxRetries = 100;
        int retryDelayMillis = 250;

        try {
            if (isNewUser(userId)) {
                logger.log(Level.INFO, "User is new and doesn't have activity data. User: {0}", userId);
                return;
            }

            List<UserActivity> userActivities = null;

            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                try {
                    userActivities = new ArrayList<>(userActivityService.getUserActivity(userId));
                    break;
                } catch (SystemBusyException e) {
                    try {
                        Thread.sleep(retryDelayMillis);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }

            if (userActivities != null && !userActivities.isEmpty()) {
                for (UserActivity activity : userActivities) {
                    for (int attempt = 1; attempt <= maxRetries; attempt++) {
                        try {
                            userActivityService.removeUserActivity(userId, activity.getId());
                            break;
                        } catch (SystemBusyException e) {
                            try {
                                Thread.sleep(retryDelayMillis);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                }
                logger.log(Level.INFO, "Deleted all ACTIVITIES for user: {0}", userId);
            } else {
                logger.log(Level.INFO, "User doesn't have activity data. User: {0}", userId);
            }

        } catch (NotFoundException | BadRequestException e) {
            logger.log(Level.SEVERE, "Error deleting activity data for user: " + userId, e);
        } catch (SystemBusyException e) {
            logger.log(Level.SEVERE, "System busy error while deleting activity data for user: " + userId, e);
        }
    }

    @Override
    protected void deletePaymentData(String userId) {
        int maxRetries = 10;
        int retryDelayMillis = 250;
        try {
            IPayment paymentService = new PaymentService();
            if (!isPremiumUser(userId)) {
                logger.log(Level.INFO, "User is not a premium user and doesn't have payment data. User: {0}", userId);
                return;
            }

            List<Transaction> userTransactions = new ArrayList<>(paymentService.getTransactions(userId));

            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                try {
                    userTransactions = new ArrayList<>(paymentService.getTransactions(userId));
                    break;
                } catch (SystemBusyException e) {
                    System.err.println("Attempt " + attempt + " failed to get transactions: " + e.getMessage());

                    try {
                        Thread.sleep(retryDelayMillis);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }

            if (userTransactions != null && !userTransactions.isEmpty()) {
                for (Transaction transaction : userTransactions) {
                    for (int attempt = 1; attempt <= maxRetries; attempt++) {
                        try {
                            paymentService.removeTransaction(userId, transaction.getId());
                            break;
                        } catch (SystemBusyException | BadRequestException e) {
                            System.err
                                    .println("Attempt " + attempt + " failed to delete transaction: " + e.getMessage());

                            try {
                                Thread.sleep(retryDelayMillis);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                }
                logger.log(Level.INFO, "Deleted PAYMENT data for user: {0}", userId);
            } else {
                logger.log(Level.INFO, "User doesn't have payment data. User: {0}", userId);
            }
        } catch (NotFoundException | SystemBusyException | BadRequestException e) {
            logger.log(Level.SEVERE, "Error deleting payment data for user: " + userId, e);
        }
    }

    @Override
    protected void deletePostData(String userId) {
        IPostService postService = new PostService();
        int maxRetries = 10;
        int retryDelayMillis = 250;

        try {
            List<Post> userPosts = null;

            for (int attempt = 1; attempt <= maxRetries; attempt++) {
                try {
                    userPosts = new ArrayList<>(postService.getPosts(userId));
                    break;
                } catch (SystemBusyException e) {
                    System.err.println("Attempt " + attempt + " failed to get posts: " + e.getMessage());

                    try {
                        Thread.sleep(retryDelayMillis);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            }

            if (userPosts != null && !userPosts.isEmpty()) {
                for (Post post : userPosts) {
                    for (int attempt = 1; attempt <= maxRetries; attempt++) {
                        try {
                            postService.deletePost(userId, post.getId());
                            break;
                        } catch (SystemBusyException e) {
                            try {
                                Thread.sleep(retryDelayMillis);
                            } catch (InterruptedException interruptedException) {
                                interruptedException.printStackTrace();
                            }
                        }
                    }
                }
                logger.log(Level.INFO, "Deleted all POSTS for user: {0}", userId);
            } else {
                logger.log(Level.INFO, "User doesn't have post data. User: {0}", userId);
            }
        } catch (NotFoundException | BadRequestException e) {
            logger.log(Level.SEVERE, "Error deleting post data for user: " + userId, e);
        }
    }

    private boolean isNewUser(String userId) throws BadRequestException, SystemBusyException, NotFoundException {
        try {
            IUserActivityService userActivityService = new UserActivityService();
            userActivityService.getUserActivity(userId);
            return false;
        } catch (NotFoundException e) {
            return true;
        } catch (SystemBusyException | BadRequestException e) {
            logger.log(Level.SEVERE, e.getMessage());
            return false;
        }
    }

    private boolean isPremiumUser(String userId) throws NotFoundException, SystemBusyException, BadRequestException {
        IPayment paymentService = new PaymentService();
        return paymentService.getBalance(userId) > 0;
    }


    
}