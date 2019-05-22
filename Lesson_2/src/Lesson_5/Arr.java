package Lesson_5;

/*
 Время считаем с созданием и инициализацией n - массивов;
*/

import java.util.Arrays;

public class Arr {
    public Arr() {
    }

    static int size = 10000;
    static int piece = 5;
    static int[] Arr = new int[size];
    static int[] Arr2 = new int[size];
    static int[][] ARR = new int[piece][size / piece];

    public static void main(String[] args) {
        Arr array = new Arr();
        array.check();
    }

    public void check() {
//        for (int i = 0; i < size; i++) {
//            Arr[i] = 1;
//        }
//
//        long c = System.currentTimeMillis();
//        for (int i = 0; i < piece; i++) {
//            float[] arri = new float[size / piece];
//
//            System.arraycopy(Arr, (size / piece) * i, arri, 0, size / piece);
//
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    for (int j = 0; j < size / piece; j++) {
//                        arri[j] = (float) (arri[j] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
//                    }
//                }
//            });
//            thread.start();
//
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.arraycopy(arri, 0, Arr2, (size / piece) * i, size / piece);
//
//        }
//        System.out.println("Время выполнения с созданием и инициализацией " + piece + " потоков");
//        System.out.println(System.currentTimeMillis() - c);
//
//    }

        for (int i = 0; i < size; i++) {
            Arr[i] = i;
        }
        long c = System.currentTimeMillis();
        for (int i = 0; i < piece; i++) {
            System.arraycopy(Arr, (size / piece) * i, ARR[i], 0, size / piece);
           // System.out.println(Arrays.toString(ARR[i]));
        }
        for (int i = 0; i < piece; i++) {
            Thread threadi = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < piece; i++) {
                    for (int j = 0; j < size / piece; j++) {
                       // ARR[j][j] = (float) (ARR[j][j] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
                        ARR[i][j] =  (i);

                    }
                }
            }});
            threadi.start();

            try {
                threadi.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < piece; i++) {
            for (int j = 0; j < size / piece; j++) {
                System.arraycopy(ARR[i], 0, Arr2, (size /piece)*i, size / piece);
            }
        }
           // System.out.println(Arrays.toString(Arr));
            System.out.println("Время выполнения с созданием и инициализацией " + piece + " потоков");
            System.out.println(System.currentTimeMillis() - c);
       // System.out.println(Arrays.toString(Arr2));
        }
    }

