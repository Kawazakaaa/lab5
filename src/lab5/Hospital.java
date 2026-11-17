package lab5;

import java.util.ArrayList;
import java.util.List;

/**
 * Обобщённый класс, представляющий больницу и управляющий медицинским персоналом и пациентами.
 * <p>
 * В лабораторной работе №5 класс параметризован типом {@code T extends Person},
 * хранит сотрудников в списке {@code List<T> staff} и включает:
 * <ul>
 *   <li>Статический nested-класс {@link Statistics} для расчёта статистики по пациентам;</li>
 *   <li>Локальный nested-класс {@code AssignmentHelper} внутри метода {@link #AssignDoctor(Patient, Person)};</li>
 *   <li>Анонимный nested-класс, реализующий {@link IBillable}, в методе {@link #generateAnonymousBill(Patient)}.</li>
 * </ul>
 *
 * @param <T> тип медицинского персонала, наследующий {@link Person}
 * @author Цаболов Арсен
 * @version 2.0
 * @since 1.0
 * @see Person
 * @see Doctor
 * @see Nurse
 * @see Patient
 * @see IBillable
 */
public final class Hospital<T extends Person> {

    /** Список пациентов данной больницы. */
    private final List<Patient> patients;

    /** Список медицинского персонала типа {@code T extends Person}. */
    private final List<T> staff;

    /**
     * Статический список всех зарегистрированных пациентов для удобного вызова
     * статических методов {@link Statistics} как со списком/больницей, так и без параметров.
     */
    private static final List<Patient> allPatientsRegistry = new ArrayList<>();

    /**
     * Создаёт пустую больницу.
     */
    public Hospital() {
        this.patients = new ArrayList<>();
        this.staff = new ArrayList<>();
    }

    /**
     * Статический nested-класс для вычисления статистических показателей больницы.
     */
    public static class Statistics {

        /**
         * Вычисляет общее количество пациентов в указанной больнице.
         *
         * @param hospital объект больницы
         * @return общее количество пациентов
         */
        public static int getTotalPatients(Hospital<?> hospital) {
            if (hospital == null) return 0;
            return hospital.patients.size();
        }

        /**
         * Вычисляет общее количество зарегистрированных пациентов.
         *
         * @return общее количество пациентов
         */
        public static int getTotalPatients() {
            return allPatientsRegistry.size();
        }

        /**
         * Вычисляет средний возраст пациентов в указанной больнице.
         *
         * @param hospital объект больницы
         * @return средний возраст пациентов (или {@code 0.0}, если пациентов нет)
         */
        public static double getAverageAge(Hospital<?> hospital) {
            if (hospital == null || hospital.patients.isEmpty()) return 0.0;
            int sum = 0;
            for (Patient p : hospital.patients) {
                sum += p.getAge();
            }
            return (double) sum / hospital.patients.size();
        }

        /**
         * Вычисляет средний возраст всех зарегистрированных пациентов.
         *
         * @return средний возраст пациентов (или {@code 0.0}, если пациентов нет)
         */
        public static double getAverageAge() {
            if (allPatientsRegistry.isEmpty()) return 0.0;
            int sum = 0;
            for (Patient p : allPatientsRegistry) {
                sum += p.getAge();
            }
            return (double) sum / allPatientsRegistry.size();
        }

        /**
         * Вычисляет количество пациентов с заданным диагнозом в указанной больнице.
         *
         * @param hospital  объект больницы
         * @param diagnosis искомый диагноз
         * @return количество пациентов с данным диагнозом
         */
        public static int getPatientsCountByDiagnosis(Hospital<?> hospital, String diagnosis) {
            if (hospital == null || diagnosis == null) return 0;
            int count = 0;
            for (Patient p : hospital.patients) {
                if (diagnosis.equalsIgnoreCase(p.getDiagnosis())) {
                    count++;
                }
            }
            return count;
        }

        /**
         * Вычисляет количество пациентов с заданным диагнозом среди всех зарегистрированных.
         *
         * @param diagnosis искомый диагноз
         * @return количество пациентов с данным диагнозом
         */
        public static int getPatientsCountByDiagnosis(String diagnosis) {
            if (diagnosis == null) return 0;
            int count = 0;
            for (Patient p : allPatientsRegistry) {
                if (diagnosis.equalsIgnoreCase(p.getDiagnosis())) {
                    count++;
                }
            }
            return count;
        }
    }

