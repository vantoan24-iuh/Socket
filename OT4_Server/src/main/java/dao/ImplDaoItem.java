package dao;

import entity.Item;

import java.util.List;

public interface ImplDaoItem {
    public List<Item>  listItems(String supplierName);
}
