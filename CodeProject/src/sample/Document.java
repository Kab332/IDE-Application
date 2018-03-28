package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


public class Document {

    public String sinceLast;
    /**
     * sinceLast:
     *      r:5 removed 5
     *      a:9 added 9
     *      e:3 edit on line 3
     */


    public List<Line> myLines;
    public int numOfLines = 0;

    public Document(){
        myLines = new ArrayList<>();
    }

    public Document(String s){
        myLines = new ArrayList<>();

        for(String i : s.split(System.getProperty("line.separator"))){
            this.addLine(i);
        }
    }

    /**
     * removes a line at a certain index
     *
     * @param index     index to remove
     */
    public void removeLine(int index){
        if(numOfLines >= index) {
            myLines.remove(index);
            numOfLines -= 1;
            sinceLast+=";r:"+index;
        }
    }


    /**
     * Adds a line at a specific index
     *
     * @param index         index where line will be added
     * @param toBeAdded     string to be added
     */
    public void addLine(int index, String toBeAdded){

        if(numOfLines < index){
            myLines.add(new Line(toBeAdded));
        }else{
            myLines.add(index, new Line(toBeAdded));
        }
        numOfLines+=1;
        sinceLast+=";a:"+index;
    }

    public void addLine(String toBeAdded){
        myLines.add(new Line(toBeAdded));
        numOfLines+=1;
        sinceLast+=";a:"+ (numOfLines-2);
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

    public Map<Integer, String> getCreations(List<String> newBundle, List<String> oldBundle){
        Map<Integer,String> creations = new TreeMap<>();


        int in = 0;
        for(String i: newBundle){
            if(i.trim().length() == 0){
                in +=1;
                continue;
            }
            System.out.println("["+i);
            int index = oldBundle.indexOf(i);
            if(index == -1){
                creations.put(in,i);
            }
            in +=1;
        }

        return creations;
    }

    public String serializeChanges(Map<Integer, Integer> movements, List<Integer> deletes, Map<Integer,String> creations){
        String ret = "$M$";
        for(Map.Entry<Integer, Integer> entry : movements.entrySet()){
            ret += entry.getKey() + ":" + entry.getValue()+";";
        }
        ret += "$D$";
        for(int i: deletes){
            ret += i+";";
        }
        ret += "$C$";
        for(Map.Entry<Integer, String> entry : creations.entrySet()){
            ret += entry.getKey()+":"+entry.getValue().getBytes().toString()+";";
        }
        return ret;
    }

    public void changed(String inBundle) {
        Map<Integer, String>  creates;
        Map<Integer, Integer> movements;
        List<Integer> deletes = new ArrayList<>();
        getMovementsHelper help;

        List<String> newBundle = new ArrayList<>(Arrays.asList(inBundle.split("\n")));
        List<String> oldBundle = new ArrayList<>(Arrays.asList(this.toString().split("\n")));

        help = getMovements(newBundle,oldBundle);
        movements = help.movements;
        creates = getCreations(newBundle,oldBundle);
        deletes = help.deletes;

//        System.out.println("Movements: "+movements);
//        System.out.println("Creations: "+creates);
//        System.out.println("Deletions: "+deletes);
//        System.out.println(oldBundle);
//        System.out.println(newBundle);

        /** order of serialization
         * Movement
         * Deletion
         * Creation
         */
        String serialized = serializeChanges(movements,deletes,creates);

        //Server.send(serialized);





    }

    public void change(String serialized){

    }

    public String toString(){
        String ret = "";

        for(Line i : myLines){
            ret = ret + i.toString();
        }

        return ret;
    }

    public String toStringP(){
        return "===============\n"+this.toString()+"======================\n";
    }



}
