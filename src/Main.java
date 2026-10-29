import java.util.*;

public class Main {

    static ArrayList<String> ids = new ArrayList<>();
    static ArrayList<String> names = new ArrayList<>();
    static ArrayList<Integer> years = new ArrayList<>();
    static Queue<String> queue = new LinkedList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n===== STUDENT REGISTRATION & SERVICE QUEUE =====");
            System.out.println("1. Register student");
            System.out.println("2. Display all records");
            System.out.println("3. Search student");
            System.out.println("4. Bubble Sort records");
            System.out.println("5. Insertion Sort records");
            System.out.println("6. Enqueue student");
            System.out.println("7. Dequeue (serve next student)");
            System.out.println("8. Peek next student");
            System.out.println("9. Display queue");
            System.out.println("0. Exit");
            choice = readInt("Choice: ", 0, 9);
            System.out.println();
            switch (choice) {
                case 1: register(); break;
                case 2: show("STUDENT RECORDS"); break;
                case 3: search(); break;
                case 4: sort(1); break;
                case 5: sort(2); break;
                case 6: enqueue(); break;
                case 7: dequeue(); break;
                case 8: peek(); break;
                case 9: showQueue(); break;
                case 0: System.out.println("Goodbye!"); break;
            }
        } while (choice != 0);
    }

    static int readInt(String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            try {
                int n = Integer.parseInt(sc.nextLine().trim());
                if (n >= min && n <= max) return n;
                System.out.println("Enter a number from " + min + " to " + max + ".");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a whole number.");
            }
        }
    }

    static String readText(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Input cannot be empty.");
        }
    }

    static int find(String id) {
        for (int i = 0; i < ids.size(); i++)
            if (ids.get(i).equalsIgnoreCase(id)) return i;
        return -1;
    }

    static void printRow(int i) {
        System.out.printf("%-12s %-22s Year %d%n", ids.get(i), names.get(i), years.get(i));
    }

    static void register() {
        String id = readText("Student number: ");
        if (find(id) != -1) {
            System.out.println("That student number already exists.");
            return;
        }
        ids.add(id);
        names.add(readText("Name: "));
        years.add(readInt("Year level (1-5): ", 1, 5));
        System.out.println("Student registered.");
    }

    static void show(String title) {
        System.out.println("--- " + title + " ---");
        if (ids.isEmpty()) {
            System.out.println("No records found.");
            return;
        }
        for (int i = 0; i < ids.size(); i++) printRow(i);
    }

    static void search() {
        if (ids.isEmpty()) {
            System.out.println("No records to search.");
            return;
        }
        String key = readText("Enter student number or name: ").toLowerCase();
        boolean found = false;
        for (int i = 0; i < ids.size(); i++) {
            if (ids.get(i).toLowerCase().contains(key) || names.get(i).toLowerCase().contains(key)) {
                printRow(i);
                found = true;
            }
        }
        if (!found) System.out.println("No matching record found.");
    }

    static boolean after(int c, String idA, String nameA, int yearA, String idB, String nameB, int yearB) {
        if (c == 1) return idA.compareToIgnoreCase(idB) > 0;
        if (c == 2) return nameA.compareToIgnoreCase(nameB) > 0;
        return yearA > yearB;
    }

    static void swap(int i, int j) {
        String t = ids.get(i);
        ids.set(i, ids.get(j));
        ids.set(j, t);
        t = names.get(i);
        names.set(i, names.get(j));
        names.set(j, t);
        int y = years.get(i);
        years.set(i, years.get(j));
        years.set(j, y);
    }

    static void bubbleSort(int c) {
        for (int pass = 0; pass < ids.size() - 1; pass++)
            for (int j = 0; j < ids.size() - 1 - pass; j++)
                if (after(c, ids.get(j), names.get(j), years.get(j),
                        ids.get(j + 1), names.get(j + 1), years.get(j + 1)))
                    swap(j, j + 1);
    }

    static void insertionSort(int c) {
        for (int i = 1; i < ids.size(); i++) {
            String id = ids.get(i);
            String name = names.get(i);
            int year = years.get(i);
            int j = i - 1;
            while (j >= 0 && after(c, ids.get(j), names.get(j), years.get(j), id, name, year)) {
                ids.set(j + 1, ids.get(j));
                names.set(j + 1, names.get(j));
                years.set(j + 1, years.get(j));
                j--;
            }
            ids.set(j + 1, id);
            names.set(j + 1, name);
            years.set(j + 1, year);
        }
    }

    static void sort(int algorithm) {
        if (ids.size() < 2) {
            System.out.println("Need at least 2 records to sort.");
            return;
        }
        System.out.println("Sort by: 1. Student number  2. Name  3. Year level");
        int c = readInt("Choice: ", 1, 3);
        show("BEFORE SORTING");
        if (algorithm == 1) bubbleSort(c);
        else insertionSort(c);
        show("AFTER SORTING");
    }

    static void enqueue() {
        if (ids.isEmpty()) {
            System.out.println("No registered students.");
            return;
        }
        int i = find(readText("Student number: "));
        if (i == -1) {
            System.out.println("Student not found.");
        } else if (queue.contains(ids.get(i))) {
            System.out.println(names.get(i) + " is already in the queue.");
        } else {
            queue.add(ids.get(i));
            System.out.println(names.get(i) + " added to the queue. Position: " + queue.size());
        }
    }

    static void dequeue() {
        if (queue.isEmpty()) {
            System.out.println("The queue is empty.");
            return;
        }
        System.out.println("Now serving:");
        printRow(find(queue.poll()));
    }

    static void peek() {
        if (queue.isEmpty()) {
            System.out.println("The queue is empty.");
            return;
        }
        System.out.println("Next in line:");
        printRow(find(queue.peek()));
    }

    static void showQueue() {
        System.out.println("--- WAITING QUEUE (front to back) ---");
        if (queue.isEmpty()) {
            System.out.println("The queue is empty.");
            return;
        }
        int pos = 1;
        for (String id : queue) {
            System.out.print(pos++ + ". ");
            printRow(find(id));
        }
    }
}