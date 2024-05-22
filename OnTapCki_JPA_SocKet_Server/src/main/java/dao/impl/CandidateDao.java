package dao.impl;

import dao.ImplCandidateDao;
import entity.Candidate;
import entity.Certificate;
import entity.Experience;
import entity.Position;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.util.*;

public class CandidateDao implements ImplCandidateDao {
    private final EntityManager em;
    private final EntityTransaction et;

    public CandidateDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnTapJPA_SocKet_sql");
        em = emf.createEntityManager();
        et = em.getTransaction();
    }

    // b) Liệt kê danh sách các ứng viên và số công ty mà các ứng viên này từng làm
    @Override
    public Map<Candidate, Long> listCadidatesByCompanies() {
        String sql = "select c, COUNT(*) as soLuongcongty from Candidate c join Experience e on c.id=e.candidate.id group by c";
        Map<Candidate, Long> listCandidates = new HashMap<>();
        try {
            et.begin();
            List<Object[]> list = em.createQuery(sql, Object[].class).getResultList();
            for (Object[] objects : list) {
                Candidate candidate = (Candidate) objects[0];
                Long count = (Long) objects[1];
                listCandidates.put(candidate, count);
            }
            et.commit();
            return listCandidates;
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return null;
    }

//    c) Tìm danh sách các ứng viên đã làm việc trên một vị trí công việc nào đó có thời gian làm lâu nhất
    @Override
    public Map<Candidate, Position> listCandidatesWithLongestWorking() {
        String sql="select c,p from Candidate c join Position p on c.position.id=p.id join Experience e on c.id=e.candidate.id order by (YEAR(e.toDate)-YEAR(e.fromDate)) desc";
        Map<Candidate, Position> listCandidates = new HashMap<>();
        try {
            et.begin();
//            List<Object[]> list = em.createQuery(sql,Object[].class).setMaxResults(1).getResultList();
            List<Object[]> list = em.createQuery(sql,Object[].class).getResultList();
            for (Object[] objects : list) {
                Candidate candidate = (Candidate) objects[0];
                Position position = (Position) objects[1];
                listCandidates.put(candidate, position);
            }
            et.commit();
            return listCandidates;
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return null;
    }

    //d) Thêm một ứng viên mới. Trong đó mã số ứng viên phải bắt đầu là C, theo sau ít nhất là 3 ký số.
    @Override
    public boolean addCandidate(Candidate candidate) {
        try {
            et.begin();
            if (candidate.getId().matches("C\\d{3,}"))
                em.persist(candidate);
            else
                return false;
            et.commit();
            return true;
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return false;
    }
//f) Liệt kê danh sách các ứng viên và danh sách bằng cấp của từng ứng viên.
    @Override
    public Map<Candidate, Set<Certificate>> listCadidatesAndCertificates() {
        String sql="select c, ce from Candidate c join Certificate ce on c.id=ce.candidate.id";
        Map<Candidate, Set<Certificate>> listCandidates = new HashMap<>();
        try {
            et.begin();
            List<Object[]> list = em.createQuery(sql,Object[].class).getResultList();
            for (Object[] objects : list) {
                Candidate candidate = (Candidate) objects[0];
                Certificate certificate = (Certificate) objects[1];
                if (listCandidates.containsKey(candidate)){
                    listCandidates.get(candidate).add(certificate);
                }else {
                    Set<Certificate> certificates = new HashSet<>();
                    certificates.add(certificate);
                    listCandidates.put(candidate,certificates);
                }
            }
            et.commit();
            return listCandidates;
        } catch (Exception e) {
            et.rollback();
            e.printStackTrace();
        }
        return null;
    }
}
