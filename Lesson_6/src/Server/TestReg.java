package Server;

import java.util.*;

public class TestReg {


    public static void main(String[] args) {

        //HashSet<String> s = new TreeSet<String>();
        String str = "/w bbb ccc ccccc cccc ";
        String[] tok = str.split(" ", 3);
        String ss = tok[1];

        for (String s : tok) {
            System.out.println(s);
        }
        System.out.println("ss " + ss);
        //Set<String> s = new HashMap<String,>();
        Set<String> e = new LinkedHashSet<>();
        HashMap<String, String> z = new HashMap<>();
        HashSet<String> q = new HashSet<>();

            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(1);
                }
            });
            Thread t2 = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        t1.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(2);
                }
            });
            t1.start();
            t2.start();

}}