package lab3;
/**
 * Класс, представляющий больницу и управляющий сотрудниками и пациентами.
 * <p>
 * Хранит сотрудников в массиве {@link Person} (полиморфизм: одновременно
 * {@link Doctor} и {@link Nurse}) и пациентов в массиве {@link Patient}.
 * Обеспечивает регистрацию, поиск и вывод списков, а также проверку
 * уникальности идентификаторов при добавлении.
 * Класс объявлен {@code final}.
 *
 * @author Цаболов Арсен
 * @version 1.0
 * @since 1.0
 * @see Person
 * @see Doctor
 * @see Nurse
 * @see Patient
 * @see IIdentifiable
 */
public final class Hospital {

    /** Массив пациентов больницы. */
    private final Patient[] patients;

    /** Количество зарегистрированных пациентов. */
    private int patientCount;

    /** Массив сотрудников больницы (врачи и медсёстры). */
    private final Person[] staff;

    /** Количество добавленных сотрудников. */
    private int staffCount;

    /**
     * Создаёт пустую больницу.
     * <p>
     * Внутренние массивы рассчитаны на 100 элементов каждый.
     */
    public Hospital() {
        this.patients = new Patient[100];
        this.patientCount = 0;
        this.staff = new Person[100];
        this.staffCount = 0;
    }

    /**
     * Возвращает копию массива зарегистрированных пациентов.
     *
     * @return новый массив, содержащий только добавленных пациентов
     */
    public Patient[] getPatients() {
        Patient[] result = new Patient[patientCount];
        System.arraycopy(patients, 0, result, 0, patientCount);
        return result;
    }

    /**
     * Возвращает копию массива сотрудников больницы.
     *
     * @return новый массив, содержащий только добавленных сотрудников
     */
    public Person[] getStaff() {
        Person[] result = new Person[staffCount];
        System.arraycopy(staff, 0, result, 0, staffCount);
        return result;
    }

    /**
     * Возвращает количество зарегистрированных пациентов.
     *
     * @return число пациентов
     */
    public int getPatientCount() { return patientCount; }

    /**
     * Возвращает количество сотрудников больницы.
     *
     * @return число сотрудников
     */
    public int getStaffCount() { return staffCount; }

    /**
     * Проверяет, занят ли указанный идентификатор среди сотрудников.
     *
     * @param id проверяемый идентификатор
     * @return {@code true}, если сотрудник с таким ID уже есть; иначе {@code false}
     */
    private boolean isStaffIdTaken(String id) {
        for (int i = 0; i < staffCount; i++) {
            if (staff[i] instanceof IIdentifiable) {
                if (((IIdentifiable) staff[i]).getId().equals(id)) return true;
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
        for (int i = 0; i < patientCount; i++) {
            if (patients[i].getId().equals(id)) return true;
        }
        return false;
    }

    /**
     * Проверяет, занят ли указанный идентификатор среди сотрудников или пациентов.
     * <p>
     * Единая проверка уникальности ID для всего учреждения.
     *
     * @param id проверяемый идентификатор
     * @return {@code true}, если объект с таким ID уже есть; иначе {@code false}
     */
    public boolean isIdTaken(String id) {
        return isStaffIdTaken(id) || isPatientIdTaken(id);
    }

    /**
     * Добавляет сотрудника в больницу с проверкой уникальности ID.
     *
     * @param p сотрудник (врач или медсестра)
     * @return {@code true}, если сотрудник добавлен; {@code false} — если ID занят
     *         или массив заполнен
     * @see IIdentifiable
     */
    public boolean addStaff(Person p) {
        if (p instanceof IIdentifiable) {
            String id = ((IIdentifiable) p).getId();
            if (isStaffIdTaken(id)) {
                System.out.println("Ошибка: сотрудник с ID " + id + " уже существует.");
                return false;
            }
        }
        if (staffCount < staff.length) {
            staff[staffCount++] = p;
            System.out.println("Добавлен сотрудник: " + p.getName() + " (ID=" +
                    (p instanceof IIdentifiable ? ((IIdentifiable) p).getId() : "-") + ")");
            return true;
        }
        System.out.println("Ошибка: массив сотрудников заполнен.");
        return false;
    }

    /**
     * Регистрирует пациента в больнице с проверкой уникальности ID.
     *
     * @param p пациент
     * @return {@code true}, если пациент зарегистрирован; {@code false} — если
     *         ID занят или массив заполнен
     */
    public boolean registerPatient(Patient p) {
        if (isPatientIdTaken(p.getId())) {
            System.out.println("Ошибка: пациент с ID " + p.getId() + " уже существует.");
            return false;
        }
        if (patientCount < patients.length) {
            patients[patientCount++] = p;
            System.out.println("Зарегистрирован пациент: " + p.getName() + " (ID=" + p.getId() + ")");
            return true;
        }
        System.out.println("Ошибка: массив пациентов заполнен.");
        return false;
    }

    /**
     * Назначает пациенту лечащего врача.
     * <p>
     * В текущей реализации метод только выводит информацию о назначении.
     *
     * @param patient пациент
     * @param doctor  назначаемый врач
     */
    public void assignDoctor(Patient patient, Doctor doctor) {
        System.out.println("Пациенту " + patient.getName() + " (ID=" + patient.getId() +
                ") назначен врач " + doctor.getName() + " (ID=" + doctor.getId() + ")");
    }

    /**
     * Ищет врача по идентификатору.
     *
     * @param id идентификатор врача
     * @return найденный врач или {@code null}, если не найден
     */
    public Doctor findDoctorById(String id) {
        for (int i = 0; i < staffCount; i++) {
            if (staff[i] instanceof Doctor) {
                Doctor d = (Doctor) staff[i];
                if (d.getId().equals(id)) return d;
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
        for (int i = 0; i < patientCount; i++) {
            if (patients[i].getId().equals(id)) return patients[i];
        }
        return null;
    }

    /**
     * Выводит в стандартный поток вывода список всех сотрудников и пациентов.
     * <p>
     * Использует полиморфный вызов {@link Person#displayInfo()}.
     */
    public void listAll() {
        System.out.println("--- Сотрудники ---");
        for (int i = 0; i < staffCount; i++) staff[i].displayInfo();
        System.out.println("--- Пациенты ---");
        for (int i = 0; i < patientCount; i++) patients[i].displayInfo();
    }
}