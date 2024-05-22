package dao;

import entity.Food;

import java.util.Map;

public interface ImplDaoFood {
    public boolean addFood(Food food);
    public Map<Food,Double> listFoodAndCost();

}
