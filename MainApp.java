import java.util.Scanner;

public class MainApp {

    private static int readInt(Scanner sc, String prompt) {
        while (true) {
            System.out.print(prompt);
            if (sc.hasNextInt()) {
                int value = sc.nextInt();
                sc.nextLine();
                return value;
            }
            System.out.println("Invalid input. Please enter a number.");
            sc.nextLine();
        }
    }

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        KeyService.loadKey();

        while (true) {
            System.out.println("\n1.Register 2.Login 3.Exit");
            int ch = readInt(sc, "Choose option: ");

            if (ch == 1) {
                System.out.print("Username: ");
                String u = sc.next();
                System.out.print("Password: ");
                String p = sc.next();

                AuthService.register(u, p);
            }

            else if (ch == 2) {
                System.out.print("Username: ");
                String u = sc.next();
                System.out.print("Password: ");
                String p = sc.next();

                if (AuthService.login(u, p)) {
                    System.out.println("Login successful!");

                    int opt;
                    do {
                        System.out.println("\n1.Store\n2.View\n3.Search\n4.Delete All\n5.Delete One\n6.Edit\n7.Export\n8.Logout\n");
                        opt = readInt(sc, "Choose option: ");

                        switch (opt) {
                            case 1:
                                System.out.print("Enter file name: ");
                                String fname = sc.next();
                                sc.nextLine(); // clear buffer
                                System.out.print("Enter text: ");
                                String text = sc.nextLine();
                                FileService.store(u, fname, text);
                                break;

                            case 2:
                                FileService.view(u);
                                break;

                            case 3:
                                System.out.print("Keyword: ");
                                String k = sc.next();
                                SearchService.search(u, k);
                                break;

                            case 4:
                                FileService.deleteAll(u);
                                break;
                            case 5:
                                FileService.listFiles(u);
                                System.out.print("Enter file name to delete: ");
                                fname = sc.next();
                                FileService.deleteOne(u, fname);
                                break;
                            case 6:
                                if (!FileService.listFiles(u)) break;
                                System.out.print("Enter file name to edit: ");
                                String editName = sc.next();
                                sc.nextLine();
                                System.out.print("Enter new content: ");
                                String newText = sc.nextLine();
                                FileService.editFile(u, editName, newText);
                                break;

                            case 7:
                                if (!FileService.listFiles(u)) break;
                                System.out.print("Enter file name to export: ");
                                String exportName = sc.next();
                                FileService.exportFile(u, exportName);
                                break;
                            case 8:
                                System.out.println("Logged out.");
                                break;
                            default:
                                System.out.println("Invalid option!");
                        }

                    } while (opt != 8);
                } else {
                    System.out.println("Invalid login!");
                }
            }

            else if (ch == 3) {
                System.out.println("Exiting app.");
                break;
            } else {
                System.out.println("Invalid option! Please choose 1, 2 or 3.");
            }
        }

        sc.close();
    }
}
