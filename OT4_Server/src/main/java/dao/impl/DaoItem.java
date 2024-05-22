package dao.impl;

import dao.ImplDaoItem;
import entity.Ingredient;
import entity.Item;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.criteria.ListJoin;

import java.util.List;

public class DaoItem implements ImplDaoItem {
    private EntityManager em;
    private EntityTransaction et;
    public DaoItem() {
        em= Persistence.createEntityManagerFactory("JPA_SQL").createEntityManager();
        et=em.getTransaction();
    }

    //Liệt kê danh sách mặt hàng là món đặt biệt của nhà hàng mà có sử dụng nguyên
    //liệu được nhập từ nhà cung cấp nào đó khi biết tên nhà cung cấp (tìm tương đối, không phân biệt
    //chữ thường hoa).
    //+ listItems (supplierName: String) : List<Item>
    @Override
    public List<Item> listItems(String supplierName) {
        String sql = "SELECT i FROM Ingredient ig JOIN ig.item i WHERE ig.supplierName LIKE :supplierName and i.onSpecial = true";
        try
        {
            et.begin();
            List<Item> listItem=em.createQuery(sql,Item.class)
                    .setParameter("supplierName","%"+supplierName+"%")
                    .getResultList();
            et.commit ();
            return listItem;
        }catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return null;
    }
}