    /**
     * Очищает статический реестр пациентов (используется перед изолированными тестами).
     */
    public static void clearStaticRegistry() {
        allPatientsRegistry.clear();
    }

    /**
     * Возвращает массив зарегистрированных пациентов (для совместимости с лабораторной №3).
     *
     * @return массив пациентов
     */
    public Patient[] getPatients() {
        return patients.toArray(new Patient[0]);
    }

    /**
     * Возвращает список зарегистрированных пациентов.
     *
     * @return список пациентов
     */
    public List<Patient> getPatientList() {
        return new ArrayList<>(patients);
    }

    /**
     * Возвращает список сотрудников больницы типа {@code T}.
     *
     * @return список сотрудников
     */
    public List<T> getStaffList() {
        return new ArrayList<>(staff);
    }

    /**
     * Возвращает массив сотрудников больницы (для совместимости с лабораторной №3).
     *
     * @return массив сотрудников
     */
    public Person[] getStaff() {
        return staff.toArray(new Person[0]);
    }

    /**
     * Возвращает количество зарегистрированных пациентов.
     *
     * @return число пациентов
     */
    public int getPatientCount() { return patients.size(); }

    /**
     * Возвращает количество сотрудников больницы.
     *
     * @return число сотрудников
     */
    public int getStaffCount() { return staff.size(); }

    /**
     * Проверяет, занят ли указанный идентификатор среди сотрудников.
     *
     * @param id проверяемый идентификатор
     * @return {@code true}, если сотрудник с таким ID уже есть; иначе {@code false}
     */
    private boolean isStaffIdTaken(String id) {
        for (T person : staff) {
            if (person instanceof IIdentifiable) {
                if (((IIdentifiable) person).getId().equals(id)) return true;
            }
        }
        return false;
    }

