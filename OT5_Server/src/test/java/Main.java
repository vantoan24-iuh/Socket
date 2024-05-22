import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManager em= Persistence.createEntityManagerFactory("JPA_SQL").createEntityManager();
        EntityTransaction et=em.getTransaction();
        et.begin();
        et.commit();
    }
}
