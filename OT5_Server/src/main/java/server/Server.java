package server;

import dao.ImplDaoBook;
import dao.ImplDaoReviews;
import dao.impl.DaoBook;
import dao.impl.DaoReviews;
import entity.Book;
import entity.Reviews;
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
        try (ServerSocket serverSocket = new ServerSocket(1234)) {
            System.out.println("Connected to server...");

            while (true) {
                Socket socket = serverSocket.accept();
                Server server = new Server();
                System.out.println("Connected to Client: " + socket.getInetAddress().getHostName());
                new Thread(server.new Handle(socket)).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class Handle implements Runnable {
        private Socket socket;
        private EntityManager em;
        private EntityTransaction et;
        private ImplDaoBook daoBook;
        private ImplDaoReviews daoReviews;

        public Handle(Socket socket) {
            this.socket = socket;
            em = Persistence.createEntityManagerFactory("JPA_SQL").createEntityManager();
            et = em.getTransaction();
            daoBook = new DaoBook();
            daoReviews = new DaoReviews();
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
                            String author = dis.readUTF();
                            int rating = dis.readInt();

                            List<Book> list = daoBook.listRatedBooks(author, rating);
                            oos.writeObject(list);
                            oos.flush();
                            break;
                        case 2:
                            Map<String, Long> map = daoBook.countBooksByAuthor();
                            oos.writeObject(map);
                            oos.flush();
                            break;
                        case 3:
                            Reviews reviews = new Reviews();
                            String isbn = dis.readUTF();
                            int rating1 = dis.readInt();
                            String comment = dis.readUTF();

                            reviews.setRating(rating1);
                            reviews.setComment(comment);
                            reviews.setBook(em.find(Book.class, isbn));

                            Boolean rs = daoReviews.updateReview(isbn,rating1,comment);
                            oos.writeBoolean(rs);
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
