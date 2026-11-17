package lab5;

/**
 * Класс медицинского отчёта (заключения), которым управляет {@link Doctor}
 * через обобщённый интерфейс {@link ICaseManagement}.
 *
 * @author Цаболов Арсен
 * @version 1.0
 * @since 2.0
 * @see Doctor
 * @see ICaseManagement
 */
public final class MedicalReport implements IIdentifiable {

    /** Уникальный идентификатор отчёта. */
    private final String id;

    /** Имя или ID пациента, к которому относится отчёт. */
    private final String patientName;

    /** Текст медицинского заключения. */
    private final String conclusion;

    /**
     * Создаёт медицинский отчёт.
     *
     * @param id          уникальный идентификатор отчёта
     * @param patientName имя пациента
     * @param conclusion  текст заключения врача
     */
    public MedicalReport(String id, String patientName, String conclusion) {
        this.id = id;
        this.patientName = patientName;
        this.conclusion = conclusion;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() { return id; }

    /**
     * Возвращает имя пациента, для которого составлен отчёт.
     *
     * @return имя пациента
     */
    public String getPatientName() { return patientName; }

    /**
     * Возвращает текст заключения врача.
     *
     * @return текст заключения
     */
    public String getConclusion() { return conclusion; }

    /**
     * Возвращает строковое представление медицинского отчёта.
     *
     * @return отформатированная строка с данными отчёта
     */
    @Override
    public String toString() {
        return "Медотчёт [ID=" + id + ", пациент=" + patientName + ", заключение='" + conclusion + "']";
    }
}
