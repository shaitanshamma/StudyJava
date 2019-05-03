
public class Main {

    public static void main(String[] args) {
        String[][] mass = {{"1", "2", "d", "s"}, {"2", "4", "w", "2"}, {"2", "4", "w", "2"}, {"2", "4", "w", "2"}};
        String[][] mass2 = {{"1","4","1","2"},{"1","4","1","2"},{"2","4","1","2"}};

        MySizeArray mySizeArray = new MySizeArray();
        MyArrayData myArrayData = new MyArrayData();
        try {
            mySizeArray.checkArray(mass);
        } catch (MySizeArrayException e) {
            e.printStackTrace();
        }
        try {
            mySizeArray.checkArray(mass2);
        } catch (MySizeArrayException e) {
            e.printStackTrace();
        }
        try {
            myArrayData.ch(mass);

        } catch (MyArrayDataException e) {
            e.printStackTrace();
        }
        System.out.println("Сумма элементов массива равна " + MyArrayData.rezult);
    }
}