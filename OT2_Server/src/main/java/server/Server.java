package server;

import dao.DaoAdmin;
import dao.DaoPost;
import dao.imp.ImpDaoAdmin;
import dao.imp.ImpDaoPost;
import entity.Admin;
import entity.Post;
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
        try(ServerSocket serverSocket=new ServerSocket(1234)){
            System.out.println("Connected to server ...");
            while(true){
                Socket socket=serverSocket.accept();
                Server server=new Server();
                System.out.println("Connected to client: "+socket.getInetAddress().getHostName());
                new Thread(server.new Handle(socket)).start();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public class Handle implements Runnable{
        private Socket socket;
        private EntityManager em;
        private EntityTransaction et;
        private ImpDaoPost daoPost;
        private ImpDaoAdmin daoAdmin;
        public Handle(Socket socket){
            this.socket=socket;
            daoAdmin=new DaoAdmin();
            daoPost=new DaoPost();
            em= Persistence.createEntityManagerFactory("JPA_SQL").createEntityManager();
            et=em.getTransaction();
        }
        @Override
        public void run() {
            try {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());

                while(true){
                    int choice=dis.readInt();
                    switch (choice){
                        case 1:
                            Map<Admin,Long> map=daoAdmin.listAdminsAndNumberOfPosts();
                            oos.writeObject(map);
                            oos.flush();
                            break;
                        case 2:
                            String status=dis.readUTF();
                            int year=dis.readInt();

                            List<Post> list=daoPost.listPostsByApprovalStatusAndYear(status,year);
                            oos.writeObject(list);
                            oos.flush();
                            break;
                        case 3:
                            List<Post> list1=daoPost.listMostLikedPosts();
                            oos.writeObject(list1);
                            oos.flush();
                            break;
                        case 4:
                            System.out.println("Client disconnected");
                            break;
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
