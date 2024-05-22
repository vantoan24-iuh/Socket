package dao.imp;

import dao.ImpDaoPerson;
import entity.Person;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.List;

public class DaoPerson implements ImpDaoPerson {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    public DaoPerson() {
        entityManagerFactory = Persistence.createEntityManagerFactory("JPA_SQL");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
    }

    //a) Liet ke danh sach nhung nguoi da tung bao tri co so vat chat nao do khi biet ten (tim tuong doi, khong phan biet chu thuong hoa) va co chi phi >0
    @Override
    public List<Person> getMainanceWorkers(String facilityName) {
        String sql="select p from Person p join Maintenance m on p.id= m.person.id join Facility f on f.id=m.facility.id where f.name like :facilityName and m.cost>0";
        try{
            entityTransaction.begin();
            List<Person> people = entityManager.createQuery(sql, Person.class)
                    .setParameter("facilityName", "%"+facilityName+"%")
                    .getResultList();
            entityTransaction.commit();
            return people;
        }
        catch (Exception e){
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return null;
    }
}
