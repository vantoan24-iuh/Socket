package test;

import dao.DaoAdmin;
import entity.Admin;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestAdmin {
    private DaoAdmin daoAdmin;

    @BeforeAll
    public void init() {
        daoAdmin = new DaoAdmin();
    }

    @org.junit.jupiter.api.Test
    public void testListAdminsAndNumberOfPosts() {
        Map<Admin, Long> map = daoAdmin.listAdminsAndNumberOfPosts();
        for (Map.Entry<Admin, Long> entry : map.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
    }

    @AfterAll
    public void destroy() {
        daoAdmin = null;
    }
}
