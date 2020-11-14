/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Engine.Networking;


import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 1635598
 */
public class ArrayListNetwork {
    private ArrayList<String> list = new ArrayList<>();

    public ArrayListNetwork() {
        list = new ArrayList<>();
    }
    
    public ArrayListNetwork(String data) {
        list = new ArrayList<>();
        String[] seperatedData = data.split(" ");
        for(String str : seperatedData) {
            list.add(str);
        }
    }
    
    public int readInt() {
        int intValue = Integer.getInteger((String) list.get(0));
        list.remove(0);
        return intValue;
    }
    
    public float readFloat() {
        float floatValue = Float.valueOf((String) list.get(0));
        list.remove(0);
        return floatValue;
    }
    
    public String readString() {
        String stringValue = String.valueOf(list.get(0));
        list.remove(0);
        return stringValue;
    }
    
    public long readLong() {
        long longValue = Long.valueOf((String) list.get(0));
        list.remove(0);
        return longValue;
    }
    
    public byte readByte() {
        byte byteValue = Byte.valueOf((String) list.get(0));
        list.remove(0);
        return byteValue;
    }
    
    public boolean readBool() {
        boolean booleanValue = Boolean.valueOf((String) list.get(0));
        list.remove(0);
        return booleanValue;
    }
    
    public void add(Object obj) {
        list.add(obj.toString());
    }
    
    public boolean listEmpty() {
        return list.isEmpty();
    }
    
    public String convert() {
        String str = "";
        for(Object obj : list) {
            str += obj.toString() + " ";
        }
        return str;
    }
}
