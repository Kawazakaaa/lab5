package lab5;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс, представляющий пациента медицинского учреждения.
 * <p>
 * Наследует {@link Person} и реализует четыре интерфейса:
 * {@link IIdentifiable}, {@link ITreatable}, {@link IBillable} и
 * {@link ICaseManagement} с параметром типа {@link TreatmentPlan}.
 * Содержит внутренний (inner) класс {@link MedicalRecord}, имеющий прямой
 * доступ к приватным полям пациента.
 * Класс объявлен {@code final}.
 *
 * @author Цаболов Арсен
 * @version 2.0
 * @since 1.0
 * @see Person
 * @see IIdentifiable
 * @see ITreatable
 * @see IBillable
 * @see ICaseManagement
 * @see TreatmentPlan
 */
public final class Patient extends Person implements IIdentifiable, ITreatable, IBillable, ICaseManagement<TreatmentPlan> {

    /** Уникальный идентификатор пациента. */
    private final String id;

    /** Приватная копия имени пациента для демонстрации прямого доступа из inner-класса. */
    private String privatePatientName;

    /** Текущий диагноз пациента. */
    private String diagnosis;

    /** Массив назначенных лечений (сохранён из лабораторной работы №3). */
    private final String[] treatments;

    /** Количество реально заполненных элементов в массиве {@link #treatments}. */
    private int treatmentCount;

    /** Количество дней, проведённых пациентом в больнице. */
    private int daysInHospital;

    /** Стоимость одного дня пребывания в стационаре (в рублях). */
    private double dailyRate;

    /** Находится ли пациент в данный момент в больнице. */
    private boolean inHospital;

    /** Назначенный медицинский сотрудник. */
    private Person assignedStaff;

    /** Экземпляр внутреннего класса медицинской карты пациента. */
    private final MedicalRecord medicalRecord;

    /** Список планов лечения пациента (для {@link ICaseManagement}). */
    private final List<TreatmentPlan> treatmentPlans;

    /**
     * Создаёт пациента со стандартными параметрами пребывания в стационаре
     * (5 дней по 1500 руб./день, находится в больнице).
     *
     * @param name      имя пациента
     * @param age       возраст в годах
     * @param id        уникальный идентификатор
     * @param diagnosis первичный диагноз
     */
    public Patient(String name, int age, String id, String diagnosis) {
        this(name, age, id, diagnosis, 5, 1500.0, true);
    }

    /**
     * Создаёт пациента с полным набором параметров.
     *
     * @param name           имя пациента
     * @param age            возраст в годах
     * @param id             уникальный идентификатор
     * @param diagnosis      первичный диагноз
     * @param daysInHospital количество дней в стационаре
     * @param dailyRate      стоимость одного дня пребывания
     * @param inHospital     {@code true}, если пациент находится в больнице
     */
    public Patient(String name, int age, String id, String diagnosis,
                   int daysInHospital, double dailyRate, boolean inHospital) {
        super(name, age);
        this.id = id;
        this.privatePatientName = name;
        this.diagnosis = diagnosis;
        this.treatments = new String[100];
        this.treatmentCount = 0;
        this.daysInHospital = daysInHospital;
        this.dailyRate = dailyRate;
        this.inHospital = inHospital;
        this.medicalRecord = new MedicalRecord("Не указана", new ArrayList<>(), "Первичный приём");
        this.treatmentPlans = new ArrayList<>();
    }

    /**
     * Внутренний (inner) класс, хранящий медицинскую карту конкретного пациента.
     * <p>
     * Имеет прямой доступ к приватным полям внешнего класса {@link Patient},
     * включая {@link Patient#privatePatientName}, {@link Patient#id} и {@link Patient#diagnosis}.
     */
    public class MedicalRecord {

        /** Дата последнего посещения врача. */
        private String lastVisitDate;

        /** Список назначенных лекарств. */
        private final List<String> prescribedMedications;

        /** Дополнительная медицинская информация о пациенте. */
        private String additionalInfo;

        /**
         * Создаёт медицинскую карту пациента.
         *
         * @param lastVisitDate         дата последнего визита к врачу
         * @param prescribedMedications начальный список лекарств
         * @param additionalInfo        дополнительная информация
         */
        public MedicalRecord(String lastVisitDate, List<String> prescribedMedications, String additionalInfo) {
            this.lastVisitDate = lastVisitDate;
            this.prescribedMedications = new ArrayList<>(prescribedMedications);
            this.additionalInfo = additionalInfo;
        }

        /**
         * Возвращает дату последнего посещения врача.
         *
         * @return дата последнего посещения
         */
        public String getLastVisitDate() { return lastVisitDate; }

        /**
         * Устанавливает дату последнего посещения врача.
         *
         * @param lastVisitDate новая дата посещения
         */
        public void setLastVisitDate(String lastVisitDate) { this.lastVisitDate = lastVisitDate; }

        /**
         * Возвращает копию списка назначенных лекарств.
         *
         * @return список лекарств
         */
        public List<String> getPrescribedMedications() {
            return new ArrayList<>(prescribedMedications);
        }

        /**
         * Добавляет лекарство в список назначений медкарты.
         *
         * @param medication название лекарства
         */
        public void addMedication(String medication) {
            if (medication != null && !medication.trim().isEmpty()) {
                prescribedMedications.add(medication.trim());
            }
        }

