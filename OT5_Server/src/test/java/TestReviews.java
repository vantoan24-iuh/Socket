import dao.ImplDaoReviews;
import dao.impl.DaoReviews;
import entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestReviews {
    private ImplDaoReviews daoReviews;
    private EntityManager em;

    @BeforeAll
    public void init(){
        em= Persistence.createEntityManagerFactory("JPA_SQL").createEntityManager();
        daoReviews=new DaoReviews();
    }
    @Test
    public void testUpdateReview(){
        boolean result=daoReviews.updateReview("888-0132350800", 5, "Great book");
        System.out.println(result);
    }
    @Test
    public void testUpdateReview2(){
        boolean result=daoReviews.updateReview("888-0132350800", 6, "");
        System.out.println(result);
    }
    @Test
    public void testUpdateReview3(){
        boolean result=daoReviews.updateReview("888-0132350800", 5, "");
        System.out.println(result);
    }
    @AfterAll
    public void destroy(){
        daoReviews=null;
    }

}
