package lab3;
/**
 * Контракт для объектов, которые могут получать лечение.
 * <p>
 * В предметной области реализуется только классом {@link Patient}.
 * Врач назначает лечение через {@link Doctor#prescribeTreatment(Patient, String)},
 * который внутри вызывает {@link #receiveTreatment(String)}.
 *
 * @author Цаболов Арсен
 * @version 1.0
 * @since 1.0
 * @see Patient
 * @see Doctor#prescribeTreatment(Patient, String)
 */
public interface ITreatable {

    /**
     * Применяет к объекту новое лечение.
     * <p>
     * Реализация должна сохранять назначение во внутреннем хранилище,
     * не изменяя ранее добавленные записи.
     *
     * @param treatment текстовое описание лечения; не должно быть {@code null}
     */
    void receiveTreatment(String treatment);
}