package test;

import dao.DaoPost;
import entity.Post;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestPost {
    private DaoPost daoPost;

    @BeforeAll
    public void init() {
        daoPost = new DaoPost();
    }

    @org.junit.jupiter.api.Test
    public void testListPostsByApprovalStatusAndYear() {
        List<Post> list = daoPost.listPostsByApprovalStatusAndYear("APPROVED", 2024);
        for (Post post : list) {
            System.out.println(post);
        }
    }

    @org.junit.jupiter.api.Test
    public void testListMostLikedPosts() {
        List<Post> list = daoPost.listMostLikedPosts();
        for (Post post : list) {
            System.out.println(post);
        }
    }
    @AfterAll
    public void destroy() {
        daoPost = null;
    }
}
