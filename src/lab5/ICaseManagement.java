package lab5;

/**
 * Обобщённый интерфейс управления медицинскими записями и случаями.
 * <p>
 * Реализуется классом {@link Doctor} для работы с {@link MedicalReport}
 * и классом {@link Patient} для работы с {@link TreatmentPlan}.
 *
 * @param <T> тип управляемого объекта (например, {@link MedicalReport} или {@link TreatmentPlan})
 * @author Цаболов Арсен
 * @version 1.0
 * @since 2.0
 * @see Doctor
 * @see Patient
 * @see MedicalReport
 * @see TreatmentPlan
 */
public interface ICaseManagement<T> {

    /**
     * Добавляет новый случай (медицинский документ/план) в хранилище.
     *
     * @param caseItem добавляемый объект типа {@code T}
     */
    void addCase(T caseItem);

    /**
     * Находит и возвращает случай по его уникальному идентификатору.
     *
     * @param id строковый идентификатор случая
     * @return найденный объект типа {@code T} или {@code null}, если не найден
     */
    T getCase(String id);
}
