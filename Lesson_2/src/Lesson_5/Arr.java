package Lesson_5;

/*
 Время считаем с созданием и инициализацией n - массивов;
*/

public class Arr {
    public Arr() {
    }

    static int size = 100000000;
    static int piece = 10;
    static float[] Arr = new float[size];
    static float[] Arr2 = new float[size];

    public static void main(String[] args) {
      Arr array = new Arr();
      array.check();
    }
    public void check(){
        for (int i = 0; i < size; i++) {
            Arr[i] = 1;
        }

        long c = System.currentTimeMillis();
        for (int i = 0; i < piece; i++) {
            float[] arri = new float[size / piece];

            System.arraycopy(Arr, (size / piece) * i, arri, 0, size/ piece);

            Thread threadi = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < size / piece; j++) {
                        arri[j] = (float) (arri[j] * Math.sin(0.2f + j / 5) * Math.cos(0.2f + j / 5) * Math.cos(0.4f + j / 2));
                    }
                }
            });
            threadi.start();

            try {
                threadi.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.arraycopy(arri, 0, Arr2, (size / piece) * i, size / piece);

        }
        System.out.println("Время выполнения с созданием и инициализацией " + piece + " потоков");
        System.out.println(System.currentTimeMillis() - c);

    }
}
