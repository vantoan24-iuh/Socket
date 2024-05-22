package testALL;

import dao.impl.PositionsDao;
import entity.Position;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestPosition {
    private PositionsDao positionsDao;
    @BeforeAll
    public void setUp() {
        positionsDao = new PositionsDao();
    }
    //a) Liệt kê danh sách các vị trí công việc khi biết tên vị trí (tìm tương đối) và mức lương khoảng từ,
    //kết quả sắp xếp theo tên vị trí công việc
    @Test
    public void testListPositions() {
        System.out.println("Danh sách các vị trí công việc khi biết tên vị trí (tìm tương đối) và mức lương khoảng từ: ");
        List<Position> list= positionsDao.listPositions("Analyst", 10000, 20000);
        Assertions.assertNotNull(list);
        Assertions.assertEquals(2, list.size());
//        list.forEach(System.out::println);
    }
    //e) Tính số năm làm việc trên một vị trí công việc nào đó khi biết mã số ứng viên.
    @Test
    public void testListYearsOfExperienceByPositionn() {
        System.out.println("Số năm làm việc trên một vị trí công việc nào đó khi biết mã số ứng viên: ");
        Map<Position, Integer> map = positionsDao.listYearsOfExperienceByPositionn("C101");
        Assertions.assertNotNull(map);
        Assertions.assertEquals(1, map.size());
//        for (Map.Entry<Position, Integer> entry : map.entrySet()) {
//            System.out.println(entry.getKey().getName() + " - " + entry.getValue());
//        }
    }
    @AfterAll
    public void tearDown() {
        positionsDao = null;
    }

}
