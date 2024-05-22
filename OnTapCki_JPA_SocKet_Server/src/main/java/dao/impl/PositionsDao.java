package dao.impl;

import dao.ImplPositionsDao;
import entity.Position;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PositionsDao implements ImplPositionsDao {
    private final EntityManager em;
    private final EntityTransaction et;
    public PositionsDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnTapJPA_SocKet_sql");
        em = emf.createEntityManager();
        et = em.getTransaction();
    }

    //a) Liệt kê danh sách các vị trí công việc khi biết tên vị trí (tìm tương đối) và mức lương khoảng từ,
    //--kết quả sắp xếp theo tên vị trí công việc.
    @Override
    public List<Position> listPositions(String name, double salaryFrom, double salaryTo) {
        String sql="select p from Position p where p.name like :name and p.salary >= :salaryFrom and p.salary <= :salaryTo order by p.name asc";
        List<Position> listPositions = new ArrayList<>();
        try {
            et.begin();
           listPositions=em.createQuery(sql,Position.class)
                   .setParameter("name","%"+name+"%")
                   .setParameter("salaryFrom",salaryFrom)
                   .setParameter("salaryTo",salaryTo)
                   .getResultList();
            et.commit();
            return listPositions;
        }catch (Exception e){
            et.rollback();
            e.printStackTrace();
        }
        return null;
    }

    //e) Tính số năm làm việc trên một vị trí công việc nào đó khi biết mã số ứng viên
    // dung cast (Bằng cách sử dụng `CASTCAST(... AS INTEGER)trong SQL của bạn Integer, bạn có thể chuyển đổi một giá trị số thực thành một số nguyên. ) tu do chuyen doi nam thanh so nguyen
    @Override
    public Map<Position, Integer> listYearsOfExperienceByPositionn(String candidateID) {
        String sql="select p, cast(SUM(YEAR(e.toDate)-YEAR(e.fromDate)) as integer ) as soNamLamViec from Position p join Candidate c on c.position.id=p.id join Experience e on c.id=e.candidate.id where c.id =:candidateID group by p";
        Map<Position, Integer> listYearsOfExperience = new HashMap<>();
        try {
            et.begin();
            List<Object[]> list = em.createQuery(sql,Object[].class)
                    .setParameter("candidateID",candidateID)
                    .getResultList();

            list.forEach(objects -> {
                Position position = (Position) objects[0];
                Integer soNamLamViec = (Integer) objects[1];
                listYearsOfExperience.put(position, soNamLamViec);
            });
            et.commit();
            return listYearsOfExperience;
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Position getFindById(String id) {
        try {
            et.begin();
            Position position = em.find(Position.class, id);
            et.commit();
            return position;
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return null;
    }
}
