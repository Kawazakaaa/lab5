package lab5;

/**
 * Класс плана лечения, которым управляет {@link Patient}
 * через обобщённый интерфейс {@link ICaseManagement}.
 *
 * @author Цаболов Арсен
 * @version 1.0
 * @since 2.0
 * @see Patient
 * @see ICaseManagement
 */
public final class TreatmentPlan implements IIdentifiable {

    /** Уникальный идентификатор плана лечения. */
    private final String id;

    /** Описание назначенного плана лечения. */
    private final String description;

    /** Длительность курса лечения в днях. */
    private final int durationDays;

    /**
     * Создаёт план лечения.
     *
     * @param id           уникальный идентификатор плана
     * @param description  описание процедур и режима
     * @param durationDays длительность курса в днях
     */
    public TreatmentPlan(String id, String description, int durationDays) {
        this.id = id;
        this.description = description;
        this.durationDays = durationDays;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() { return id; }

    /**
     * Возвращает описание плана лечения.
     *
     * @return описание лечения
     */
    public String getDescription() { return description; }

    /**
     * Возвращает длительность плана лечения в днях.
     *
     * @return количество дней
     */
    public int getDurationDays() { return durationDays; }

    /**
     * Возвращает строковое представление плана лечения.
     *
     * @return отформатированная строка с данными плана
     */
    @Override
    public String toString() {
        return "План лечения [ID=" + id + ", описание='" + description + "', длительность=" + durationDays + " дн.]";
    }
}
