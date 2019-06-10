public class MySizeArrayException extends Exception {
    String[][] mass;

    public MySizeArrayException(String msg, String[][] mass) {
        super(msg);
        this.mass = mass;
    }
}

class MySizeArray {
    public void checkArray(String[][] mass) throws MySizeArrayException {
        if (mass.length != 4) throw new MySizeArrayException("Ошибка размера массива", mass);
    }
}
