package Lesson_3;

import java.util.*;

public class PhoneBook {

    HashMap<String, HashSet<String>> contact = new HashMap<>();
    HashSet<String> phone = new HashSet<>();

    public void add(String lastName, String phoneNumber) {
        if (contact.containsKey(lastName)) {
            phone = contact.get(lastName);
            phone.add(phoneNumber);
            contact.put(lastName, phone);
        } else {
            phone = new HashSet<>();
            phone.add(phoneNumber);
            contact.put(lastName, phone);
        }
    }

    public void getPhone(String lastName) {
        System.out.println("По имени " + lastName + " найдено");
        System.out.println(contact.get(lastName));
    }

    public static void main(String[] args) {
        PhoneBook phoneBook1 = new PhoneBook();
        phoneBook1.add("Вася", "1236");
        phoneBook1.add("Вася", "112244");
        phoneBook1.add("Вася", "11212244");
        phoneBook1.add("Аня", "12244");
        phoneBook1.add("Вася", "145612244");
        phoneBook1.add("Аня", "11227744");
        phoneBook1.add("Вася", "112244");
        phoneBook1.add("Петя", "11244244");

        phoneBook1.getPhone("Вася");
        phoneBook1.getPhone("Аня");
    }

}



