import dao.imp.DaoFacility;
import entity.Facility;
import entity.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.Map;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestFacility {
    private DaoFacility daoFacility;
    private EntityManager entityManager;

    @BeforeAll
    public void init(){
        daoFacility = new DaoFacility();
        entityManager = Persistence.createEntityManagerFactory("JPA_SQL").createEntityManager();
    }

    //b) Cap nhat thong tin cua mot co so vat chat , cac thuoc tinh co the cap nhat bao gom: Mo ta, gia, trang thai va vi tri. Trong do: Gia phai nho hon gia tri hien tai va chi cap nhat neu co so vat chat
    //ton tai; cac thuoc tinh khac khong duoc phep cap nhat
    @Test
    public void testUpdateFacility(){
        Facility facility = entityManager.find(Facility.class, "F001");
        facility.setDescription("Phong moi");
        facility.setPrice(1000);
        facility.setStatus(Status.IN_USE);
        facility.setLocation("Floor 1");
        boolean result = daoFacility.updateFaccility(facility);
        if(result){
            System.out.println("Update success");
        }else {
            System.out.println("Update fail");
        }
    }
    //c) Thong ke so luong bao tri tren tung co so vat chat, ket qua sap xep tang dan theo ten
    @Test
    public void testCountMaintenance(){
        Map<Facility,Long> map=daoFacility.countMaintenance();
        for(Facility f: map.keySet()){
            System.out.println(f.getName()+" "+map.get(f));
        }
    }
    @AfterAll
    public void destroy(){
        daoFacility=null;
        entityManager.close();
    }
}
