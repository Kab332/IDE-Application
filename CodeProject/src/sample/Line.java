package sample;

public class Line {
    public int lineNumber;
    public boolean hasChanged;
    public String line;

    public Line(String line){
        this.line = line;
    }

    public boolean isDifferent(String toCheck){
        return toCheck.equals(line);
    }

    public String toString(){
        return line+"\n";
    }
}
