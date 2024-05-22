package client;

import entity.Book;

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
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        ) {
            Scanner scanner = new Scanner(System.in);
            while (true){
                System.out.println("1.danh sách các cuốn sách");
                System.out.println("2. Thống kê số cuốn sách được dịch sang ngôn ngữ khác của từng tác giả");
                System.out.println("3. Cập nhật thêm một lượt đánh giá cho một cuốn sách");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                dos.writeInt(choice);
                dos.flush();
                switch (choice){
                    case 1:
                        scanner = new Scanner(System.in);
                        System.out.println("Nhập tên tác giả: ");
                        String author= scanner.nextLine();
                        System.out.println("Nhap rating: ");
                        int rating= scanner.nextInt();

                        dos.writeUTF(author);
                        dos.writeInt(rating);
                        dos.flush();

                        List<Book> bookList= (List<Book>) ois.readObject();
                        for (Book book: bookList){
                            System.out.println(book);
                        }
                        break;
                    case 2:
                        scanner.nextLine();
                        Map<String, Long> map= (Map<String, Long>) ois.readObject();
                        for (Map.Entry<String, Long> entry: map.entrySet()){
                            System.out.println(entry.getKey()+" : "+entry.getValue());
                        }
                        break;
                    case 3:
                        scanner.nextLine();
                        System.out.println("Nhập ma isbn: ");
                        String isbn= scanner.nextLine();
                        System.out.println("Nhập rating: ");
                        int rating1= scanner.nextInt();
                        scanner = new Scanner(System.in);
                        System.out.println("Nhập comment: ");
                        String comment=scanner.nextLine();

                        dos.writeUTF(isbn);
                        dos.writeInt(rating1);
                        dos.writeUTF(comment);
                        dos.flush();

                        Boolean rs= ois.readBoolean();
                        if (rs){
                            System.out.println("Cập nhật thành công");
                        }else {
                            System.out.println("Cập nhật thất bại");

                        }
                        break;
                    case 4:
                        System.exit(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
