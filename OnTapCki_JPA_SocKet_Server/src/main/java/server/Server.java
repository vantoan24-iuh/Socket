package server;

import dao.ImplCandidateDao;
import dao.ImplPositionsDao;
import dao.impl.CandidateDao;
import dao.impl.PositionsDao;
import entity.Candidate;
import entity.Certificate;
import entity.Position;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Server {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Connected to server...");

            while (true) {
                Socket socket = serverSocket.accept();
                Server server = new Server();
                System.out.println("Connected to client: " + socket.getInetAddress().getHostName());
                new Thread(server.new Handler(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class Handler implements Runnable {
        private Socket socket;
        private ImplCandidateDao candidateDao;
        private ImplPositionsDao positionsDao;
        private EntityManager em;
        private EntityTransaction et;

        public Handler(Socket socket) {
            this.socket = socket;
            candidateDao = new CandidateDao();
            positionsDao = new PositionsDao();
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("OnTapJPA_SocKet_sql");
            em = emf.createEntityManager();
            et = em.getTransaction();
        }

        @Override
        public void run() {
            try {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                while (true) {
                    int choice = dis.readInt();
                    switch (choice) {
                        //a) Liệt kê danh sách các vị trí công việc khi biết tên vị trí (tìm tương đối) và mức lương khoảng từ,
                        //kết quả sắp xếp theo tên vị trí công việc
                        case 1:
                            Position position = new Position();
                            String name = dis.readUTF();
                            position.setName(name);
                            double minSalary = dis.readDouble();
                            position.setSalary(minSalary);
                            double maxSalary = dis.readDouble();
                            position.setSalary(maxSalary);
                            List<Position> list = positionsDao.listPositions(name, minSalary, maxSalary);
                            oos.writeObject(list);
                            oos.flush();

                            break;
                        //b) Liệt kê danh sách các ứng viên và số công ty mà các ứng viên này từng làm
                        case 2:
                            Map<Candidate, Long> map = candidateDao.listCadidatesByCompanies();
                            oos.writeObject(map);
                            oos.flush();
                            break;
                        //c) Tìm danh sách các ứng viên đã làm việc trên một vị trí công việc nào đó có thời gian làm lâu nhất
                        case 3:
                            Map<Candidate, Position> map1 = candidateDao.listCandidatesWithLongestWorking();
                            oos.writeObject(map1);
                            oos.flush();
                            break;
                        //d) Thêm một ứng viên mới. Trong đó mã số ứng viên phải bắt đầu là C, theo sau ít nhất là 3 ký số.
                        case 4:
                            Candidate candidate = new Candidate();
                            candidate.setId(dis.readUTF());
                            candidate.setFullName(dis.readUTF());
                            candidate.setYearOfBirth(dis.readInt());
                            candidate.setGender(dis.readUTF());
                            candidate.setEmail(dis.readUTF());
                            candidate.setPhone(dis.readUTF());
                            candidate.setDescription(dis.readUTF());
                            Position position1 = positionsDao.getFindById(dis.readUTF());
                            candidate.setPosition(position1);

                            boolean rs = candidateDao.addCandidate(candidate);
                            oos.writeBoolean(rs);
                            oos.flush();
                            break;
                        //e) Tính số năm làm việc trên một vị trí công việc nào đó khi biết mã số ứng viên
                        case 5:
                            Map<Position, Integer> map2 = positionsDao.listYearsOfExperienceByPositionn(dis.readUTF());
                            oos.writeObject(map2);
                            oos.flush();
                            break;
                        //f) Liệt kê danh sách các ứng viên và danh sách bằng cấp của từng ứng viên
                        case 6:
                            Map<Candidate, Set<Certificate>> map3 = candidateDao.listCadidatesAndCertificates();
                            oos.writeObject(map3);
                            oos.flush();
                            break;
                        //exit
                        case 7:
                            System.out.println("Client disconnected...");
                            break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
