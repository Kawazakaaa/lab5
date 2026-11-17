package lab5;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий врача медицинского учреждения.
 * <p>
 * Наследует {@link Person} и реализует {@link IIdentifiable}, а также
 * обобщённый интерфейс {@link ICaseManagement} с параметром типа {@link MedicalReport}.
 * Врач имеет уникальный идентификатор, специализацию, статус доступности
 * ({@link #isAvailable()}) и список медицинских отчётов.
 * Класс объявлен {@code final} — наследование запрещено.
 *
 * @author Цаболов Арсен
 * @version 2.0
 * @since 1.0
 * @see Person
 * @see IIdentifiable
 * @see ICaseManagement
 * @see MedicalReport
 * @see Patient
 */
public final class Doctor extends Person implements IIdentifiable, ICaseManagement<MedicalReport> {

    /** Уникальный идентификатор врача. */
    private final String id;

    /** Специализация врача (например, «Терапевт», «Кардиолог»). */
    private String specialization;

    /** Флаг доступности врача для назначения новому пациенту. */
    private boolean available;

    /** Список медицинских отчётов, составленных врачом. */
    private final List<MedicalReport> reports;

    /**
     * Создаёт свободного врача с заданными параметрами.
     *
     * @param name           имя врача
     * @param age            возраст врача в годах
     * @param id             уникальный идентификатор
     * @param specialization специализация врача
     */
    public Doctor(String name, int age, String id, String specialization) {
        this(name, age, id, specialization, true);
    }

    /**
     * Создаёт врача с заданными параметрами и указанием статуса доступности.
     *
     * @param name           имя врача
     * @param age            возраст врача в годах
     * @param id             уникальный идентификатор
     * @param specialization специализация врача
     * @param available      {@code true}, если врач свободен для назначения
     */
    public Doctor(String name, int age, String id, String specialization, boolean available) {
        super(name, age);
        this.id = id;
        this.specialization = specialization;
        this.available = available;
        this.reports = new ArrayList<>();
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
     * Проверяет, свободен ли врач для назначения пациенту.
     *
     * @return {@code true}, если врач свободен; иначе {@code false}
     */
    public boolean isAvailable() { return available; }

    /**
     * Устанавливает статус доступности врача.
     *
     * @param available {@code true}, если врач свободен; иначе {@code false}
     */
    public void setAvailable(boolean available) { this.available = available; }

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
     * Добавляет медицинский отчёт в список дел врача.
     *
     * @param caseItem медицинский отчёт
     */
    @Override
    public void addCase(MedicalReport caseItem) {
        if (caseItem != null) {
            reports.add(caseItem);
        }
    }

    /**
     * Находит медицинский отчёт по его идентификатору.
     *
     * @param caseId идентификатор отчёта
     * @return найденный {@link MedicalReport} или {@code null}, если не найден
     */
    @Override
    public MedicalReport getCase(String caseId) {
        for (MedicalReport report : reports) {
            if (report.getId().equals(caseId)) {
                return report;
            }
        }
        return null;
    }

    /**
     * Возвращает копию списка всех медицинских отчётов врача.
     *
     * @return список отчётов
     */
    public List<MedicalReport> getReports() {
        return new ArrayList<>(reports);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Выводит имя, возраст, ID, специализацию и статус врача.
     */
    @Override
    public void displayInfo() {
        System.out.println("Врач: " + name + ", возраст: " + age +
                ", ID: " + id + ", специализация: " + specialization +
                ", свободен: " + (available ? "да" : "нет"));
    }
}
