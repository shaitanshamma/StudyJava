package ENUM;

public enum Days {
    SUNDAY("Не работаем!", "Воскресенье"),
    MONDAY("40 часов", "Понедельник"),
    TUESDAY("32 часа", "Вторник"),
    WEDNESDAY("24 часа", "Среда"),
    THURSDAY("16 часов", "Четверг"),
    FRIDAY("8 часов", "Пятница"),
    SATURDAY("Не работаем!", "Суббота");

    public String getRusName() {
        return rusName;
    }

    Days(String workOurs, String rusName) {
        this.workOurs = workOurs;
        this.rusName = rusName;
    }

    String workOurs;
    String rusName;

    public String getWorkOurs() {
        return workOurs;
    }

}
