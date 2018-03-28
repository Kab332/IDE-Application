package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Document {

    public String sinceLast;
    /**
     * sinceLast:
     *      r:5 removed 5
     *      a:9 added 9
     *      e:3 edit on line 3
     */


    public List<Line> myLines;
    public int numOfLines = -1;

    public Document(){
        myLines = new ArrayList<>();
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

    public void changed(String inBundle) {
        List<String> newBundle = new ArrayList<>(Arrays.asList(inBundle.split("\n")));



    }


    public String toString(){
        String ret = "";

        for(Line i : myLines){
            ret = ret + i.toString();
        }

        return ret;
    }



}
