package org;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormatSymbols;
import java.util.Scanner;
import javax.swing.JTextField;

public class MatrixTextFields extends JTextField {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private int row, col;
    private Matrix matrix;
    private static KeyListener keyListener;
    private static FocusListener focusListener;

    public MatrixTextFields(int row, int col, Matrix mt, boolean resultSet){

        super();
        this.row = row;
        this.col = col;
        this.matrix = mt;
        setColumns(3);
        setText(String.valueOf(0));
        if(resultSet){
            setEditable(false);
            setText(String.valueOf(mt.getElem(this.row, this.col)));
        }
        keyListener = new KeyListener() {

            char commar = new DecimalFormatSymbols(getDefaultLocale()).getDecimalSeparator();
            char[] allowedKeys = {'1','2','3','4','5','6','7','8','9','0',commar};
            boolean isCommar = false;

            public void keyTyped(KeyEvent arg0) {
                boolean isFine = false;


                for(int i=0; i<allowedKeys.length; i++){

                    if(arg0.getKeyChar()==allowedKeys[i]){
                        isFine = true;
                        if(isCommar&&(arg0.getKeyChar()==commar)){
                            arg0.consume();
                        }
                        if(arg0.getKeyChar()==commar){
                            isCommar = true;
                        }
                        break;
                    }
                }

                if(!isFine){
                    arg0.consume();
                }
            }


            public void keyReleased(KeyEvent arg0) {
                // TODO Auto-generated method stub

            }


            public void keyPressed(KeyEvent arg0) {


            }
        };
        addKeyListener(keyListener);
        focusListener = new FocusListener() {


            public void focusLost(FocusEvent e) {
                MatrixTextFields mt = (MatrixTextFields)e.getSource();
                double value = mt.checkCommar(mt);
                matrix.setElem(getRow(), getCol(), value);
            }

            public void focusGained(FocusEvent e) {
                JTextField tf = (JTextField)e.getSource();
                tf.selectAll();

            }
        };
        addFocusListener(focusListener);

    }
    private double checkCommar(MatrixTextFields field){
        String s = field.getText();
        char commar = new DecimalFormatSymbols(getDefaultLocale()).getDecimalSeparator();
        StringBuilder sb = new StringBuilder(s);

        for(int i=0; i<sb.length();i++){
            if((sb.charAt(i)=='.')||(sb.charAt(i)==',')){
                sb.setCharAt(i, commar);
            }
        }
        return new Scanner(sb.toString()).nextDouble();
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }
}