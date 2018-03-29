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
    private String _oldValue;
    private String _newValue;
    private String _change;
    private String[] brackets = {"()","{}","[]","<>","''"};
    private String[] specialBrackets = {"()","{}","[]"};
    private int caretPosition = 0;
    private boolean inBracket = false;
    private int closingBracketPosition = -1;
    private int openingBracketPosition = -1;

    //private final String newValue = null;

    TextEditorAI(){}

    //accessor functions
    public String get_newValue() {
        return _newValue;
    }
    public String get_oldValue() {
        return _oldValue;
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

    private void set_change(){
        this._change =  _newValue.substring(caretPosition-1,caretPosition);
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
//                    try{
//                        setTextCaret(caretPosition,text);
//                        textAreaListener(text);
//                    }catch (Exception e){
//
//                    }
                    //System.out.println();
                }
                else if (keyEvent.getCode() == KeyCode.RIGHT)  {
                    if(caretPosition >= _newValue.length()){

                    }
                    else {
                        System.out.println("right");
                        setCaretPosition(++caretPosition);
                    }

                    System.out.println(caretPosition);
//                    try{
//                        setTextCaret(caretPosition,text);
//                        textAreaListener(text);
//                    }catch (Exception e){
//
//                    }
                    //System.out.println();
                }
                else if (keyEvent.getCode() == KeyCode.BACK_SPACE)  {
                    if(caretPosition <= _newValue.length()){

                    }
                    else {
                        System.out.println("backspace");
                        setCaretPosition(caretPosition--);
                    }

                    System.out.println(caretPosition);
                    try{
                        setTextCaret(caretPosition,text);
                        textAreaListener(text);
                    }catch (Exception e){

                    }
                    //System.out.println();
                }
                else if (keyEvent.getCode() == KeyCode.DELETE)  {
                    if(caretPosition <= _newValue.length()){

                    }
                    else {
                        System.out.println("delete");
                        setCaretPosition(caretPosition);
                    }

                    System.out.println(caretPosition);
                    try{
                        setTextCaret(caretPosition,text);
                        textAreaListener(text);
                    }catch (Exception e){

                    }
                    //System.out.println();
                }




            }
        });
    }

    private void setTextCaret(int caretPosition, TextArea text){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                System.out.println("caret position reset to: " + caretPosition);
                text.positionCaret(caretPosition);
                //text.setText();
            }
        });
    }

    public void textAreaListener(javafx.scene.control.TextArea text) throws Exception{

        Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("caret position reset to*: " + caretPosition);
                        text.positionCaret(caretPosition);
                        //text.setText();
                    }
                });
        arrowKeyListener(text);

        //sets the caret(blinky line) position equal to its respective position on the text area
        setCaretPosition(text.getCaretPosition());


        //used for the overloaded function
        final String newV = _newValue;


        //Debug Test Outputs
        System.out.println("OLD:" + _oldValue);
        System.out.println("New:" + _newValue);


        //Every time a new key is pressed the position of the cursor is updated

        //setCaretPosition(text.getCaretPosition() + 1);
        caretPosition++;
        set_change();
        System.out.println("Change: " +_change);
        System.out.println("current caret position " + text.getCaretPosition());

        //if(key)

        //checks to see if the cursor is in the between brackets
        if(caretPosition >= closingBracketPosition && closingBracketPosition != -1){
                inBracket = false;
        }
        if(caretPosition <= openingBracketPosition && openingBracketPosition != -1){
            inBracket = false;
        }

        if(_change.equals("{")){
            openingBracketPosition = caretPosition-1;
            if(inBracket){
                System.out.println("We did it");
            }
            else{
                //caretPosition += 1;
                //System.out.println("hello");
                inBracket = true;
                System.out.println(_newValue + "}");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        //System.out.println("this is a test");
                        text.positionCaret(caretPosition);

                    }

                });
                //cursor is at the end of the string
                if(caretPosition  == _newValue.length()){
                    System.out.println("does this even work");
                    caretPosition += 1;
                    closingBracketPosition = caretPosition;
                    text.setText(_newValue + "}");
                }else if(caretPosition < _newValue.length() && caretPosition !=0){
                    text.setText(_newValue.substring(0,caretPosition-1)+"}"+_newValue.substring(caretPosition,_newValue.length()));

                }


                System.out.println(caretPosition);
            }

        }
//        else if(_change.equals("\n")){
//            System.out.println("enter was pressed");
//        }
        else if(_change.equals("\n") && inBracket){
            System.out.println("Hello");
            _newValue += " \t\n";
            inBracket = false;
            text.setText(_newValue);

        }


        //Debug Test Outputs
        System.out.println(inBracket);
        System.out.println("caret pos*: " + caretPosition);




    }
}
