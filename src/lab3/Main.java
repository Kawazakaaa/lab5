package lab3;
import java.util.Scanner;

/**
 * Точка входа в программу «Медицинское обслуживание пациентов».
 * <p>
 * Программа работает в интерактивном режиме через меню: пользователь сам
 * выбирает действие — добавить врача, медсестру или пациента, назначить
 * лечащего врача, назначить лечение, изменить диагноз, вывести списки
 * или посчитать стоимость лечения. Выход — по команде меню.
 *
 * @author Цаболов Арсен
 * @version 1.0
 * @since 1.0
 * @see Hospital
 * @see Doctor
 * @see Nurse
 * @see Patient
 */
public class Main {

    /**
     * Создаёт экземпляр класса Main.
     * <p>
     * Явно объявленный конструктор по умолчанию, чтобы Javadoc мог
     * задокументировать его и не выдавать предупреждение.
     */
    public Main() {
        // Конструктор по умолчанию
    }

    /**
     * Главный метод программы.
     *
     * @param args аргументы командной строки (не используются)
     */
    static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital hospital = new Hospital();

        System.out.println("=== Медицинское обслуживание пациентов ===");
        runMenu(scanner, hospital);

        scanner.close();
    }

    /**
     * Главный цикл меню.
     * <p>
     * Выводит список действий и выполняет выбранное, пока пользователь
     * не введёт команду выхода.
     *
     * @param scanner  сканер для чтения ввода
     * @param hospital больница, с которой ведётся работа
     */
    private static void runMenu(Scanner scanner, Hospital hospital) {
        while (true) {
            printMenu();
            int choice = readInt(scanner);
            System.out.println();
            switch (choice) {
                case 1:
                    addDoctor(scanner, hospital);
                    break;
                case 2:
                    addNurse(scanner, hospital);
                    break;
                case 3:
                    addPatient(scanner, hospital);
                    break;
                case 4:
                    assignDoctorToPatient(scanner, hospital);
                    break;
                case 5:
                    prescribeTreatment(scanner, hospital);
                    break;
                case 6:
                    changeDiagnosis(scanner, hospital);
                    break;
                case 7:
                    hospital.listAll();
                    break;
                case 8:
                    showBills(hospital);
                    break;
                case 0:
                    System.out.println("Выход из программы.");
                    return;
                default:
                    System.out.println("Нет такого пункта. Повторите выбор.");
            }
            System.out.println();
        }
    }

    /**
     * Выводит меню доступных действий.
     */
    private static void printMenu() {
        System.out.println("--- Меню ---");
        System.out.println("1. Добавить врача");
        System.out.println("2. Добавить медсестру");
        System.out.println("3. Добавить пациента");
        System.out.println("4. Назначить пациентам лечащего врача");
        System.out.println("5. Назначить лечение пациенту");
        System.out.println("6. Изменить диагноз пациента");
        System.out.println("7. Показать всех сотрудников и пациентов");
        System.out.println("8. Показать стоимость лечения пациентов");
        System.out.println("0. Выход");
        System.out.print("Выбор: ");
    }

    /**
     * Добавляет врача в больницу.
     *
     * @param scanner  сканер для чтения ввода
     * @param hospital больница
     */
    private static void addDoctor(Scanner scanner, Hospital hospital) {
        System.out.println("--- Новый врач ---");
        System.out.print("Имя: ");
        String name = readLine(scanner);
        System.out.print("Возраст: ");
        int age = readInt(scanner);
        String id = askUniqueId(scanner, hospital);
        System.out.print("Специализация: ");
        String spec = readLine(scanner);
        hospital.addStaff(new Doctor(name, age, id, spec));
    }

    /**
     * Добавляет медсестру в больницу.
     *
     * @param scanner  сканер для чтения ввода
     * @param hospital больница
     */
    private static void addNurse(Scanner scanner, Hospital hospital) {
        System.out.println("--- Новая медсестра ---");
        System.out.print("Имя: ");
        String name = readLine(scanner);
        System.out.print("Возраст: ");
        int age = readInt(scanner);
        String id = askUniqueId(scanner, hospital);
        System.out.print("Смена: ");
        String shift = readLine(scanner);
        hospital.addStaff(new Nurse(name, age, id, shift));
    }

    /**
     * Добавляет пациента в больницу.
     *
     * @param scanner  сканер для чтения ввода
     * @param hospital больница
     */
    private static void addPatient(Scanner scanner, Hospital hospital) {
        System.out.println("--- Новый пациент ---");
        System.out.print("Имя: ");
        String name = readLine(scanner);
        System.out.print("Возраст: ");
        int age = readInt(scanner);
        String id = askUniqueId(scanner, hospital);
        System.out.print("Диагноз: ");
        String diagnosis = readLine(scanner);
        hospital.registerPatient(new Patient(name, age, id, diagnosis));
    }

    /**
     * Назначает выбранного врача выбранному пациенту.
     *
     * @param scanner  сканер для чтения ввода
     * @param hospital больница
     */
    private static void assignDoctorToPatient(Scanner scanner, Hospital hospital) {
        System.out.println("--- Назначение лечащего врача ---");
        if (hospital.getStaffCount() == 0 || hospital.getPatientCount() == 0) {
            System.out.println("Сначала добавьте хотя бы одного врача и одного пациента.");
            return;
        }
        listDoctors(hospital);
        System.out.print("ID врача: ");
        Doctor doctor = hospital.findDoctorById(readLine(scanner));
        if (doctor == null) {
            System.out.println("Врач с таким ID не найден.");
            return;
        }
        listPatients(hospital);
        System.out.print("ID пациента: ");
        Patient patient = hospital.findPatientById(readLine(scanner));
        if (patient == null) {
            System.out.println("Пациент с таким ID не найден.");
            return;
        }
        hospital.assignDoctor(patient, doctor);
    }

    /**
     * Назначает лечение выбранному пациенту через выбранного врача.
     *
     * @param scanner  сканер для чтения ввода
     * @param hospital больница
     */
    private static void prescribeTreatment(Scanner scanner, Hospital hospital) {
        System.out.println("--- Назначение лечения ---");
        if (hospital.getStaffCount() == 0 || hospital.getPatientCount() == 0) {
            System.out.println("Сначала добавьте хотя бы одного врача и одного пациента.");
            return;
        }
        listDoctors(hospital);
        System.out.print("ID врача: ");
        Doctor doctor = hospital.findDoctorById(readLine(scanner));
        if (doctor == null) {
            System.out.println("Врач с таким ID не найден.");
            return;
        }
        listPatients(hospital);
        System.out.print("ID пациента: ");
        Patient patient = hospital.findPatientById(readLine(scanner));
        if (patient == null) {
            System.out.println("Пациент с таким ID не найден.");
            return;
        }
        System.out.print("Лечение: ");
        doctor.prescribeTreatment(patient, readLine(scanner));
    }

    /**
     * Изменяет диагноз выбранного пациента.
     *
     * @param scanner  сканер для чтения ввода
     * @param hospital больница
     */
    private static void changeDiagnosis(Scanner scanner, Hospital hospital) {
        System.out.println("--- Изменение диагноза ---");
        if (hospital.getPatientCount() == 0) {
            System.out.println("Сначала добавьте хотя бы одного пациента.");
            return;
        }
        listPatients(hospital);
        System.out.print("ID пациента: ");
        Patient patient = hospital.findPatientById(readLine(scanner));
        if (patient == null) {
            System.out.println("Пациент с таким ID не найден.");
            return;
        }
        System.out.print("Новый диагноз: ");
        patient.setDiagnosis(readLine(scanner));
        System.out.println("Диагноз обновлён.");
    }

    /**
     * Выводит стоимость лечения всех пациентов.
     *
     * @param hospital больница
     */
    private static void showBills(Hospital hospital) {
        System.out.println("--- Стоимость лечения пациентов ---");
        Patient[] patients = hospital.getPatients();
        if (patients.length == 0) {
            System.out.println("Пациентов пока нет.");
            return;
        }
        for (Patient p : patients) {
            System.out.printf("Пациент %s (ID=%s): %.2f руб.%n",
                    p.getName(), p.getId(), p.calculateBill());
        }
    }

    /**
     * Выводит список врачей больницы.
     *
     * @param hospital больница
     */
    private static void listDoctors(Hospital hospital) {
        System.out.println("Врачи:");
        for (Person p : hospital.getStaff()) {
            if (p instanceof Doctor) {
                Doctor d = (Doctor) p;
                System.out.println("  ID=" + d.getId() + " — " + d.getName() +
                        " (" + d.getSpecialization() + ")");
            }
        }
    }

    /**
     * Выводит список пациентов больницы.
     *
     * @param hospital больница
     */

    private static void listPatients(Hospital hospital) {
        System.out.println("Пациенты:");
        for (Patient p : hospital.getPatients()) {
            System.out.println("  ID=" + p.getId() + " — " + p.getName() +
                    " (диагноз: " + p.getDiagnosis() + ")");
        }
    }

    /**
     * Запрашивает у пользователя уникальный ID (среди сотрудников и пациентов).
     *
     * @param scanner  сканер для чтения ввода
     * @param hospital больница для проверки занятости ID
     * @return уникальный ID
     */
    private static String askUniqueId(Scanner scanner, Hospital hospital) {
        while (true) {
            System.out.print("ID: ");
            String id = readLine(scanner);
            if (hospital.isIdTaken(id)) {
                System.out.println("Такой ID уже занят. Введите другой.");
            } else {
                return id;
            }
        }
    }

    /**
     * Читает из консоли целое число с защитой от некорректного ввода.
     *
     * @param scanner сканер для чтения
     * @return корректное целое число
     */
    private static int readInt(Scanner scanner) {
        while (true) {
            try {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) continue;
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Некорректное число. Повторите ввод: ");
            }
        }
    }

    /**
     * Читает из консоли непустую строку.
     *
     * @param scanner сканер для чтения
     * @return непустая строка
     */
    private static String readLine(Scanner scanner) {
        String line = scanner.nextLine().trim();
        while (line.isEmpty()) {
            System.out.print("Пустая строка. Повторите ввод: ");
            line = scanner.nextLine().trim();
        }
        return line;
    }
}