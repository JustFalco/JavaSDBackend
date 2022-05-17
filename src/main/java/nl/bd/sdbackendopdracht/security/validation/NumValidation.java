package nl.bd.sdbackendopdracht.security.validation;

public class NumValidation {
    public Boolean validateId(Long id){

        boolean b = id < Long.MAX_VALUE;
        boolean b1 = id != null;
        boolean b2 = id > 0;

        if(b && b1 && b2){
            return true;
        }else {
            return false;
        }
    }

    public Boolean validateNumber(int number, int min, int max){

        boolean b = number < Integer.MAX_VALUE;
        boolean b2 = number > 0;
        boolean b3 = number >= min;
        boolean b4 = number <= max;

        if(b && b2 && b3 && b4){
            return true;
        }else {
            return false;
        }
    }

    public Boolean validateFloat(float number, float min, float max){

        boolean b = number < Float.MAX_VALUE;
        boolean b2 = number > Float.MIN_VALUE;
        boolean b3 = number >= min;
        boolean b4 = number <= max;

        if(b && b2 && b3 && b4){
            return true;
        }else {
            return false;
        }
    }
}
