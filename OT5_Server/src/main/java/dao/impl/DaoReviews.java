package dao.impl;

import dao.ImplDaoReviews;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class DaoReviews implements ImplDaoReviews {
    private EntityManager em;
    private EntityTransaction et;

    public DaoReviews() {
        em = Persistence.createEntityManagerFactory("JPA_SQL").createEntityManager();
        et = em.getTransaction();
    }
    @Override
    public boolean updateReview(String isbn, int rating, String comment) {
        String sql="update Reviews r set r.rating=:rating, r.comment=:comment where r.book.ISBN=:isbn";
        try{
            et.begin();
            if(rating<1 || rating>5){
                return false;
            }else if(comment==null || comment.isEmpty()){
                return false;
            }
            em.createQuery(sql)
                    .setParameter("rating", rating)
                    .setParameter("comment", comment)
                    .setParameter("isbn", isbn)
                    .executeUpdate();
            et.commit();
            return true;
        }catch (Exception e){
            et.rollback();
            e.printStackTrace();
        }
        return false;
    }
}