        /**
         * Возвращает дополнительную медицинскую информацию.
         *
         * @return строка с дополнительной информацией
         */
        public String getAdditionalInfo() { return additionalInfo; }

        /**
         * Устанавливает дополнительную медицинскую информацию.
         *
         * @param additionalInfo новая дополнительная информация
         */
        public void setAdditionalInfo(String additionalInfo) { this.additionalInfo = additionalInfo; }

        /**
         * Возвращает полное строковое описание медкарты, обращаясь напрямую
         * к приватным полям внешнего класса {@link Patient}.
         *
         * @return отформатированная информация медкарты
         */
        public String getFullRecordInfo() {
            return "Медкарта пациента " + privatePatientName + " (ID=" + id + ", диагноз=" + diagnosis + "): "
                    + "последний визит=" + lastVisitDate
                    + ", лекарства=" + prescribedMedications
                    + ", доп. сведения='" + additionalInfo + "'";
        }

        /**
         * Выводит содержимое медкарты в консоль.
         */
        public void printRecord() {
            System.out.println(getFullRecordInfo());
        }
    }

    /**
     * Возвращает экземпляр внутреннего класса {@link MedicalRecord} данного пациента.
     *
     * @return медицинская карта пациента
     */
    public MedicalRecord getMedicalRecord() {
        return medicalRecord;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setName(String name) {
        super.setName(name);
        this.privatePatientName = name;
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
     * Возвращает количество дней, проведённых в больнице.
     *
     * @return количество дней
     */
    public int getDaysInHospital() { return daysInHospital; }

    /**
     * Устанавливает количество дней, проведённых в больнице.
     *
     * @param daysInHospital количество дней
     */
    public void setDaysInHospital(int daysInHospital) { this.daysInHospital = daysInHospital; }

    /**
     * Возвращает стоимость одного дня пребывания в больнице.
     *
     * @return стоимость дня в рублях
     */
    public double getDailyRate() { return dailyRate; }

    /**
     * Устанавливает стоимость одного дня пребывания в больнице.
     *
     * @param dailyRate стоимость дня в рублях
     */
    public void setDailyRate(double dailyRate) { this.dailyRate = dailyRate; }

    /**
     * Проверяет, находится ли пациент в больнице.
     *
     * @return {@code true}, если пациент находится в больнице
     */
    public boolean isInHospital() { return inHospital; }

    /**
     * Устанавливает статус нахождения пациента в больнице.
     *
     * @param inHospital {@code true}, если пациент в больнице
     */
    public void setInHospital(boolean inHospital) { this.inHospital = inHospital; }

    /**
     * Возвращает назначенного пациенту медицинского сотрудника.
     *
     * @return назначенный сотрудник или {@code null}
     */
    public Person getAssignedStaff() { return assignedStaff; }

    /**
     * Устанавливает назначенного медицинского сотрудника.
     *
     * @param assignedStaff врач или другой сотрудник
     */
    public void setAssignedStaff(Person assignedStaff) { this.assignedStaff = assignedStaff; }

    /**
     * Возвращает количество назначенных лечений.
     *
     * @return число добавленных лечений
     */
    public int getTreatmentCount() { return treatmentCount; }

    /**
     * {@inheritDoc}
     * <p>
     * Сохраняет назначение в массиве {@link #treatments} и одновременно
     * добавляет запись в список лекарств/назначений {@link MedicalRecord}.
     *
     * @param treatment описание лечения
     */
    @Override
    public void receiveTreatment(String treatment) {
        if (treatmentCount < treatments.length) {
            treatments[treatmentCount++] = treatment;
        }
        medicalRecord.addMedication(treatment);
    }

    /**
     * Возвращает копию массива назначенных лечений.
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
     * Условная стоимость: 1000 рублей за каждое назначенное лечение
     * плюс стоимость пребывания в стационаре (дни * стоимость дня).
     *
     * @return стоимость лечения в рублях
     */
    @Override
    public double calculateBill() {
        return treatmentCount * 1000.0 + daysInHospital * dailyRate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double applyDiscount(double discountPercentage) {
        double total = calculateBill();
        return total * (1.0 - discountPercentage / 100.0);
    }

    /**
     * Добавляет план лечения пациенту.
     *
     * @param caseItem план лечения
     */
    @Override
    public void addCase(TreatmentPlan caseItem) {
        if (caseItem != null) {
            treatmentPlans.add(caseItem);
        }
    }

    /**
     * Находит план лечения по идентификатору.
     *
     * @param caseId идентификатор плана лечения
     * @return найденный {@link TreatmentPlan} или {@code null}
     */
    @Override
    public TreatmentPlan getCase(String caseId) {
        for (TreatmentPlan plan : treatmentPlans) {
            if (plan.getId().equals(caseId)) {
                return plan;
            }
        }
        return null;
    }

    /**
     * Возвращает список всех планов лечения пациента.
     *
     * @return копия списка планов лечения
     */
    public List<TreatmentPlan> getTreatmentPlans() {
        return new ArrayList<>(treatmentPlans);
    }

    /**
     * {@inheritDoc}
     * <p>
     * Выводит имя, возраст, ID, диагноз и статус нахождения в стационаре.
     */
    @Override
    public void displayInfo() {
        System.out.println("Пациент: " + name + ", возраст: " + age +
                ", ID: " + id + ", диагноз: " + diagnosis +
                ", дней в стационаре: " + daysInHospital +
                ", в больнице: " + (inHospital ? "да" : "нет"));
    }
}
