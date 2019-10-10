import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Matrix extends JButton{

	private int col;
	private int row;	
	private int state;
	public Matrix(Icon icon, int row, int col) {
		super(icon);
		this.col = col;
		this.row = row;
		this.state = 1;
	}
	public Matrix(int row, int col) {
		super();
		this.col = col;
		this.row = row;
		this.state = 1;
			
		}	
	public int getRow() {
		return row;
	}
	public int getCol() {
		return col;
	}
	
	public void setBackground() {
		this.setBackground(new Color(102,51,0));
		this.state = 1;
		
	}
	public void changeColor() {
		this.setBackground(Color.white);
		this.state = 0;
	}
	
	public int getState() {
		return state;
	}
	
}


