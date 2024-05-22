package dao.imp;

import dao.ImpDaoFacility;
import entity.Facility;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DaoFacility implements ImpDaoFacility {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private EntityTransaction entityTransaction;
    public DaoFacility() {
        entityManagerFactory = Persistence.createEntityManagerFactory("JPA_SQL");
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
    }

    //b) Cap nhat thong tin cua mot co so vat chat , cac thuoc tinh co the cap nhat bao gom: Mo ta, gia, trang thai va vi tri. Trong do: Gia phai nho hon gia tri hien tai va chi cap nhat neu co so vat chat
    //ton tai; cac thuoc tinh khac khong duoc phep cap nhat
    @Override
    public boolean updateFaccility(Facility facilityNewInfor) {
           try{
                entityTransaction.begin();
                Facility facility = entityManager.find(Facility.class, facilityNewInfor.getId());
                if(facility!=null){
                    facility.setDescription(facilityNewInfor.getDescription());
                    if (facilityNewInfor.getPrice() < facility.getPrice()){
                        facility.setPrice(facilityNewInfor.getPrice());
                    }else {
                        System.out.println("Gia moi phai nho hon gia hien ta");
                        return false;
                    }
                    facility.setStatus(facilityNewInfor.getStatus());
                    facility.setLocation(facilityNewInfor.getLocation());
                    entityManager.merge(facility);
                    entityTransaction.commit();
                    return true;
                }
            }
            catch (Exception e){
                entityTransaction.rollback();
                e.printStackTrace();
            }
        return false;
    }

    @Override
    public Map<Facility, Long> countMaintenance() {
        String sql="select f.name,COUNT(*) as soLuongBaoTri from Facility f join Maintenance m on f. id=m.facility.id \n" +
                "group by f.name\n" +
                "order by f.name asc";
        Map<Facility,Long> map =new LinkedHashMap<>();
        try{
            List<Object[]> objects = entityManager.createQuery(sql).getResultList();
            for(Object[] obj: objects){
                Facility facility = new Facility();
                facility.setName(obj[0].toString());
                Long count = Long.parseLong(obj[1].toString());
                map.put(facility,count);
            }
            return map;
        }catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
        return null;
    }
}
