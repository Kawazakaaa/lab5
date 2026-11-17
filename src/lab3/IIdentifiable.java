package lab3;
/**
 * Контракт для объектов, обладающих уникальным идентификатором.
 * <p>
 * Позволяет единообразно получать ID у врачей, медсестёр и пациентов,
 * что используется, например, при поиске в {@link Hospital}.
 *
 * @author Цаболов Арсен
 * @version 1.0
 * @since 1.0
 * @see Doctor
 * @see Nurse
 * @see Patient
 */
public interface IIdentifiable {

    /**
     * Возвращает уникальный идентификатор объекта.
     *
     * @return строковый идентификатор, не равный {@code null}
     */
    String getId();
}