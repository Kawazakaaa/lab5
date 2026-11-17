package lab5;

/**
 * Контракт для объектов, для которых можно рассчитать стоимость оказанных услуг
 * и применить скидку.
 * <p>
 * В лабораторной работе №5 интерфейс расширен методом {@link #applyDiscount(double)},
 * а также используется при создании анонимного класса в {@link Hospital#generateAnonymousBill(Patient)}.
 *
 * @author Цаболов Арсен
 * @version 2.0
 * @since 1.0
 * @see Patient
 * @see Hospital#generateAnonymousBill(Patient)
 */
public interface IBillable {

    /**
     * Вычисляет итоговую стоимость лечения объекта без учёта скидки.
     *
     * @return стоимость в рублях (неотрицательное число)
     */
    double calculateBill();

    /**
     * Применяет указанную процентную скидку к рассчитанной сумме счёта.
     *
     * @param discountPercentage процент скидки (от 0 до 100)
     * @return новая сумма счёта с учётом скидки
     */
    double applyDiscount(double discountPercentage);
}
