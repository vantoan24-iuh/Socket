package client;

import entity.Facility;
import entity.Person;

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

            while (true) {
                System.out.println("1. Get maintenance workers");
                System.out.println("2. Update facility");
                System.out.println("3. Count maintenance");
                System.out.println("4. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                dos.writeInt(choice);
                dos.flush();
                switch (choice) {
                    case 1:
                        System.out.print("Enter facility name: ");
                        String facilityName = scanner.next();
                        dos.writeUTF(facilityName);
                        dos.flush();

                        List<Person> people = (List<Person>) ois.readObject();
                        for (Person person : people) {
                            System.out.println(person);
                        }
                        break;
                    case 2:
                        scanner.nextLine();
                        System.out.print("Enter facility id: ");
                        String id = scanner.nextLine();
                        System.out.print("Enter new description: ");
                        String description = scanner.nextLine();
                        System.out.print("Enter new price: ");
                        double price = scanner.nextDouble();
                        scanner = new Scanner(System.in);
                        System.out.print("Enter new status: ");
                        String status = scanner.nextLine();
                        System.out.print("Enter new location: ");
                        String location = scanner.nextLine();

                        dos.writeUTF(id);
                        dos.writeUTF(description);
                        dos.writeDouble(price);
                        dos.writeUTF(status);
                        dos.writeUTF(location);
                        dos.flush();

                        Boolean result = ois.readBoolean();
                        if (result) {
                            System.out.println("Update successfully");
                        } else {
                            System.out.println("Update failed");
                        }
                        break;
                    case 3:
                        Map<Facility,Long> map= (Map<Facility, Long>) ois.readObject();
                        for(Facility f: map.keySet()){
                            System.out.println(f.getName()+" "+map.get(f));
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
