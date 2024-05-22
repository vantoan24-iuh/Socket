package dao;

import dao.imp.ImpDaoAdmin;
import entity.Admin;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DaoAdmin implements ImpDaoAdmin {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    public DaoAdmin() {
        entityManagerFactory= Persistence.createEntityManagerFactory("JPA_SQL");
        entityManager=entityManagerFactory.createEntityManager();
        entityTransaction=entityManager.getTransaction();
    }
    @Override
    public Map<Admin, Long> listAdminsAndNumberOfPosts() {
        String sql="select a.username,COUNT(p) as soBaiViet from Admin a join Post p on a.username=p.approval.admin.username group by a.username";
        Map<Admin, Long> map=new LinkedHashMap<>();
        try {
            entityTransaction.begin();
            List<?> list=entityManager.createQuery(sql).getResultList();
            list.stream().map(o->(Object[])o).forEach(a->{
                Admin admin=entityManager.find(Admin.class,a[0]);
                Long numberOfPosts=(Long)a[1];
                map.put(admin,numberOfPosts);
            });
            entityTransaction.commit();
            return map;
        }catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return null;
    }
}
