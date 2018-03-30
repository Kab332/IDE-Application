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

public class TextEditorAI {
    private String _newValue;
    private String _change;
    private String[] brackets = {"()","{}","[]","<>","''"};
    private String[] specialBrackets = {"()","{}","[]"};
    private int caretPosition = 0;
    private boolean inBracket = false;
    private int closingBracketPosition = -1;
    private int openingBracketPosition = -1;
    public boolean test = true;

    //private final String newValue = null;

    TextEditorAI(){}

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
        setCaretPosition(getCaretPosition(text));
        System.out.println(caretPosition);
        set_change(text.getText(caretPosition, caretPosition+1));
        System.out.println(_change);
        set_change(_newValue.substring(caretPosition,caretPosition+1));
        System.out.println("new change: " +_change);

        if(test) {
            if (_change.equals("{")) {
                test = false;
                System.out.println("this is a bracket");
                //Inserts the close bracket after the open bracket
                text.insertText(caretPosition+1, "}");
                //set the cursor position to in between the brackets
                Platform.runLater( new Runnable() {
                    @Override
                    public void run() {
                        text.positionCaret(getCaretPosition(text)-1);
                    }
                });
            }if(_change.equals("\n")){
                if(get_change(getCaretPosition(text)-1).equals("{")){
                    //tab the next line
                    text.insertText(caretPosition+1, "\t");

                    System.out.println("new line and tab");
                }
                System.out.println("new line");
            }
        } else {
            test = true;
        }

    }
}
