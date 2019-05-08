package Lesson_3;

import java.util.*;

public class Array {
    public static void main(String[] args) {
        LinkedList<String> arrayList = new LinkedList<>();
        arrayList.add("дом");
        arrayList.add("хлеб");
        arrayList.add("Вася");
        arrayList.add("солнце");
        arrayList.add("рыба");
        arrayList.add("Вася");
        arrayList.add("дом");
        arrayList.add("нос");
        arrayList.add("слон");
        arrayList.add("хлеб");
        arrayList.add("куча");
        arrayList.add("Петя");
        arrayList.add("машина");
        arrayList.add("река");
        arrayList.add("отпуск");
        arrayList.add("отпуск");

        Iterator<String> itr = arrayList.iterator();
        Set<String> set = new HashSet<>(arrayList);

        System.out.println(set);
        System.out.println(arrayList);
        while (itr.hasNext()) {

            int num = 0;
            String res = itr.next();
            for (int i = 0; i < arrayList.size(); i++) {
                if (res == arrayList.get(i)) {
                    num++;

                }
            }
            if (num == 1) {
                System.out.println("слово " + res+ " встречается один раз");
            }else {
                System.out.println("слово " + res + " встречается " + num +" раза");

            }
        }

    }
}
