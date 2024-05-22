package dao.impl;

import dao.ImplDaoFood;
import entity.Food;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DaoFood implements ImplDaoFood {
    private EntityManager em;
    private EntityTransaction et;
    public DaoFood() {
        em= Persistence.createEntityManagerFactory("JPA_SQL").createEntityManager();
        et=em.getTransaction();
    }
    //Thêm một món ăn mới. Trong đó, mã số của món phải bắt đầu là F và theo sau ít
    //nhất 3 ký số
    @Override
    public boolean addFood(Food food) {
        try
        {
            et.begin();
            if(food.getId().matches("F\\d{3}"))
            {
                em.persist(food);
                et.commit();
                return true;
            }else {
                return false;
            }
        }
        catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return false;
    }

    //Tính giá gốc của từng món ăn sau khi chế biết thành phẩm. Kết quả sắp xếp giảm dần theo đơn giá gốc.
    //Trong đó: Giá gốc món ăn = (số lượng nguyên liệu * đơn giá nguyên liệu) + (thời gian chuẩn bị + thời gian phục vụ) * 0.2$
    @Override
    public Map<Food, Double> listFoodAndCost() {
        String sql = "select f.id, sum(ing.price * ing.quantity) + (f.preparation_time + f.serving_time)*0.2 as giaGoc from foods f \n" +
                "join items i on f.id = i.id\n" +
                "join items_ingredients ii on i.id = ii.item_id\n" +
                "join ingredients ing on ii.ingredient_id = ing.ingredient_id\n" +
                "group by f.id, f.preparation_time, f.serving_time " +
                "order by giaGoc desc;";
        Map<Food, Double> map = new LinkedHashMap<>();
        try {
            et.begin();
            List<Object[]> list = em.createNativeQuery(sql).getResultList();
            list.forEach(objects -> {
                Food food = em.find(Food.class, objects[0]);
                map.put(food, (Double) objects[1]);
            });
            et.commit();
            return map;
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return null;
    }
}
