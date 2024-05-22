import dao.ImplDaoFood;
import dao.impl.DaoFood;
import entity.Food;
import entity.Item;
import entity.Type;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestFood {
    private ImplDaoFood daoFood;

    @BeforeAll
    public void init() {
        daoFood = new DaoFood();
    }

    //Thêm một món ăn mới. Trong đó, mã số của món phải bắt đầu là F và theo sau ít
    //nhất 3 ký số

    //Test case 1: Mã số của món phải bắt đầu là F và theo sau ít nhất 3 ký số
    @Test
    public void testAddFood1() {
        Type type = Type.APPETIZER;
        Food food = new Food("F112","Pho",20,"Pho Viet Nam",false, type,10, 10);
        Boolean result = daoFood.addFood(food);
        if(result) {
            System.out.println("Test case 1: Passed");
        } else {
            System.out.println("Test case 1: Failed");
        }
    }

    //Test case 2: Mã số của món phải bắt đầu là F và theo sau 2 ky số
    @Test
    public void testAddFood2() {
        Type type = Type.APPETIZER;
        Food food = new Food("F12","Noodle",20,"Pho Viet Nam",false, type,10, 10);
        Boolean result = daoFood.addFood(food);
        if(result) {
            System.out.println("Test case 2: Failed");
        } else {
            System.out.println("Test case 2: Passed");
        }
    }

    @Test
    public void testListFoodAndCost() {
        Map<Food,Double> map=daoFood.listFoodAndCost();
        for(Food food:map.keySet())
        {
            System.out.println(food.getName()+"_"+map.get(food));
        }
    }
    @AfterAll
    public void close() {
        daoFood = null;
    }

}
