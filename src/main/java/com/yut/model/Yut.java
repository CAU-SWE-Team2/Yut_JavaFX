package com.yut.model;
import java.util.Random;


public class Yut {
    private static Yut instance = new Yut();

    public static final int BACKDO = 0;
    public static final int DO = 1;
    public static final int GE = 2;
    public static final int GUL = 3;
    public static final int YUT = 4;
    public static final int MO = 5;

    public static String getYutName(int yut){
        switch (yut) {
            case BACKDO:
                return "Backdo";
            case DO:
                return "Do";
            case GE:
                return "Ge";
            case GUL:
                return "Gul";
            case YUT:
                return "Yut";
            case MO:
                return "Mo";
            default:
                return null;
        }
    }

    private Integer current = null;

    private Yut() {}

    static Yut getYut() {return instance;}

    void rollYutRandomly(){
        Random random = new Random();
        current = random.nextInt(16);
        if (current == 0) {
            current = 0;
        } else if (current <= 3) {
            current = 1;
        } else if (current <= 9) {
            current = 2;
        } else if (current <= 13) {
            current = 3;
        } else if (current == 14) {
            current = 4;
        } else {
            current = 5;
        }
    }

    void rollYutSelected(int yut){
        current = yut;
    }


    int getCurrent(){return current;}


}
