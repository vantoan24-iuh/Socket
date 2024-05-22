package testALL;

import dao.impl.CandidateDao;
import entity.Candidate;
import entity.Certificate;
import entity.Position;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.junit.jupiter.api.*;

import java.util.Map;
import java.util.Set;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestCadidate {
    private CandidateDao candidateDao;
    private EntityManager em;
    private EntityTransaction et;

    @BeforeAll
    public void setUp() {
        candidateDao = new CandidateDao();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnTapJPA_SocKet_sql");
        em = emf.createEntityManager();
        et = em.getTransaction();
    }

//b) Liệt kê danh sách các ứng viên và số công ty mà các ứng viên này từng làm.
    @Test
    public void testListCadidatesByCompanies() {
        System.out.println("Danh sách các ứng viên và số công ty mà các ứng viên này từng làm: ");
        Map<Candidate,Long> map= candidateDao.listCadidatesByCompanies();
        Assertions.assertNotNull(map);
        Assertions.assertEquals(7,map.size());
//        for (Map.Entry<Candidate, Long> entry : map.entrySet()) {
//            System.out.println(entry.getKey().getFullName() + " - " + entry.getValue());
//        }
    }
//c) Tìm danh sách các ứng viên đã làm việc trên một vị trí công việc nào đó có thời gian làm lâu nhất.
    @Test
    public void testListCandidatesWithLongestWorking() {
        System.out.println("Danh sách các ứng viên và vị trí công việc mà ứng viên đó làm lâu nhất: ");
        Map<Candidate, Position> map = candidateDao.listCandidatesWithLongestWorking();
        Assertions.assertNotNull(map);
        Assertions.assertEquals(7, map.size());
//        for (Map.Entry<Candidate, Position> entry : map.entrySet()) {
//            System.out.println(entry.getKey().getFullName() + " - " + entry.getValue().getName());
//        }
    }

//d) Thêm một ứng viên mới. Trong đó mã số ứng viên phải bắt đầu là C, theo sau ít nhất là 3 ký số.
    @Test
    public void testAddCandidate() {
        Candidate candidate = new Candidate();
        Position position = new Position();
        candidate.setId("C113");
        candidate.setFullName("Nguyen Van A");
        candidate.setYearOfBirth(1999);
        candidate.setGender("Male");
        candidate.setEmail("ANguyen@gmail.com");
        candidate.setPhone("0123456789");
        candidate.setDescription("Software Engineer");
        candidate.setPosition(em.find(Position.class, "P102"));

        Assertions.assertNotNull(candidate);
        Assertions.assertTrue(candidateDao.addCandidate(candidate));

//        System.out.println("Thêm ứng viên mới: ");
//        if (candidateDao.addCandidate(candidate)) {
//            System.out.println("Thêm thành công");
//        } else {
//            System.out.println("Thêm thất bại");
//        }
    }

    //f) Liệt kê danh sách các ứng viên và danh sách bằng cấp của từng ứng viên
    @Test
    public void testListCandidatesByDegrees() {
        System.out.println("Danh sách các ứng viên và danh sách bằng cấp của từng ứng viên: ");
        Map<Candidate, Set<Certificate>> map = candidateDao.listCadidatesAndCertificates();
        Assertions.assertNotNull(map);
        Assertions.assertEquals(11, map.size());
//        for (Map.Entry<Candidate, Set<Certificate>> entry : map.entrySet()) {
//            System.out.println(entry.getKey().getId() + " - " + entry.getKey().getFullName());
//            for (Certificate certificate : entry.getValue()) {
//                System.out.println("\t" + certificate.getName());
//            }
//        }
    }
    @AfterAll
    public void tearDown() {
        candidateDao = null;
    }
}
