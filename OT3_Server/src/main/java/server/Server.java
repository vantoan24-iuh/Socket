package server;

import dao.imp.DaoFacility;
import dao.imp.DaoPerson;
import dao.ImpDaoFacility;
import dao.ImpDaoPerson;
import entity.Facility;
import entity.Person;
import entity.Status;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;

import java.io.DataInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Map;

public class Server {

    public static void main(String[] args) {
        try (ServerSocket serversocket = new ServerSocket(1234)) {
            System.out.println("Connected to server...");

            while (true) {
                Socket socket = serversocket.accept();
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
        private EntityManager em;
        private EntityTransaction et;
        private ImpDaoFacility daoFacility;
        private ImpDaoPerson daoPerson;

        public Handler(Socket socket) {
            this.socket = socket;
            em = Persistence.createEntityManagerFactory("JPA_SQL").createEntityManager();
            et = em.getTransaction();
            daoPerson = new DaoPerson();
            daoFacility = new DaoFacility();
        }

        @Override
        public void run() {
            try {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                while (true) {
                    int choice = dis.readInt();
                    switch (choice) {
                        case 1:
                            String facilityName = dis.readUTF();

                            List<Person> people = daoPerson.getMainanceWorkers(facilityName);
                            oos.writeObject(people);
                            oos.flush();
                            break;
                        case 2:
                            Facility facility = new Facility();
                            facility.setId(em.find(Facility.class, dis.readUTF()).getId());
                            facility.setDescription(dis.readUTF());
                            facility.setPrice(dis.readDouble());
                            facility.setStatus(Status.fromString(dis.readUTF()));
                            facility.setLocation(dis.readUTF());

                            boolean result = daoFacility.updateFaccility(facility);
                            oos.writeBoolean(result);
                            oos.flush();
                            break;
                        case 3:
                            Map<Facility,Long> map= daoFacility.countMaintenance();
                            oos.writeObject(map);
                            oos.flush();
                            break;
                        case 4:
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