    /**
     * Проверяет, занят ли указанный идентификатор среди пациентов.
     *
     * @param id проверяемый идентификатор
     * @return {@code true}, если пациент с таким ID уже есть; иначе {@code false}
     */
    private boolean isPatientIdTaken(String id) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) return true;
        }
        return false;
    }

    /**
     * Проверяет, занят ли указанный идентификатор среди сотрудников или пациентов.
     *
     * @param id проверяемый идентификатор
     * @return {@code true}, если объект с таким ID уже есть; иначе {@code false}
     */
    public boolean isIdTaken(String id) {
        return isStaffIdTaken(id) || isPatientIdTaken(id);
    }

    /**
     * Добавляет сотрудника типа {@code T} в больницу с проверкой уникальности ID.
     *
     * @param p сотрудник (например, врач или медсестра)
     * @return {@code true}, если сотрудник добавлен; {@code false} — если ID занят
     */
    public boolean addStaff(T p) {
        if (p == null) return false;
        if (p instanceof IIdentifiable) {
            String id = ((IIdentifiable) p).getId();
            if (isStaffIdTaken(id)) {
                System.out.println("Ошибка: сотрудник с ID " + id + " уже существует.");
                return false;
            }
        }
        staff.add(p);
        System.out.println("Добавлен сотрудник: " + p.getName() + " (ID=" +
                (p instanceof IIdentifiable ? ((IIdentifiable) p).getId() : "-") + ")");
        return true;
    }

    /**
     * Регистрирует пациента в больнице с проверкой уникальности ID.
     *
     * @param p пациент
     * @return {@code true}, если пациент зарегистрирован; {@code false} — если ID занят
     */
    public boolean registerPatient(Patient p) {
        if (p == null) return false;
        if (isPatientIdTaken(p.getId())) {
            System.out.println("Ошибка: пациент с ID " + p.getId() + " уже существует.");
            return false;
        }
        patients.add(p);
        allPatientsRegistry.add(p);
        System.out.println("Зарегистрирован пациент: " + p.getName() + " (ID=" + p.getId() + ")");
        return true;
    }

    /**
     * Назначает медицинского сотрудника типа {@code T} пациенту, используя
     * локальный nested-класс {@code AssignmentHelper} для предварительных проверок.
     *
     * @param patient пациент
     * @param doctor  назначаемый медицинский персонал типа {@code T}
     * @return {@code true}, если проверки пройдены и назначение выполнено; иначе {@code false}
     */
    public boolean AssignDoctor(final Patient patient, final T doctor) {

        /**
         * Локальный nested-класс для проверки возможности назначения врача пациенту.
         * Имеет доступ к локальным переменным/параметрам метода AssignDoctor (patient и doctor)
         * и к полям внешнего класса Hospital.
         */
        class AssignmentHelper {

            /**
             * Выполняет проверку перед назначением сотрудника пациенту.
             *
             * @return {@code true}, если пациент находится в больнице и сотрудник свободен
             */
            public boolean canAssign() {
                if (patient == null || doctor == null) {
                    System.out.println("Проверка не пройдена: пациент или сотрудник не указан.");
                    return false;
                }
                if (!patients.contains(patient)) {
                    System.out.println("Проверка не пройдена: пациент " + patient.getName() + " не зарегистрирован в больнице.");
                    return false;
                }
                if (!patient.isInHospital()) {
                    System.out.println("Проверка не пройдена: пациент " + patient.getName() + " не находится в стационаре.");
                    return false;
                }
                if (!staff.contains(doctor)) {
                    System.out.println("Проверка не пройдена: сотрудник " + doctor.getName() + " не числится в штате больницы.");
                    return false;
                }
                if (doctor instanceof Doctor) {
                    Doctor d = (Doctor) doctor;
                    if (!d.isAvailable()) {
                        System.out.println("Проверка не пройдена: врач " + d.getName() + " в данный момент занят.");
                        return false;
                    }
                }
                return true;
            }
        }

        AssignmentHelper helper = new AssignmentHelper();
        if (helper.canAssign()) {
            patient.setAssignedStaff(doctor);
            String staffId = (doctor instanceof IIdentifiable) ? ((IIdentifiable) doctor).getId() : "-";
            System.out.println("Пациенту " + patient.getName() + " (ID=" + patient.getId() +
                    ") назначен сотрудник " + doctor.getName() + " (ID=" + staffId + ")");
            return true;
        }
        return false;
    }

    /**
     * Синоним метода {@link #AssignDoctor(Patient, Person)} в стиле camelCase.
     *
     * @param patient пациент
     * @param doctor  назначаемый сотрудник типа {@code T}
     * @return {@code true}, если назначение выполнено успешно
     */
    public boolean assignDoctor(Patient patient, T doctor) {
        return AssignDoctor(patient, doctor);
    }

    /**
     * Создаёт и возвращает анонимный класс, реализующий интерфейс {@link IBillable},
     * для расчёта стоимости пребывания пациента в больнице и применения скидки.
     *
     * @param patient пациент, для которого формируется счёт
     * @return экземпляр анонимного класса, реализующего {@link IBillable}
     */
    public IBillable generateAnonymousBill(final Patient patient) {
        return new IBillable() {
            @Override
            public double calculateBill() {
                if (patient == null) return 0.0;
                return patient.getDaysInHospital() * patient.getDailyRate()
                        + patient.getTreatmentCount() * 1000.0;
            }

            @Override
            public double applyDiscount(double discountPercentage) {
                double baseAmount = calculateBill();
                return baseAmount * (1.0 - discountPercentage / 100.0);
            }
        };
    }

    /**
     * Ищет врача по идентификатору.
     *
     * @param id идентификатор врача
     * @return найденный врач или {@code null}, если не найден
     */
    public Doctor findDoctorById(String id) {
        for (T person : staff) {
            if (person instanceof Doctor) {
                Doctor d = (Doctor) person;
                if (d.getId().equals(id)) return d;
            }
        }
        return null;
    }

    /**
     * Ищет сотрудника типа {@code T} по идентификатору.
     *
     * @param id идентификатор сотрудника
     * @return найденный сотрудник или {@code null}, если не найден
     */
    public T findStaffById(String id) {
        for (T person : staff) {
            if (person instanceof IIdentifiable) {
                if (((IIdentifiable) person).getId().equals(id)) return person;
            }
        }
        return null;
    }

    /**
     * Ищет пациента по идентификатору.
     *
     * @param id идентификатор пациента
     * @return найденный пациент или {@code null}, если не найден
     */
    public Patient findPatientById(String id) {
        for (Patient patient : patients) {
            if (patient.getId().equals(id)) return patient;
        }
        return null;
    }

    /**
     * Выводит в стандартный поток вывода список всех сотрудников и пациентов.
     */
    public void listAll() {
        System.out.println("--- Сотрудники ---");
        for (T person : staff) person.displayInfo();
        System.out.println("--- Пациенты ---");
        for (Patient patient : patients) patient.displayInfo();
    }
}
