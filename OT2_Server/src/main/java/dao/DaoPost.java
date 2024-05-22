package dao;

import dao.imp.ImpDaoPost;
import entity.Post;
import entity.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.List;

public class DaoPost implements ImpDaoPost {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;

    public DaoPost() {
        entityManagerFactory = Persistence.createEntityManagerFactory("JPA_SQL");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
    }

    @Override
    public List<Post> listPostsByApprovalStatusAndYear(String status, int year) {
        String sql = "SELECT p FROM Post p WHERE p.approval.status = :status AND YEAR(p.approval.approvedDate) = :year";
        Status status1 = Status.fromString(status);
        try {
            entityTransaction.begin();
            List<Post> list = entityManager.createQuery(sql, Post.class)
                    .setParameter("status", status1)
                    .setParameter("year", year)
                    .getResultList();
            entityTransaction.commit();
            return list;
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Post> listMostLikedPosts() {
        String query = "SELECT p.id, p.likes\n" +
                "FROM Post p \n" +
                "group by p.id, p.likes\n" +
                "having p.likes >=all (SELECT p1.likes FROM Post p1)";
        try {
            entityTransaction.begin();
            List<Post> posts = new ArrayList<>();
            List<Object[]> results = entityManager.createQuery(query, Object[].class).getResultList();
            for (Object[] result : results) {
                Post post = entityManager.find(Post.class, (String) result[0]);
                posts.add(post);
            }
            entityTransaction.commit();
            return posts;
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return null;
    }
}
