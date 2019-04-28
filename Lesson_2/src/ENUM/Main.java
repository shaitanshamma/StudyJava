package ENUM;

public class Main {
    public static void main(String[] args) {
        System.out.println("Осталось работать: " + Days.valueOf("SUNDAY").workOurs);
        System.out.println(Days.FRIDAY.getRusName() + " " + Days.FRIDAY.getWorkOurs());
        for (Days d : Days.values()) {
            System.out.println("Осталось работать в " + d.getRusName() + " : " + d.workOurs);
        }
        System.out.println(getWorkOur(Days.FRIDAY));
    }

    private static String getWorkOur(Days day) {
        return day.workOurs;
    }
}
