package sample;



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
            this.caretPosition = newValue.intValue();
        });

        this.txt = text;
        setCaretPosition(getCaretPosition(text));
        set_change(text.getText(caretPosition, caretPosition+1));

        set_change(_newValue.substring(caretPosition,caretPosition+1));

        if(test) {
            int i = 0;
            while((i < brackets.length) && (!breakloop)) {
                if (_change.equals(brackets[i].substring(0, 1))) {
                    test = false;
                    text.insertText(caretPosition + 1, brackets[i].substring(1, 2));
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            text.positionCaret(getCaretPosition(text) - 1);
                        }
                    });
                }
                if (_change.equals("\n")) {
                    if (i == 2){
                        if (get_change(getCaretPosition(text) - (1)).equals(brackets[i].substring(0, 1))) {
                            indentation += "1";
                            if (test) {
                                test = false;
                                text.insertText(getCaretPosition(text), "\n" + indentation);
                            } else {
                                test = true;
                            }
                        }
                    }
                }
                i++;
            }
        } else {
            test = true;
        }

    }




}
