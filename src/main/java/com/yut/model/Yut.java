package com.yut.model;
import java.util.Random;


public class Yut {
    private static Yut instance = new Yut();
    public static final int DO = 1;
    public static final int GE = 2;
    public static final int GUL = 3;
    public static final int YUT = 4;
    public static final int MO = 5;

    private Integer current = null;

    private Yut() {}

    static Yut getYut() {return instance;}

    void rollYut(){
        Random random = new Random();
        current = random.nextInt(5) + 1;
    }

    int getCurrent(){return current;}
}
