import dao.ImplDaoItem;
import dao.impl.DaoItem;
import entity.Item;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestItem {
    private ImplDaoItem daoItem;

    @BeforeAll
    public void init() {
        daoItem = new DaoItem();
    }

    //Liệt kê danh sách mặt hàng là món đặt biệt của nhà hàng mà có sử dụng nguyên
    //liệu được nhập từ nhà cung cấp nào đó khi biết tên nhà cung cấp (tìm tương đối, không phân biệt
    //chữ thường hoa).
    @Test
    public void testListItems() {
        String supplierName = "food";
        List<Item> listItem = daoItem.listItems(supplierName);
        for (Item item : listItem) {
            System.out.println(item);
        }
    }

    @AfterAll
    public void close() {
        daoItem = null;
    }
}
