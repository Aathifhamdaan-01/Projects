package encry;


import java.io.*;
import java.util.Scanner;

public class FileEncryptor {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== File Encryption Tool ===");
            System.out.println("1. Encrypt File");
            System.out.println("2. Decrypt File");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> processFile(sc, true);
                case 2 -> processFile(sc, false);
                case 3 -> {
                    System.out.println("Exiting...");
                    return;
                }
                default -> System.out.println("Invalid choice!");
            }
        }
    }

    private static void processFile(Scanner sc, boolean encrypt) {
        try {
            System.out.print("Enter file path: ");
            String filePath = sc.nextLine();
            System.out.print("Enter secret key: ");
            String key = sc.nextLine();

            File inputFile = new File(filePath);
            if (!inputFile.exists()) {
                System.out.println("File not found!");
                return;
            }

            byte[] fileData = readFile(inputFile);
            byte[] resultData = xorEncryptDecrypt(fileData, key);

            String outputFileName = "C:\\Users\\Lenovo\\Documents\\" + (encrypt ? "encrypted_" + inputFile.getName() : "decrypted_" + inputFile.getName());
            File outputFile = new File(outputFileName);
            writeFile(outputFile, resultData);

            System.out.println((encrypt ? "Encrypted" : "Decrypted") + " file saved as: " + outputFileName);

        } catch (Exception e) {
            System.out.println("Link: " + e.getMessage());
        }
    }

    private static byte[] readFile(File file) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();
        return data;
    }

    private static void writeFile(File file, byte[] data) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(data);
        fos.close();
    }

    private static byte[] xorEncryptDecrypt(byte[] data, String key) {
        byte[] keyBytes = key.getBytes();
        byte[] result = new byte[data.length];

        for (int i = 0; i < data.length; i++) {
            result[i] = (byte) (data[i] ^ keyBytes[i % keyBytes.length]);
        }
        return result;
    }
}
