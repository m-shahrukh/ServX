package pl.servx.servx.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class cart_data {

    //public static ArrayAdapter<String> adapt;
    public static ArrayList<String> cars= new ArrayList<>();

    public static String OilChange="";
    public static String CarWash="";
    public static String reqid="";
    public static int OilGold, OilSilver, OilBronze, CarGold, CarSilver, CarBronze, costOil, costWash;
    public static Map<String, String> dict= new HashMap<String,String>();
    public static String date =""; public static String time="" ;public static  String location="";
    public static String car="";


    public void setData(String oil, String wash){
        OilChange= oil;
        CarWash=wash;
    }

    public void setLocation(String loc){
        location = loc;
    }

    public void setdDate(String loc){
        date = loc;
    }

    public void setTime(String loc){
        time = loc;
    }

    public void setCar(String loc){car = loc;}

    public static void clear(){
        OilChange=""; CarWash=""; dict.clear(); time="";
        date=""; car=""; location=""; reqid="";
        OilGold=0; OilBronze=0; OilSilver=0;
        CarGold=0; CarBronze=0; CarSilver=0;
    }
}