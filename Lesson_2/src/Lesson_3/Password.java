package Lesson_3;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Password {

    public static void checkPass(String password) {
        char c;
        int lowerCase = 0;
        int upperCase = 0;
        int spesialCase = 0;
        int digital = 0;
        if (password.length() >= 8 && password.length() <= 20) {
            for (int i = 0; i < password.length(); i++) {
                c = password.charAt(i);
                if (Character.isUpperCase(c)) {
                    upperCase++;
                }
                if (Character.isLowerCase(c)) {
                    lowerCase++;
                }
                if (Character.isDigit(c)) {
                    digital++;
                }
                if (c >= 33 && c <= 47 || c >= 58 && c <= 64) {
                    spesialCase++;
                }
            }
            if (spesialCase > 0 && digital > 0 && upperCase > 0 && lowerCase > 0) {
                System.out.println("Отличный пароль!");
            } else if (spesialCase == 0) {
                System.out.println("Не хватает спец символа!");
            } else if (digital == 0) {
                System.out.println("Не хватает числа!");
            } else if (lowerCase == 0) {
                System.out.println("Не хватает строчной буквы!");
            } else if (upperCase == 0) {
                System.out.println("Не хватает прописной буквы!");
            }
        } else if (password.length() < 8) {
            System.out.println("Пароль меньше 8 символов");

        } else if (password.length() > 20) {
            System.out.println("Пароль больше 20 символов");
        }
    }

    public static void main(String[] args) {
        String pass1 = "!sds#Fd33g1121111";
        String pass2 = "!sds#d33g1121111";
        checkPass(pass1);
        checkPass(pass2);
        Pattern pattern;
        pattern = Pattern.compile("(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9@#$%]).{8,20}");
        Matcher matcher = pattern.matcher(pass1);
        if (matcher.find()) {
            System.out.println("Пароль подходит!");
        } else System.out.println("Пароль не валиден!");
    }
}