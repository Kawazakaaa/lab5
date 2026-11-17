package lab3;
/**
 * Класс, представляющий врача медицинского учреждения.
 * <p>
 * Наследует {@link Person} и реализует {@link IIdentifiable}. Врач имеет
 * уникальный идентификатор и специализацию, а также умеет назначать лечение
 * пациентам через метод {@link #prescribeTreatment(Patient, String)}.
 * Класс объявлен {@code final} — наследование запрещено.
 *
 * @author Цаболов Арсен
 * @version 1.0
 * @since 1.0
 * @see Person
 * @see IIdentifiable
 * @see Patient
 */
public final class Doctor extends Person implements IIdentifiable {

    /** Уникальный идентификатор врача. */
    private final String id;

    /** Специализация врача (например, «Терапевт», «Кардиолог»). */
    private String specialization;

    /**
     * Создаёт врача с заданными параметрами.
     *
     * @param name           имя врача
     * @param age            возраст врача в годах
     * @param id             уникальный идентификатор
     * @param specialization специализация врача
     */
    public Doctor(String name, int age, String id, String specialization) {
        super(name, age);
        this.id = id;
        this.specialization = specialization;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() { return id; }

    /**
     * Возвращает специализацию врача.
     *
     * @return строковое название специализации
     */
    public String getSpecialization() { return specialization; }

    /**
     * Устанавливает новую специализацию врача.
     *
     * @param specialization новая специализация
     */
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    /**
     * Назначает пациенту лечение.
     * <p>
     * Внутри вызывает {@link Patient#receiveTreatment(String)}, после чего
     * выводит сообщение о назначении в стандартный поток вывода.
     *
     * @param patient   пациент, которому назначается лечение
     * @param treatment текстовое описание лечения
     * @see Patient#receiveTreatment(String)
     */
    public void prescribeTreatment(Patient patient, String treatment) {
        patient.receiveTreatment(treatment);
        System.out.println("Врач " + name + " (" + specialization + ") назначил лечение пациенту "
                + patient.getName() + ": " + treatment);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Выводит имя, возраст, ID и специализацию врача.
     */
    @Override
    public void displayInfo() {
        System.out.println("Врач: " + name + ", возраст: " + age +
                ", ID: " + id + ", специализация: " + specialization);
    }
}