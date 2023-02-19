/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ghostnet.gncasestudy;

/**
 *
 * @author stefa
 */
public class Converter {
    public static Status IntToStatus(int i) {
        switch (i) {
            case 1: return Status.REPORTED;
            case 2: return Status.UPCOMING_SALVAGE;
            case 3: return Status.SALVAGED;
            case 4: return Status.MISSING;
            case 5: return Status.FOUND;
        }
        return Status.ERROR;
    }
    
    public static int StatusToInt (Status s){
        switch (s) {
            case REPORTED: return 1;
            case UPCOMING_SALVAGE: return 2;
            case SALVAGED: return 3;
            case MISSING: return 4;
            case FOUND: return 5;
        }
        return -1;
    }
}
