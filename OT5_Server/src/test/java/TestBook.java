import dao.ImplDaoBook;
import dao.impl.DaoBook;
import entity.Book;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestBook {
    private ImplDaoBook daoBook;

    @BeforeAll
    public void init(){
        daoBook=new DaoBook();
    }

    @Test
    public void testListRatedBooks(){
        List<Book> books=daoBook.listRatedBooks("Robert C. Martin", 4);
        Assertions.assertNotNull(books);
        Assertions.assertEquals(2,books.size());
        books.forEach(System.out::println);
    }

    @Test
    public void testListRatedBooks2(){
        List<Book> books=daoBook.listRatedBooks("Robert C. Martin", 6);
        Assertions.assertNull(books);
    }
    @Test
    public void testCountBooksByAuthor(){
        Map<String,Long> map=daoBook.countBooksByAuthor();
        Assertions.assertNotNull(map);
        Assertions.assertEquals(3,map.size());
//        map.forEach((k,v)-> System.out.println(k+" - "+v));
    }
    @AfterAll
    public void destroy(){
        daoBook=null;
    }

}
