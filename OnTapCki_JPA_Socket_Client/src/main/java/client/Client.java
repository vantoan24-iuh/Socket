package client;

import entity.Candidate;
import entity.Certificate;
import entity.Position;

import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Client {
    private Candidate candidate;
    private Position position;

    public static void main(String[] args) {
        try (Socket socket = new Socket("LAPTOP-NE7C2S28", 1234);
             Scanner sc = new Scanner(System.in);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             ObjectInputStream ois = new ObjectInputStream(socket.getInputStream())
        ) {
            while (true) {
                System.out.println("Menu");
                System.out.println("1. Liệt kê danh sách các vị trí công việc khi biết tên vị trí (tìm tương đối) và mức lương khoảng từ, \n" +
                        "kết quả sắp xếp theo tên vị trí công việc");
                System.out.println("2. Liệt kê danh sách các ứng viên và số công ty mà các ứng viên này từng làm");
                System.out.println("3. Tìm danh sách các ứng viên đã làm việc trên một vị trí công việc nào đó có thời gian làm lâu nhất");
                System.out.println("4.  Thêm một ứng viên mới. Trong đó mã số ứng viên phải bắt đầu là C, theo sau ít nhất là 3 ký số.");
                System.out.println("5. Tính số năm làm việc trên một vị trí công việc nào đó khi biết mã số ứng viên");
                System.out.println("6. Liệt kê danh sách các ứng viên và danh sách bằng cấp của từng ứng viên.");
                System.out.println("7. Exit");

                int choice = sc.nextInt();
                dos.writeInt(choice);
                switch (choice) {
                    //a) Liệt kê danh sách các vị trí công việc khi biết tên vị trí (tìm tương đối) và mức lương khoảng từ,
                    //kết quả sắp xếp theo tên vị trí công việc
                    case 1:
                        System.out.println("Danh sách các vị trí công việc khi biết tên vị trí (tìm tương đối) và mức lương khoảng từ: ");
                        System.out.println("Nhập tên vị trí công việc: ");
                        String name = sc.next();
                        System.out.println("Nhập mức lương khoảng từ: ");
                        double minSalary = Double.parseDouble(sc.next());
                        System.out.println("Nhập mức lương khoảng đến: ");
                        double maxSalary = Double.parseDouble(sc.next());

                        dos.writeUTF(name);
                        dos.writeDouble(minSalary);
                        dos.writeDouble(maxSalary);

                        List<Position> list = (List<Position>) ois.readObject();
                        for (Position position : list) {
                            System.out.println(position);
                        }
                        break;
                    //b) Liệt kê danh sách các ứng viên và số công ty mà các ứng viên này từng làm
                    case 2:
                        sc.nextLine();
                        System.out.println("Danh sách các ứng viên và số công ty mà các ứng viên này từng làm: ");
                        Map<Candidate, Long> map = (Map<Candidate, Long>) ois.readObject();
                        for (Map.Entry<Candidate, Long> entry : map.entrySet()) {
                            System.out.println(entry.getKey().getFullName() + " - " + entry.getValue());
                        }
                        break;
                    //c) Tìm danh sách các ứng viên đã làm việc trên một vị trí công việc nào đó có thời gian làm lâu nhất
                    case 3:
                        sc.nextLine();
                        System.out.println("Danh sách các ứng viên và vị trí công việc mà ứng viên đó làm lâu nhất: ");
                        Map<Candidate, Position> map1 = (Map<Candidate, Position>) ois.readObject();
                        for (Map.Entry<Candidate, Position> entry : map1.entrySet()) {
                            System.out.println(entry.getKey().getFullName() + " - " + entry.getValue().getName());
                        }
                        break;
                    //d) Thêm một ứng viên mới. Trong đó mã số ứng viên phải bắt đầu là C, theo sau ít nhất là 3 ký số.
                    case 4:
                        sc.nextLine();
                        System.out.println("Thêm ứng viên mới: ");
                        System.out.println("Nhập mã số ứng viên: ");
                        String id = sc.next();
                        System.out.println("Nhập họ tên ứng viên: ");
                        String fullName = sc.next();
                        System.out.println("Nhập năm sinh: ");
                        int yearOfBirth = Integer.parseInt(sc.next());
                        System.out.println("Nhập giới tính: ");
                        String gender = sc.next();
                        System.out.println("Nhập email: ");
                        String email = sc.next();
                        System.out.println("Nhập số điện thoại: ");
                        String phone = sc.next();
                        System.out.println("Nhập mô tả: ");
                        String description = sc.next();
                        System.out.println("Nhập mã số vị trí công việc: ");
                        String positionId = sc.next();

                        dos.writeUTF(id);
                        dos.writeUTF(fullName);
                        dos.writeInt(yearOfBirth);
                        dos.writeUTF(gender);
                        dos.writeUTF(email);
                        dos.writeUTF(phone);
                        dos.writeUTF(description);
                        dos.writeUTF(positionId);

                        boolean rs = ois.readBoolean();
                        if (rs) {
                            System.out.println("Thêm thành công");
                        } else {
                            System.out.println("Thêm thất bại");
                        }
                        break;
                    //e) Tính số năm làm việc trên một vị trí công việc nào đó khi biết mã số ứng viên
                    case 5:
                        sc.nextLine();
                        System.out.println("Số năm làm việc trên một vị trí công việc nào đó khi biết mã số ứng viên: ");
                        System.out.println("Nhập mã số ứng viên: ");
                        String candidateId = sc.next();

                        dos.writeUTF(candidateId);

                        Map<Position, Integer> map2 = (Map<Position, Integer>) ois.readObject();
                        for (Map.Entry<Position, Integer> entry : map2.entrySet()) {
                            System.out.println(entry.getKey().getName() + " - " + entry.getValue());
                        }

                        break;
                    //f) Liệt kê danh sách các ứng viên và danh sách bằng cấp của từng ứng viên
                    case 6:
                        sc.nextLine();
                        System.out.println("Danh sách các ứng viên và danh sách bằng cấp của từng ứng viên: ");
                        Map<Candidate, Set<Certificate>> map3 = (Map<Candidate, Set<Certificate>>) ois.readObject();
                        for (Map.Entry<Candidate, Set<Certificate>> entry : map3.entrySet()) {
                            System.out.println(entry.getKey().getId() + " - " + entry.getKey().getFullName());
                            for (Certificate certificate : entry.getValue()) {
                                System.out.println("\t" + certificate.getName());
                            }
                        }
                        break;
                    //exit
                    case 7:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
