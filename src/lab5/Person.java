package lab5;

/**
 * Абстрактный базовый класс для всех людей в системе медицинского обслуживания.
 * <p>
 * Хранит общие для всех участников поля — имя и возраст — и объявляет
 * абстрактный метод {@link #displayInfo()}, который каждый наследник
 * реализует по-своему (пример полиморфизма).
 *
 * @author Цаболов Арсен
 * @version 2.0
 * @since 1.0
 * @see Doctor
 * @see Nurse
 * @see Patient
 */
public abstract class Person {

    /** Имя человека. */
    protected String name;

    /** Возраст человека в годах. */
    protected int age;

    /**
     * Создаёт человека с заданными именем и возрастом.
     *
     * @param name имя человека
     * @param age  возраст человека в годах (неотрицательное число)
     */
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Выводит в стандартный поток вывода информацию об объекте.
     * <p>
     * Реализуется каждым подклассом индивидуально.
     */
    public abstract void displayInfo();

    /**
     * Возвращает имя человека.
     *
     * @return имя человека
     */
    public String getName() { return name; }

    /**
     * Устанавливает новое имя человека.
     *
     * @param name новое имя
     */
    public void setName(String name) { this.name = name; }

    /**
     * Возвращает возраст человека.
     *
     * @return возраст в годах
     */
    public int getAge() { return age; }

    /**
     * Устанавливает новый возраст человека.
     *
     * @param age новый возраст в годах
     */
    public void setAge(int age) { this.age = age; }
}
