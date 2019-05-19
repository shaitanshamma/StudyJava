package Lesson_5;


public class ArrayMath {
    static int size = 10000000;
    static int half = size / 2;
    static float[] arr1 = new float[size];
    static float[] arr2 = new float[half];
    static float[] arr3 = new float[half];

    public void opacity(float arr[]) {
        long c = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr1[i] = 1;
        }
        System.out.println("Скорость заполнения ");
        System.out.println(System.currentTimeMillis() - c);
    }

    public void math1(float arr[]) {
        long a = System.currentTimeMillis();
        for (int i = 0; i < size; i++) {
            arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println("С одним потоком ");
        System.out.println(System.currentTimeMillis() - a);
    }

    public void math2(float arr[]) {
        long b = System.currentTimeMillis();
        System.arraycopy(arr1, 0, arr2, 0, half);
        System.arraycopy(arr1, half, arr3, 0, half);
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size / 2; i++) {
                    arr2[i] = (float) (arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        thread1.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < size / 2; i++) {
                    arr3[i] = (float) (arr3[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
            }
        });
        thread2.start();
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.arraycopy(arr2, 0, arr1, 0, half);
        System.arraycopy(arr3, 0, arr1, half, half);

        System.out.println("С двумя потоками ");
        System.out.println(System.currentTimeMillis() - b);
    }


    public static void main(String[] args) {
        ArrayMath arr = new ArrayMath();
        arr.opacity(arr1);
        arr.math1(arr1);
        arr.opacity(arr1);
        arr.math2(arr1);

    }
}
