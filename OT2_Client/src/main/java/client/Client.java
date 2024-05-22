package client;

import com.microsoft.sqlserver.jdbc.ISQLServerCallableStatement;
import entity.Admin;
import entity.Post;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (Socket socket = new Socket("LAPTOP-NE7C2S28", 1234);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())) {
            Scanner sc = new Scanner(System.in);

            while (true) {
                System.out.println("1. Danh sach admin dang bai viet");
                System.out.println("2. Danh sach cac bai viet duoc phe duyet");
                System.out.println("3. Danh sach cac bai viet duoc yeu thich nhieu nhat");
                System.out.println("4. Exit");
                int choice = sc.nextInt();
                dos.writeInt(choice);
                dos.flush();

                switch (choice) {
                    case 1:
                        Map<Admin, Long> map = (Map<Admin, Long>) ois.readObject();
                        for (Map.Entry<Admin, Long> entry : map.entrySet()) {
                            System.out.println(entry.getKey() + " - " + entry.getValue());
                        }
                        break;
                    case 2:
                        sc.nextLine();
                        System.out.println("Nhap trang thai: ");
                        String status = sc.nextLine();
                        System.out.println("Nhap nam: ");
                        int year = Integer.parseInt(sc.nextLine());
                        dos.writeUTF(status);
                        dos.flush();
                        dos.writeInt(year);
                        dos.flush();
                        List<Post> list = (List<Post>) ois.readObject();
                        for (Post post : list) {
                            System.out.println(post);
                        }
                        break;
                    case 3:
                        List<Post> list1 = (List<Post>) ois.readObject();
                        for (Post post : list1) {
                            System.out.println(post);
                        }
                        break;
                    case 4:
                        System.exit(0);
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
