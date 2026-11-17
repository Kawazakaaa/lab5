package lab3;
/**
 * Класс, представляющий пациента медицинского учреждения.
 * <p>
 * Наследует {@link Person} и реализует сразу три интерфейса:
 * {@link IIdentifiable}, {@link ITreatable} и {@link IBillable}.
 * Пациент имеет уникальный ID, диагноз и историю назначенных лечений,
 * на основе которой рассчитывается стоимость лечения.
 * Класс объявлен {@code final}.
 *
 * @author Цаболов Арсен
 * @version 1.0
 * @since 1.0
 * @see Person
 * @see IIdentifiable
 * @see ITreatable
 * @see IBillable
 * @see Doctor
 */
public final class Patient extends Person implements IIdentifiable, ITreatable, IBillable {

    /** Уникальный идентификатор пациента. */
    private final String id;

    /** Текущий диагноз пациента. */
    private String diagnosis;

    /** Массив назначенных лечений (заменяет {@code List} по требованию лабораторной). */
    private final String[] treatments;

    /** Количество реально заполненных элементов в массиве {@link #treatments}. */
    private int treatmentCount;

    /**
     * Создаёт пациента с заданными параметрами.
     *
     * @param name      имя пациента
     * @param age       возраст в годах
     * @param id        уникальный идентификатор
     * @param diagnosis первичный диагноз
     */
    public Patient(String name, int age, String id, String diagnosis) {
        super(name, age);
        this.id = id;
        this.diagnosis = diagnosis;
        this.treatments = new String[100];
        this.treatmentCount = 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() { return id; }

    /**
     * Возвращает текущий диагноз пациента.
     *
     * @return строковое описание диагноза
     */
    public String getDiagnosis() { return diagnosis; }

    /**
     * Устанавливает новый диагноз пациента.
     *
     * @param diagnosis новый диагноз
     */
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    /**
     * Возвращает количество назначенных лечений.
     *
     * @return число добавленных лечений
     */
    public int getTreatmentCount() { return treatmentCount; }

    /**
     * {@inheritDoc}
     * <p>
     * Если массив {@link #treatments} заполнен, новое лечение игнорируется.
     *
     * @param treatment описание лечения
     */
    @Override
    public void receiveTreatment(String treatment) {
        if (treatmentCount < treatments.length) {
            treatments[treatmentCount++] = treatment;
        }
    }

    /**
     * Возвращает копию массива назначенных лечений.
     * <p>
     * Возвращается именно копия, чтобы внешний код не мог изменить
     * внутреннее состояние пациента.
     *
     * @return новый массив, содержащий только добавленные лечения
     */
    public String[] getTreatments() {
        String[] result = new String[treatmentCount];
        System.arraycopy(treatments, 0, result, 0, treatmentCount);
        return result;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Условная стоимость: 1000 рублей за каждое назначенное лечение.
     *
     * @return стоимость лечения в рублях
     */
    @Override
    public double calculateBill() {
        return treatmentCount * 1000.0;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Выводит имя, возраст, ID и диагноз пациента.
     */
    @Override
    public void displayInfo() {
        System.out.println("Пациент: " + name + ", возраст: " + age +
                ", ID: " + id + ", диагноз: " + diagnosis);
    }
}