package sample;

public class Line {

    public String line;

    public void setLine(String s){
        this.line = s;
    }

    public Line(String line){
        this.line = line;
    }

    public boolean isDifferent(String toCheck){
        return toCheck.equals(line);
    }

    public String toString(){
        return line;
    }
}
