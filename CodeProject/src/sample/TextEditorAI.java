package sample;

/////////
/////////IMportant: try edditing change to reflect caret position
/////////

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jdk.jfr.Name;

import java.awt.*;

public class TextEditorAI{
    private String indentation = "";

    private String _newValue;
    private String _change;
    private String[] brackets = {"()","{}","[]","<>","''","\"\""};
    //private String[] specialBrackets = {"()","{}","[]"};
    public int caretPosition = 0;
    private boolean inBracket = false;
    private int closingBracketPosition = -1;
    private int openingBracketPosition = -1;
    public boolean test = true;
    public boolean tab = false;
    public boolean breakloop = false;

    private TextArea txt;

    //private CarotPosition carotPos;


    //private final String newValue = null;

//    public TextEditorAI(){
//        this.carotPos = new CarotPosition(txt);
//    }

    //accesor methods
    public int getCaretPosition(TextArea text) {
        return text.getCaretPosition();
    }

    public String get_change(int position) {
        return _newValue.substring(position,position+1);
    }

    //mutator functions
    public void set_newValue(String _newValue) {
        this._newValue = _newValue;
    }
    public void set_change(String _change) {
        this._change = _change;
    }

    public void setCaretPosition(int caretPosition) {
        this.caretPosition = caretPosition;
    }

    public void textAreaListener(TextArea text){
        text.caretPositionProperty().addListener((observable, oldValue, newValue) -> {
            //System.out.println("caretPos: "+newValue);
            this.caretPosition = newValue.intValue();
        });

        //carotPosition.start();

        this.txt = text;
        setCaretPosition(getCaretPosition(text));
        //System.out.println(caretPosition);
        set_change(text.getText(caretPosition, caretPosition+1));
        //System.out.println(_change);
        set_change(_newValue.substring(caretPosition,caretPosition+1));
        System.out.println("new change: " +_change);

        System.out.println("tab " +tab);
        System.out.println("test: " + test);



            //System.out.println("bracket test: " + brackets[i]);
            if(test) {
                System.out.println("entered test");
                int i = 0;
                while((i < brackets.length) && (!breakloop)) {
                    if (_change.equals(brackets[i].substring(0, 1))) {
                        System.out.println("IMPORTANT: " + brackets[i].substring(0, 1));
                        test = false;
                        System.out.println("this is a bracket");
                        //Inserts the close bracket after the open bracket
                        text.insertText(caretPosition + 1, brackets[i].substring(1, 2));
                        //set the cursor position to in between the brackets
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                text.positionCaret(getCaretPosition(text) - 1);
                            }
                        });
                    }
                    if (_change.equals("\n")) {
                        if (i < 3){
                            if (get_change(getCaretPosition(text) - (1)).equals(brackets[i].substring(0, 1))) {
                                if(insideBracket()) {
                                    System.out.println("Still inside brackets");
                                    indentation += "1";
                                }
                                //indentation -= "\t";
                                System.out.println("tab: " + tab);
                                if (test) {
                                    test = false;
                                    //tab the next line
                                    text.insertText(getCaretPosition(text), "\n" + indentation);
                                    System.out.println("it tabed");
//                                    if(indentation.endsWith("1")){
//                                        System.out.println("DO YOU WORK?");
//                                        indentation.substring(0,indentation.length()-1).replace("1","");
//                                        System.out.println("does this work?");
//                                        System.out.println("it indeted: " +indentation);
//                                        text.insertText(getCaretPosition(text),indentation);
//                                    }
//                                    Platform.runLater(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            //
//                                            text.positionCaret(getCaretPosition(text) - 1);
//                                        }
//                                    });

                                    System.out.println("new line and tab");
                                } else {
                                    test = true;
                                }
                            }
                        }
                        System.out.println("new line");
                    }
                    i++;
                }
            } else {
                test = true;
            }



    }

    private boolean insideBracket() {
        int pos = getCaretPosition(txt);
        System.out.println(pos);
        return true;
    }



}
