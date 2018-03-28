package sample;

import java.util.List;

public class Document {

    public List<Line> myLines;

    public void removeLine(int index){
        myLines.remove(index);
    }

    public void addLine(int index, String toBeAdded){
        myLines.add(index, new Line(toBeAdded));
    }

}
