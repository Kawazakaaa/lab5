package lab3;
/**
 * Класс, представляющий медицинскую сестру.
 * <p>
 * Наследует {@link Person} и реализует {@link IIdentifiable}. Медсестра
 * имеет уникальный идентификатор и рабочую смену, а также может ассистировать
 * врачу при работе с пациентом. Класс объявлен {@code final}.
 *
 * @author Цаболов Арсен
 * @version 1.0
 * @since 1.0
 * @see Person
 * @see IIdentifiable
 * @see Doctor
 * @see Patient
 */
public final class Nurse extends Person implements IIdentifiable {

    /** Уникальный идентификатор медсестры. */
    private final String id;

    /** Рабочая смена (например, «Дневная», «Ночная»). */
    private String shift;

    /**
     * Создаёт медсестру с заданными параметрами.
     *
     * @param name  имя медсестры
     * @param age   возраст в годах
     * @param id    уникальный идентификатор
     * @param shift рабочая смена
     */
    public Nurse(String name, int age, String id, String shift) {
        super(name, age);
        this.id = id;
        this.shift = shift;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() { return id; }

    /**
     * Возвращает рабочую смену медсестры.
     *
     * @return строковое название смены
     */
    public String getShift() { return shift; }

    /**
     * Устанавливает новую рабочую смену.
     *
     * @param shift новая смена
     */
    public void setShift(String shift) { this.shift = shift; }

    /**
     * Ассистирует врачу при работе с пациентом.
     * <p>
     * Метод не изменяет состояние объектов, только выводит информацию
     * о факте ассистирования в стандартный поток вывода.
     *
     * @param doctor  врач, которому ассистирует медсестра
     * @param patient пациент, с которым ведётся работа
     */
    public void assistDoctor(Doctor doctor, Patient patient) {
        System.out.println("Медсестра " + name + " (смена: " + shift + ") ассистирует врачу "
                + doctor.getName() + " при работе с пациентом " + patient.getName());
    }

    /**
     * {@inheritDoc}
     * <p>
     * Выводит имя, возраст, ID и смену медсестры.
     */
    @Override
    public void displayInfo() {
        System.out.println("Медсестра: " + name + ", возраст: " + age +
                ", ID: " + id + ", смена: " + shift);
    }
}