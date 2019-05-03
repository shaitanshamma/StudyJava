public class MyArrayDataException extends Exception {
    private int b;
    private int c;

    public MyArrayDataException(String msg, int b, int c) {
        super(msg);
        this.b = b;
        this.c = c;
    }
}

class MyArrayData {

    public static int rezult;
    public static int c;
    public static int b;

    public int ch(String[][] ms) throws MyArrayDataException {
        for (int i = 0; i < ms.length; i++) {
            for (int j = 0; j < ms.length; j++) {
                try {
                    rezult += Integer.parseInt(ms[i][j]);
                    c = i;
                    b = j+1;
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException("Ошибка данных массива ", i, j);
                }
            }
        }
        return rezult;
    }

}