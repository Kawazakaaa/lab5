package lab5;

import java.util.Scanner;

/**
 * Точка входа в программу «Медицинское обслуживание пациентов» (Лабораторная работа №5, Вариант 13).
 * <p>
 * Расширяет интерактивное меню лабораторной работы №3 возможностями работы с:
 * <ul>
 *   <li>Статическим nested-классом {@link Hospital.Statistics};</li>
 *   <li>Внутренним (inner) классом {@link Patient.MedicalRecord};</li>
 *   <li>Локальным классом {@code AssignmentHelper} в методе {@link Hospital#AssignDoctor(Patient, Person)};</li>
 *   <li>Анонимным классом {@link IBillable} в методе {@link Hospital#generateAnonymousBill(Patient)};</li>
 *   <li>Обобщениями {@link Hospital}{@code <T extends Person>} и {@link ICaseManagement}{@code <T>};</li>
 *   <li>Набором автоматических тестов для проверки всех пунктов задания.</li>
 * </ul>
 *
 * @author Цаболов Арсен
 * @version 2.0
 * @since 1.0
 * @see Hospital
 * @see Doctor
 * @see Nurse
 * @see Patient
 * @see ICaseManagement
 */
public class Main {

    /**
     * Создаёт экземпляр класса Main.
     */
    public Main() {
        // Конструктор по умолчанию
    }

    /**
     * Главный метод программы.
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Hospital<Person> hospital = new Hospital<>();

        System.out.println("=== Медицинское обслуживание пациентов (Лабораторная №5, Вариант 13) ===");
        runMenu(scanner, hospital);

        scanner.close();
    }

    /**
     * Главный цикл меню.
     *
     * @param scanner  сканер для чтения ввода
     * @param hospital больница, с которой ведётся работа
     */
    private static void runMenu(Scanner scanner, Hospital<Person> hospital) {
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
                    showBillsWithDiscount(scanner, hospital);
                    break;
                case 9:
                    showHospitalStatistics(scanner, hospital);
                    break;
                case 10:
                    managePatientMedicalRecord(scanner, hospital);
                    break;
                case 11:
                    manageCases(scanner, hospital);
                    break;
                case 12:
                    runDemonstrationAndTests();
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
        System.out.println("4. Назначить пациенту врача (локальный класс AssignmentHelper)");
        System.out.println("5. Назначить лечение пациенту");
        System.out.println("6. Изменить диагноз пациента");
        System.out.println("7. Показать всех сотрудников и пациентов");
        System.out.println("8. Рассчитать счёт со скидкой (анонимный класс IBillable)");
        System.out.println("9. Статистика больницы (статический класс Hospital.Statistics)");
        System.out.println("10. Медкарта пациента (внутренний класс Patient.MedicalRecord)");
        System.out.println("11. Управление случаями ICaseManagement (MedicalReport / TreatmentPlan)");
        System.out.println("12. Запустить готовую демонстрацию и автотесты (Вариант 13)");
        System.out.println("0. Выход");
        System.out.print("Выбор: ");
    }

    /**
     * Добавляет врача в больницу.
     */
    private static void addDoctor(Scanner scanner, Hospital<Person> hospital) {
        System.out.println("--- Новый врач ---");
        System.out.print("Имя: ");
        String name = readLine(scanner);
        System.out.print("Возраст: ");
        int age = readInt(scanner);
        String id = askUniqueId(scanner, hospital);
        System.out.print("Специализация: ");
        String spec = readLine(scanner);
        hospital.addStaff(new Doctor(name, age, id, spec, true));
    }

