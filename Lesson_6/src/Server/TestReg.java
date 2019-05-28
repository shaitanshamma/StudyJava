package Server;

public class TestReg {
    public static void main(String[] args) {
        String str = "/w bbb ccc ccccc cccc ";
        String[] tok = str.split(" ", 3);
        String ss = tok[1];

        for (String s : tok) {
            System.out.println(s);
        }
        System.out.println("ss " + ss);
    }
}
