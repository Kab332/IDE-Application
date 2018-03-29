package sample;

import javafx.scene.control.Tab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Document {

    public List<Line> myLines;
    public int numOfLines = 0;

    public Tab tab;

    public Document(Tab tab){
        myLines = new ArrayList<>();

        this.tab = tab;
    }

    public Document(String s, Tab tab){
        myLines = new ArrayList<>();

        this.setString(s);

        this.tab = tab;

    }

    public void setString(String s){
        this.deleteAllLines();
        for(String i : s.split("\n")){
            this.addLine(i);
        }
    }

    public void deleteAllLines(){
        myLines = new ArrayList<>();
        numOfLines = 0;
    }

    public Document(String s){
        myLines = new ArrayList<>();

        this.setString(s);
    }

    public void removeLine(int index){
        if(numOfLines >= index) {
            myLines.remove(index);
            numOfLines -= 1;
        }
    }

    public void addLine(int index, String toBeAdded){

        if(numOfLines < index){
            myLines.add(new Line(toBeAdded));
        }else{
            myLines.add(index, new Line(toBeAdded));
        }
        numOfLines+=1;

    }

    public void addLine(String toBeAdded){
        myLines.add(new Line(toBeAdded));
        numOfLines+=1;
    }

    public void growLines(int target){
        while(target > numOfLines){
            this.addLine("");
        }

    }

    private class getMovementsHelper{
        public List<Integer> deletes = new ArrayList<>();
        public Map<Integer,Integer> movements = new TreeMap<>();
        public getMovementsHelper(List<Integer> del, Map<Integer,Integer> mov){
            this.deletes = del;
            this.movements = mov;
        }
    }

    public getMovementsHelper getMovements(List<String> newBundle, List<String> oldBundle){
        Map<Integer,Integer> movements = new TreeMap<>();

        List<Integer> deletes = new ArrayList<>();
        List<Integer> newBundleLines = new ArrayList<>();
        List<Integer> oldBundleLines = new ArrayList<>();
        int newNumberOfLines = newBundle.size()-1;
        this.growLines(newNumberOfLines);

        for(int i = 0; i < oldBundle.size(); i++){
            if(!oldBundle.get(i).equals("")){
                oldBundleLines.add(i);
            }
        }
        for(int i = 0; i < newBundle.size(); i++){
            newBundleLines.add(i);
        }

        for(int i : oldBundleLines){

            int newIndex =  newBundle.indexOf(oldBundle.get(i));

            if(newIndex > -1) {
                if (newBundleLines.contains(newIndex)) {
                    if (i != newIndex) {

                        movements.put(i, newIndex);
                        newBundleLines.remove(new Integer(newIndex));
                    }
                }
            }else {
                deletes.add(i);
            }
        }

        return new getMovementsHelper(deletes,movements);


    }

    public void moveLines(int a, int b){
        String temp = myLines.get(a).line;
        this.removeLine(a);
        this.addLine(b,temp);
    }

    public Map<Integer, String> getCreations(List<String> newBundle, List<String> oldBundle){
        Map<Integer,String> creations = new TreeMap<>();


        int in = 0;
        for(String i: newBundle){
            if(i.trim().length() == 0){
                in +=1;
                continue;
            }

            int index = oldBundle.indexOf(i);
            if(index == -1){
                creations.put(in,i);
            }
            in +=1;
        }

        return creations;
    }

    public String serializeChanges(Map<Integer, Integer> movements, List<Integer> deletes, Map<Integer,String> creations){
        String ret = "|";
        for(Map.Entry<Integer, Integer> entry : movements.entrySet()){
            ret += entry.getKey() + ":" + entry.getValue()+";";
        }
        ret += "$|";
        for(int i: deletes){
            ret += i+";";
        }
        ret += "$|";
        byte[] bytes;
        for(Map.Entry<Integer, String> entry : creations.entrySet()){
            ret += entry.getKey()+":";
            bytes = entry.getValue().getBytes();
            for(int i = 0; i < bytes.length; i++){

                ret += bytes[i] + "=";
            }
            ret+=";";
        }

        return ret;
    }

    public void change(String serialString){
        System.out.println("gotChange: ["+serialString+"]");
        deserializeHelper deserialized = deserialize(serialString);

        Map<Integer,Integer> m = deserialized.moved;

        for (Map.Entry<Integer, Integer> entry : m.entrySet()) {
            int k = entry.getKey();
            int v = entry.getValue();
            this.moveLines(k,v);
        }

        for(int i: deserialized.deleted){
            this.removeLine(i);
        }

        for(Map.Entry<Integer,String> entry : deserialized.created.entrySet()){
            addLine(entry.getKey(), entry.getValue());
        }

    }

    public void p(String s){
        System.out.println(s);
    }

    public String changed(String inBundle) {


        Map<Integer, String>  creates;
        Map<Integer, Integer> movements;
        List<Integer> deletes;
        getMovementsHelper help;

        List<String> newBundle = new ArrayList<>(Arrays.asList(inBundle.split("\n")));
        List<String> oldBundle = new ArrayList<>(Arrays.asList(this.toString().split("\n")));

        help = getMovements(newBundle,oldBundle);
        movements = help.movements;
        creates = getCreations(newBundle,oldBundle);
        deletes = help.deletes;

        /** order of serialization
         * Movement
         * Deletion
         * Creation
         */
        String serialized = serializeChanges(movements,deletes,creates);
        System.out.println(serialized);

        this.setString(inBundle);

        return serialized;









    }

    class deserializeHelper{
        public Map<Integer,Integer> moved;
        public List<Integer> deleted;
        public Map<Integer, String> created;
        deserializeHelper(Map<Integer,Integer> moved ,List<Integer> deleted, Map<Integer, String> created){
            this.moved = moved;
            this.deleted = deleted;
            this.created = created;
        }

        public String toString(){
            return ""+this.moved+":"+this.deleted+":"+this.created;
        }




    }

    public deserializeHelper deserialize(String serialized){
        String[] parts = serialized.split("\\$");
        String movedS = parts[0];
        String deletedS = parts[1];
        String createdS = parts[2];

        Map<Integer,Integer> moved = new TreeMap<>();
        List<Integer> deleted = new ArrayList<>();
        Map<Integer, String> created = new TreeMap<>();
        String[] helper;
        String helping;
        if(movedS.length() > 1){
            for(String i : movedS.substring(1,movedS.length()).split(";")){
                helper = i.split(":");
                moved.put(Integer.valueOf(helper[0]),Integer.valueOf(helper[1]));
            }
        }

        if(deletedS.length() > 1){
            for(String i : deletedS.substring(1,deletedS.length()).split(";")){
                deleted.add(Integer.valueOf(i));
            }
        }



        if(createdS.length() > 1){
            for(String i : createdS.substring(1,createdS.length()).split(";")){
                helper = i.split(":");
                helping = "";
                for(String c : helper[1].split("=")){
                    helping+= (char) Integer.parseInt(c.replace(".","") );
                }
                created.put(Integer.parseInt(helper[0]),helping);

            }

        }



        return new deserializeHelper(moved,deleted,created);
    }

    public String toString(){
        String ret = "";

        for(Line i : myLines){
            ret = ret + i.toString()+"\n";
        }

        return ret;
    }

    public String toStringP(){
        return "===============\n"+this.toString()+"======================\n";
    }



}
