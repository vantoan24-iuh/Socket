package dao.impl;

import dao.ImplDaoBook;
import entity.Book;
import entity.Reviews;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.hibernate.Hibernate;

import java.util.*;

public class DaoBook implements ImplDaoBook {
    private EntityManager em;
    private EntityTransaction et;

    public DaoBook() {
        em = Persistence.createEntityManagerFactory("JPA_SQL").createEntityManager();
        et = em.getTransaction();
    }

    //Liệt kê danh sách các cuốn sách được viết bởi tác giả nào đó khi biết tên tác giả và
    //có điểm đánh giá từ mấy sao trở lên.
    //+ listRatedBooks(author: String, rating: int): List<Book>
    @Override
    public List<Book> listRatedBooks(String author, int rating) {
        if(rating<1 || rating>5){
            return null;
        }
        String sql = "select distinct b from Book b join fetch b.reviews r where :author member of b.authors and r.rating >= :rating";
        List<Book> listBooks = null;
        try {
            et.begin();
            listBooks = em.createQuery(sql, Book.class)
                    .setParameter("author", author)
                    .setParameter("rating", rating)
                    .getResultList();
            listBooks.forEach(b -> {
                Hibernate.initialize(b.getAuthors());
            });
            et.commit();
            return listBooks;
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Map<String, Long> countBooksByAuthor() {
//        String sql= "select ba,COUNT(*) as soCuonSachDuocDich from Book b join BookTranslation bt on b.ISBN=bt.ISBN join b.authors ba group by ba";
        String sql="select ba.author,COUNT(*) as soCuonSachDuocDich from books b join book_translations bt on b.ISBN=bt.ISBN join books_authors ba on b.ISBN=ba.ISBN\n" +
                "group by ba.author\n" +
                "order by ba.author desc";
        Map<String,Long> map=new LinkedHashMap<>();
        try{
            et.begin();
            List<Object[]> list=em.createNativeQuery(sql).getResultList();
            for (Object[] objects : list) {
                String author = (String) objects[0];
                Long count = ((Integer) objects[1]).longValue();
                map.put(author, count);
            }
            et.commit();
            return map;
        }catch (Exception e){
            et.rollback();
        }
        return null;
    }
}
