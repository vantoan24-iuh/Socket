import dao.ImpDaoPerson;
import dao.imp.DaoPerson;
import entity.Person;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestPerson {
    private ImpDaoPerson daoPerson;

    @BeforeAll
    public void init(){
        daoPerson = new DaoPerson();
    }

    //a) Liet ke danh sach nhung nguoi da tung bao tri co so vat chat nao do khi biet ten (tim tuong doi, khong phan biet chu thuong hoa) va co chi phi >0
    @Test
    public void testGetMainanceWorkers(){
        List<Person> people = daoPerson.getMainanceWorkers("room");
        for(Person p: people){
            System.out.println(p);
        }
    }

    @AfterAll
    public void destroy(){
        daoPerson=null;
    }
}
