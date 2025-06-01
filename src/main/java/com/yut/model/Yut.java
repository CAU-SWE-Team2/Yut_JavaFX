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

    private int[] yuts;

    private Yut() {
        yuts = new int[4];
    }

    static Yut getYut() {return instance;}

    void rollYutRandomly(){
        Random random = new Random();
        
        yuts[0] = random.nextInt(4) < 2 ? 1 : 0;
        yuts[1] = random.nextInt(4) < 2 ? 1 : 0;
        yuts[2] = random.nextInt(4) < 2 ? 1 : 0;
        yuts[3] = random.nextInt(4) < 2 ? 1 : 0;

        if(yuts[0] == 0 && yuts[1] == 1 && yuts[2] == 1 && yuts[3] == 1){
            current = Yut.BACKDO;
        }
        else if(yuts[0] + yuts[1] + yuts[2] + yuts[3] == 3){
            current = Yut.DO;
        }
        else if(yuts[0] + yuts[1] + yuts[2] + yuts[3] == 2){
            current = Yut.GE;
        }
        else if(yuts[0] + yuts[1] + yuts[2] + yuts[3] == 1){
            current = Yut.GUL;
        }
        else if(yuts[0] + yuts[1] + yuts[2] + yuts[3] == 0){
            current = Yut.YUT;
        }
        else if(yuts[0] + yuts[1] + yuts[2] + yuts[3] == 4){
            current = Yut.MO;
        }
    }

    void rollYutSelected(int yut){
        current = yut;
    }


    int[] getCurrent(){
        return new int[] {current, yuts[0], yuts[1], yuts[2], yuts[3]};
    }


}
