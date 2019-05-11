package Lesson_3;

import java.util.*;

public class PhoneBook {
    String lastName;

    public int getPhone(String lastName) {
        return phone;
    }

    int phone;

    public String getLastName() {
        return lastName;
    }

    public PhoneBook(String lastName, int phone) {
        this.lastName = lastName;
        this.phone = phone;
    }

    public String toString() {
        return lastName + ":" + phone;
    }

    public static void main(String[] args) {
        LinkedList<PhoneBook> phoneBook = new LinkedList<>();
        phoneBook.add(new PhoneBook("Вася", 1112345));
        phoneBook.add(new PhoneBook("Вася", 1233345));
        phoneBook.add(new PhoneBook("Аня", 1232245));
        phoneBook.add(new PhoneBook("Аня", 12121345));
        for (PhoneBook phone : phoneBook) {
            System.out.println(phone.getPhone("Вася"));
        }
     }
}