    /**
     * Добавляет медсестру в больницу.
     */
    private static void addNurse(Scanner scanner, Hospital<Person> hospital) {
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
     */
    private static void addPatient(Scanner scanner, Hospital<Person> hospital) {
        System.out.println("--- Новый пациент ---");
        System.out.print("Имя: ");
        String name = readLine(scanner);
        System.out.print("Возраст: ");
        int age = readInt(scanner);
        String id = askUniqueId(scanner, hospital);
        System.out.print("Диагноз: ");
        String diagnosis = readLine(scanner);
        System.out.print("Количество дней в больнице: ");
        int days = readInt(scanner);
        System.out.print("Стоимость одного дня (руб.): ");
        double rate = readDouble(scanner);
        hospital.registerPatient(new Patient(name, age, id, diagnosis, days, rate, true));
    }

    /**
     * Назначает выбранного врача выбранному пациенту с проверкой через локальный класс AssignmentHelper.
     */
    private static void assignDoctorToPatient(Scanner scanner, Hospital<Person> hospital) {
        System.out.println("--- Назначение лечащего врача ---");
        if (hospital.getStaffCount() == 0 || hospital.getPatientCount() == 0) {
            System.out.println("Сначала добавьте хотя бы одного сотрудника и одного пациента.");
            return;
        }
        listDoctors(hospital);
        System.out.print("ID врача (или сотрудника): ");
        Person staffMember = hospital.findStaffById(readLine(scanner));
        if (staffMember == null) {
            System.out.println("Сотрудник с таким ID не найден.");
            return;
        }
        listPatients(hospital);
        System.out.print("ID пациента: ");
        Patient patient = hospital.findPatientById(readLine(scanner));
        if (patient == null) {
            System.out.println("Пациент с таким ID не найден.");
            return;
        }
        hospital.AssignDoctor(patient, staffMember);
    }

    /**
     * Назначает лечение выбранному пациенту через выбранного врача.
     */
    private static void prescribeTreatment(Scanner scanner, Hospital<Person> hospital) {
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
        System.out.print("Лечение (лекарство/процедура): ");
        doctor.prescribeTreatment(patient, readLine(scanner));
    }

    /**
     * Изменяет диагноз выбранного пациента.
     */
    private static void changeDiagnosis(Scanner scanner, Hospital<Person> hospital) {
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
     * Демонстрирует расчёт счёта через анонимный класс IBillable с применением скидки.
     */
    private static void showBillsWithDiscount(Scanner scanner, Hospital<Person> hospital) {
        System.out.println("--- Стоимость лечения пациентов (анонимный класс IBillable) ---");
        Patient[] patients = hospital.getPatients();
        if (patients.length == 0) {
            System.out.println("Пациентов пока нет.");
            return;
        }
        System.out.print("Введите процент скидки (0 - 100): ");
        double discount = readDouble(scanner);
        for (Patient p : patients) {
            IBillable bill = hospital.generateAnonymousBill(p);
            double raw = bill.calculateBill();
            double discounted = bill.applyDiscount(discount);
            System.out.printf("Пациент %s (ID=%s): без скидки = %.2f руб., со скидкой %.1f%% = %.2f руб.%n",
                    p.getName(), p.getId(), raw, discount, discounted);
        }
    }

    /**
     * Выводит статистику больницы с помощью статического nested-класса Hospital.Statistics.
     */
    private static void showHospitalStatistics(Scanner scanner, Hospital<Person> hospital) {
        System.out.println("--- Статистика больницы (Hospital.Statistics) ---");
        System.out.println("Общее количество пациентов: " + Hospital.Statistics.getTotalPatients(hospital));
        System.out.printf("Средний возраст пациентов: %.2f лет%n", Hospital.Statistics.getAverageAge(hospital));
        System.out.print("Введите диагноз для подсчёта пациентов: ");
        String diag = readLine(scanner);
        int count = Hospital.Statistics.getPatientsCountByDiagnosis(hospital, diag);
        System.out.println("Количество пациентов с диагнозом «" + diag + "»: " + count);
    }

    /**
     * Демонстрирует работу с внутренним классом Patient.MedicalRecord.
     */
    private static void managePatientMedicalRecord(Scanner scanner, Hospital<Person> hospital) {
        System.out.println("--- Медкарта пациента (Patient.MedicalRecord) ---");
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
        Patient.MedicalRecord record = patient.getMedicalRecord();
        record.printRecord();

        System.out.print("Обновить дату последнего посещения врача: ");
        record.setLastVisitDate(readLine(scanner));
        System.out.print("Добавить лекарство в медкарту: ");
        record.addMedication(readLine(scanner));
        System.out.print("Дополнительная медицинская информация: ");
        record.setAdditionalInfo(readLine(scanner));

        System.out.println("Обновлённая медкарта:");
        record.printRecord();
    }

    /**
     * Демонстрирует работу с обобщённым интерфейсом ICaseManagement<T> у Doctor и Patient.
     */
    private static void manageCases(Scanner scanner, Hospital<Person> hospital) {
        System.out.println("--- Управление случаями (ICaseManagement<T>) ---");
        System.out.println("1. Добавить и найти MedicalReport у врача");
        System.out.println("2. Добавить и найти TreatmentPlan у пациента");
        System.out.print("Выбор: ");
        int sub = readInt(scanner);
        if (sub == 1) {
            listDoctors(hospital);
            System.out.print("ID врача: ");
            Doctor doc = hospital.findDoctorById(readLine(scanner));
            if (doc == null) {
                System.out.println("Врач не найден.");
                return;
            }
            System.out.print("ID отчёта: ");
            String repId = readLine(scanner);
            System.out.print("Имя пациента: ");
            String patName = readLine(scanner);
            System.out.print("Заключение: ");
            String conclusion = readLine(scanner);
            doc.addCase(new MedicalReport(repId, patName, conclusion));
            System.out.println("Сохранено: " + doc.getCase(repId));
        } else if (sub == 2) {
            listPatients(hospital);
            System.out.print("ID пациента: ");
            Patient pat = hospital.findPatientById(readLine(scanner));
            if (pat == null) {
                System.out.println("Пациент не найден.");
                return;
            }
            System.out.print("ID плана лечения: ");
            String planId = readLine(scanner);
            System.out.print("Описание плана: ");
            String desc = readLine(scanner);
            System.out.print("Длительность (дней): ");
            int days = readInt(scanner);
            pat.addCase(new TreatmentPlan(planId, desc, days));
            System.out.println("Сохранено: " + pat.getCase(planId));
        }
    }

    /**
     * Запускает полную демонстрацию всех 4 типов nested-классов, обобщений и модульных тестов.
     */
    public static void runDemonstrationAndTests() {
        System.out.println("=== ДЕМОНСТРАЦИЯ И ТЕСТИРОВАНИЕ (ВАРИАНТ 13) ===");

        Hospital.clearStaticRegistry();
        Hospital<Doctor> docHospital = new Hospital<>();

        Doctor d1 = new Doctor("Иванов И.И.", 45, "D1", "Терапевт", true);
        Doctor d2 = new Doctor("Петрова А.С.", 39, "D2", "Кардиолог", false); // занята
        docHospital.addStaff(d1);
        docHospital.addStaff(d2);

        Patient p1 = new Patient("Сидоров П.П.", 30, "P1", "Грипп", 5, 2000.0, true);
        Patient p2 = new Patient("Козлова М.В.", 50, "P2", "Гипертония", 10, 3000.0, true);
        Patient p3 = new Patient("Орлов Д.А.", 40, "P3", "Грипп", 3, 2000.0, false); // выписан
        docHospital.registerPatient(p1);
        docHospital.registerPatient(p2);
        docHospital.registerPatient(p3);

        // 1. Статический nested-класс Hospital.Statistics
        System.out.println("\n[1] Тест Hospital.Statistics (Static Nested Class):");
        int total = Hospital.Statistics.getTotalPatients(docHospital);
        double avgAge = Hospital.Statistics.getAverageAge(docHospital);
        int fluCount = Hospital.Statistics.getPatientsCountByDiagnosis(docHospital, "Грипп");
        System.out.println("Всего пациентов: " + total + " (ожидалось: 3)");
        System.out.println("Средний возраст: " + avgAge + " (ожидалось: 40.0)");
        System.out.println("Пациентов с диагнозом 'Грипп': " + fluCount + " (ожидалось: 2)");
        if (total != 3 || Math.abs(avgAge - 40.0) > 0.001 || fluCount != 2) {
            throw new AssertionError("Ошибка в тесте Hospital.Statistics!");
        }

        // 2. Внутренний nested-класс Patient.MedicalRecord
        System.out.println("\n[2] Тест Patient.MedicalRecord (Inner Class):");
        Patient.MedicalRecord record = p1.getMedicalRecord();
        record.setLastVisitDate("2026-11-17");
        record.addMedication("Осельтамивир");
        record.addMedication("Парацетамол");
        record.setAdditionalInfo("Постельный режим, обильное питьё");
        record.printRecord();
        if (!record.getFullRecordInfo().contains("Сидоров П.П.") || record.getPrescribedMedications().size() != 2) {
            throw new AssertionError("Ошибка в тесте Patient.MedicalRecord!");
        }

        // 3. Локальный nested-класс AssignmentHelper в методе AssignDoctor
        System.out.println("\n[3] Тест AssignmentHelper в AssignDoctor (Local Class):");
        boolean okAssign = docHospital.AssignDoctor(p1, d1);
        boolean failBusy = docHospital.AssignDoctor(p2, d2);
        boolean failNotInHospital = docHospital.AssignDoctor(p3, d1);
        System.out.println("Назначение свободного врача пациенту в больнице: " + okAssign + " (ожидалось: true)");
        System.out.println("Назначение занятого врача: " + failBusy + " (ожидалось: false)");
        System.out.println("Назначение пациенту вне стационара: " + failNotInHospital + " (ожидалось: false)");
        if (!okAssign || failBusy || failNotInHospital) {
            throw new AssertionError("Ошибка в тесте AssignmentHelper!");
        }

        // 4. Анонимный nested-класс IBillable в generateAnonymousBill
        System.out.println("\n[4] Тест generateAnonymousBill (Anonymous Class):");
        d1.prescribeTreatment(p1, "Ингаляции");
        IBillable anonymousBill = docHospital.generateAnonymousBill(p1);
        double billAmount = anonymousBill.calculateBill(); // 5 * 2000 + 1 * 1000 = 11000.0
        double discountedAmount = anonymousBill.applyDiscount(20.0); // 11000 * 0.8 = 8800.0
        System.out.printf("Сумма счёта p1 без скидки: %.2f руб. (ожидалось: 11000,00)%n", billAmount);
        System.out.printf("Сумма счёта p1 со скидкой 20%%: %.2f руб. (ожидалось: 8800,00)%n", discountedAmount);
        if (Math.abs(billAmount - 11000.0) > 0.001 || Math.abs(discountedAmount - 8800.0) > 0.001) {
            throw new AssertionError("Ошибка в тесте generateAnonymousBill!");
        }

        // 5. Обобщения: Hospital<T extends Person> и ICaseManagement<T>
        System.out.println("\n[5] Тест Generics (Hospital<Nurse>, ICaseManagement<MedicalReport>, ICaseManagement<TreatmentPlan>):");
        Hospital<Nurse> nurseHospital = new Hospital<>();
        Nurse nurse = new Nurse("Смирнова Е.В.", 28, "N1", "Дневная");
        nurseHospital.addStaff(nurse);
        nurseHospital.registerPatient(p2);
        boolean nurseAssigned = nurseHospital.AssignDoctor(p2, nurse);
        System.out.println("Назначение медсестры через Hospital<Nurse>: " + nurseAssigned + " (ожидалось: true)");

        MedicalReport report = new MedicalReport("MR-1", p1.getName(), "Состояние стабильное");
        d1.addCase(report);
        System.out.println("Найден отчёт врача по ID 'MR-1': " + d1.getCase("MR-1"));

        TreatmentPlan plan = new TreatmentPlan("TP-1", "Приём противовирусных препаратов 2 раза в день", 5);
        p1.addCase(plan);
        System.out.println("Найден план лечения пациента по ID 'TP-1': " + p1.getCase("TP-1"));

        if (!nurseAssigned || d1.getCase("MR-1") != report || p1.getCase("TP-1") != plan) {
            throw new AssertionError("Ошибка в тесте Generics!");
        }

        System.out.println("\n=== ВСЕ ТЕСТЫ УСПЕШНО ПРОЙДЕНЫ! ===");
    }

    /**
     * Выводит список врачей больницы.
     */
    private static void listDoctors(Hospital<Person> hospital) {
        System.out.println("Сотрудники:");
        for (Person p : hospital.getStaff()) {
            if (p instanceof Doctor) {
                Doctor d = (Doctor) p;
                System.out.println("  [Врач] ID=" + d.getId() + " — " + d.getName() +
                        " (" + d.getSpecialization() + ", свободен: " + (d.isAvailable() ? "да" : "нет") + ")");
            } else if (p instanceof Nurse) {
                Nurse n = (Nurse) p;
                System.out.println("  [Медсестра] ID=" + n.getId() + " — " + n.getName() +
                        " (смена: " + n.getShift() + ")");
            }
        }
    }

    /**
     * Выводит список пациентов больницы.
     */
    private static void listPatients(Hospital<Person> hospital) {
        System.out.println("Пациенты:");
        for (Patient p : hospital.getPatients()) {
            System.out.println("  ID=" + p.getId() + " — " + p.getName() +
                    " (диагноз: " + p.getDiagnosis() + ", в стационаре: " + (p.isInHospital() ? "да" : "нет") + ")");
        }
    }

    /**
     * Запрашивает у пользователя уникальный ID (среди сотрудников и пациентов).
     */
    private static String askUniqueId(Scanner scanner, Hospital<Person> hospital) {
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
     * Читает из консоли вещественное число с защитой от некорректного ввода.
     */
    private static double readDouble(Scanner scanner) {
        while (true) {
            try {
                String line = scanner.nextLine().trim().replace(',', '.');
                if (line.isEmpty()) continue;
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.print("Некорректное число. Повторите ввод: ");
            }
        }
    }

    /**
     * Читает из консоли непустую строку.
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
