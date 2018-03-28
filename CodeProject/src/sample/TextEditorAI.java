package sample;

/////////
/////////IMportant: try edditing change to reflect caret position
/////////

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.awt.*;

public class TextEditorAI {
    private String _oldValue;
    private String _newValue;
    private String _change;
    private String[] brackets = {"()","{}","[]","<>","''"};
    private String[] specialBrackets = {"()","{}","[]"};
    private int caretPosition = 0;
    private boolean inBracket = false;
    //private final String newValue = null;

    TextEditorAI(){}

    //accessor functions
    public String get_newValue() {
        return _newValue;
    }
    public String get_oldValue() {
        return _oldValue;
    }
    public String get_change() {
        return _change;
    }
    public int getCaretPosition(){
        return caretPosition;
    }


    //mutator functions
    public void set_newValue(String _newValue) {
        this._newValue = _newValue;
    }
    public void set_oldValue(String _oldValue) {
        this._oldValue = _oldValue;
    }

    private void set_change() {
        this._change = _newValue.substring(_oldValue.length(), ++caretPosition);
    }
//    private void set_change() {
//        this._change = _newValue.substring(_oldValue.length(), _newValue.length());
//    }
    private void set_change(int pos) {
        this._change = _newValue.substring(pos, caretPosition);
    }
    private void setCaretPosition(int position){
        caretPosition = position;
    }

    private void setInBracket(boolean inBracket) {
        this.inBracket = inBracket;
    }

    public void arrowKeyListener(TextArea text){
        text.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.LEFT)  {
                    if(caretPosition <= 0){
                        caretPosition = 0;
                    }else{
                        System.out.println("left");
                        setCaretPosition(--caretPosition);
                    }
                    System.out.println(caretPosition);
                    //System.out.println();
                }
                else if (keyEvent.getCode() == KeyCode.RIGHT)  {
                    if(caretPosition == _newValue.length()){

                    }
                    else {
                        System.out.println("right");
                        setCaretPosition(++caretPosition);
                    }

                    System.out.println(caretPosition);
                    //System.out.println();
                }
            }
        });
    }

    public void textAreaListener(javafx.scene.control.TextArea text){
        setCaretPosition(text.getCaretPosition());
        if(caretPosition == 0){
            set_change(0);
        }
//        else if(inBracket){
//            System.out.println("its in a bracket");
//            set_oldValue(_oldValue.substring(0,_oldValue.length()-2));
//        }
        else{
            set_change();
        }


        final String newV = _newValue;

        System.out.println("OLD:" + _oldValue);
        System.out.println("New:" + _newValue);
        System.out.println(inBracket);
        System.out.println("caret pos: " + caretPosition);
        //System.out.println(inBracket);


        System.out.println(getCaretPosition());
        if(_change.equals("{")){
            inBracket = true;
        }else if (_change.equals("\n") && inBracket){
            inBracket = true;
            _newValue += "\t";
            text.setText(_newValue);
        }
        else if(_change.equals("}")){
            inBracket = false;
        }



//        System.out.println("change: " + _change);
//        //System.out.println("Old Value: " + _oldValue.length());
//        //System.out.println("caret possition: " + caretPosition);
//        for (String s: brackets) {
//            //System.out.println(s.substring(0,1));
//            if(_change.equals(s.substring(0,1))){
//
//
//                boolean inBracket = true;
//                System.out.println("Hello");
//                text.setText(_newValue + s.substring(1));
//                //if()
//                Platform.runLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        //System.out.println("this is a test");
//                        text.positionCaret(newV.length());
//                    }
//                });
//                //System.out.println("OLD:" + _oldValue);
//                //System.out.println("New:" + _newValue);
//
//                //System.out.println("OLD:" + _oldValue);
//                //System.out.println("New change: " + _change);
//                System.out.println("New caret possition: " + caretPosition);
//
//
//
//
//
//            }
//
//
//            //text.positionCaret( newValue.length()-1 );
//        }



    }
}
