// Class: GraphicsPanel
// Written by: Mr. Swope
// Date: 1/27/2020
// Description: This class is the main class for this project.  It extends the Jpanel class and will be drawn on
// 				on the JPanel in the GraphicsMain class.  
//
// Since you will modify this class you should add comments that describe when and how you modified the class.  

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.util.Scanner;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener{

	private Timer timer;					// The timer is used to move objects at a consistent time interval.

	private Background background1;			// The background object will display a picture in the background.
	private Background background2;			// There has to be two background objects for scrolling.


	public int actualState = 0;
	Graphics2D g2;

	private final int titleState = 0;
	private final int leaderboard = 1;
	private final int playState = 2;
	private final int loseState = 3;
	private final int userState = 4; 

	private Sprite sprite;					// create a Sprite object
	private Guard guard;
	private Rectangle a;
	private Rectangle b;
	private Rectangle c;
	private ArrayList<Integer> ints;
	String text = "";
	int user = 1;
	

	private double elapseTime = 0;


	// pretty much any image that you would like by passing it
	// the path for the image.
	private ArrayList<Item> items;
	private int boxCounter;

	public GraphicsPanel(){

		ints = new ArrayList<Integer>();
		background1 = new Background();	// You can set the background variable equal to an instance of any of  
		background2 = new Background(-background1.getImage().getIconWidth());								

		this.addMouseListener(this);
		this.setFocusable(true);
		// The Item constructor has 4 parameters - the x coordinate, y coordinate
		// the path for the image, and the scale. The scale is used to make the
		// image smaller, so the bigger the scale, the smaller the image will be.
		items = new ArrayList<Item>();

		sprite = new Sprite(50, 340);	
		guard = new Guard(900, 300);
		// The Sprite constuctor has two parameter - - the x coordinate and y coordinate

		setPreferredSize(new Dimension(background1.getImage().getIconWidth(),
				background2.getImage().getIconHeight()));  
		// This line of code sets the dimension of the panel equal to the dimensions
		// of the background image.

		timer = new Timer(5, new ClockListener(this));   // This object will call the ClockListener's
		// action performed method every 5 milliseconds once the 
		// timer is started. You can change how frequently this
		// method is called by changing the first parameter.

		boxCounter = 0;
		timer.start();
		this.setFocusable(true);					     // for keylistener
		this.addKeyListener(this);


	}

	// method: paintComponent
	// description: This method will paint the items onto the graphics panel.  This method is called when the panel is
	//   			first rendered.  It can also be called by this.repaint(). You'll want to draw each of your objects.
	//				This is the only place that you can draw objects.
	// parameters: Graphics g - This object is used to draw your images onto the graphics panel.
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D) g;


		if (actualState == titleState) {
			Item title = new Item(210, 100, "images/background/Title.png", 4);  
			title.draw(g2, this);
			//g2.drawRect(90, 190, 443, 50);
			a = new Rectangle(410, 250, 254, 50);
			g2.draw(a);

			g2.fillRect(410, 250, 254, 50);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 35)); 
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 33)); 


			g2.setColor(Color.WHITE);
			b = new Rectangle(410, 350, 254, 50);
			g2.draw(b);
			g2.fillRect(410, 350, 254, 50);

			g2.drawString("PLAY", 495, 285);

			g2.setColor(Color.BLACK);
			g2.drawString("LEADERBOARD", 413, 385);


		}

		else if (actualState == leaderboard) {
			int index = 0;
			Collections.sort(ints);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 35)); 
			g2.drawString("LEADERBOARD", 200, 100);
			
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 20)); 
			g2.drawString("HIGH SCORE: " + ints.get(ints.size()-1), 200, 150);
//			for (int i = 0; i <ints.size(); i++) {
//				g2.drawString("" + ints.get(i), 200, 150 + 40*index);
//			}
			
			c = new Rectangle(870, 570, 100, 50);
			g2.draw(c);
			g2.fillRect(870, 570, 254, 50);
			g2.setColor(Color.WHITE);
			g2.drawString("PLAY GAME!", 930, 585);
			
		}
		else if (actualState == playState) {
			
			sprite.resurrect();
			background1.draw(this, g);
			background2.draw(this, g);

			sprite.draw(g2, this);
			guard.draw(g2, this);

			g2.setColor(Color.RED);
			Rectangle r = sprite.getBounds();
			Rectangle r1 = guard.getBounds();

			g2.draw(r);
			g2.draw(r1);

			for(int i = 0; i < items.size(); i++) {
				items.get(i).draw(g2, this);
				g2.draw(new Rectangle(items.get(i).getBounds()));
			}

			//g2.drawRect(10, 10, 40, 40);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 35)); 
			g2.drawString("Timer: " + (int)elapseTime, 15, 30);
		}


		else if (actualState == loseState) {
			Item title = new Item(290, 100, "images/background/lose.png", 2);  
			title.draw(g2, this);

			a = new Rectangle(410, 250, 254, 50);
			g2.draw(a);

			g2.fillRect(410, 250, 254, 50);


			g2.setFont(new Font("TimesRoman", Font.PLAIN, 33)); 


			g2.setColor(Color.WHITE);
			b = new Rectangle(410, 350, 254, 50);
			g2.draw(b);
			g2.fillRect(410, 350, 254, 50);

			g2.drawString("PLAY AGAIN", 440, 285);

			g2.setColor(Color.BLACK);
			g2.drawString("LEADERBOARD", 413, 385);

			sprite.die();
			ints.add((int)(elapseTime));
			elapseTime = 0;

		}

		else if (actualState == userState) {
			
			System.out.println("Jello");
			Scanner s = new Scanner(System.in);
		
			
			g.drawRect(0, 1000, 1000, 1000);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 50)); 
			g.drawString("ENTER NAME", 250, 185);


			g2.setColor(Color.blue);
			g2.fillRect(350, 200, 315, 50);
			g2.setColor(Color.black);
			
			System.out.println(text);
			g2.drawString(text, 350, 200);
			
			addKeyListener(new KeyAdapter(){
				  public void keyTyped(KeyEvent e){
					 if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						 actualState = 2;
						 repaint();
					 }
					 else {
						  text += e.getKeyChar();
						    System.out.println(text);
						    repaint();
						  }
					 }
				  
			});
		
			

			// You can play with this code to center the text
			
		

		}

	}

	// method:clock
	// description: This method is called by the clocklistener every 5 milliseconds.  You should update the coordinates
	//				of one of your characters in this method so that it moves as time changes.  After you update the
	//				coordinates you should repaint the panel. 
	public void clock(){
		// You can move any of your objects by calling their move methods.

		if (actualState == playState) {
			background1.move();
			background2.move();
			sprite.move(this);

			boxCounter++;
			if(boxCounter % 200 == 0)
				items.add(new Item(background1.getImage().getIconWidth() - 100, 
						(int)(Math.random() * background1.getImage().getIconHeight()), "images/objects/Cinderblock.png", 15));


			elapseTime+=.01;
			System.out.println((int)(elapseTime));

			// You can also check to see if two objects intersect like this. In this case if the sprite collides with the
			// item, the item will get smaller. 


			for (int i = 0; i < items.size(); i++) {
				items.get(i).move(this);
				if (items.get(i).x_coordinate == 0) {
					items.remove(i);
				}

				if(sprite.collision(items.get(i))) {
					System.out.println("stop");
					items.remove(i);
					//sprite.die();

					actualState = 3;

					sprite.stopMove();

				}
				this.repaint();
			}

		}
	}



	// method: keyPressed()
	// description: This method is called when a key is pressed. You can determine which key is pressed using the 
	//				KeyEvent object.  For example if(e.getKeyCode() == KeyEvent.VK_LEFT) would test to see if
	//				the left key was pressed.
	// parameters: KeyEvent e
	@Override
	public void keyPressed(KeyEvent e) {
		if (actualState == playState) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT)
				sprite.walkRight();
			else if(e.getKeyCode() == KeyEvent.VK_LEFT)
				sprite.walkLeft();
			//	else if(e.getKeyCode() == KeyEvent.VK_UP)
			//sprite.moveUp();
			//else if(e.getKeyCode() == KeyEvent.VK_DOWN && !(sprite.collision(item) && sprite.getY() < item.getY()))
			//	sprite.moveDown();
			else if(e.getKeyCode() == KeyEvent.VK_SPACE)
				sprite.run();
			else if(e.getKeyCode() == KeyEvent.VK_UP)
				sprite.jump();
			//else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			//	sprite.
			//	}
			//			else if(e.getKeyCode() == KeyEvent.VK_D) {
			//				playSound("src/sounds/bump.WAV");
			//				sprite.die();	
			//			}
		
		}
		


	}

	// This function will play the sound "fileName".
	public static void playSound(String fileName) {
		try {
			File url = new File(fileName);
			Clip clip = AudioSystem.getClip();

			AudioInputStream ais = AudioSystem.getAudioInputStream(url);
			clip.open(ais);
			clip.start();
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// method: keyTyped()
	// description: This method is called when a key is pressed and released. It basically combines the keyPressed and
	//              keyReleased functions.  You can determine which key is typed using the KeyEvent object.  
	//				For example if(e.getKeyCode() == KeyEvent.VK_LEFT) would test to see if the left key was typed.
	//				You probably don't want to do much in this method, but instead want to implement the keyPresses and keyReleased methods.
	// parameters: KeyEvent e
	@Override
	public void keyTyped(KeyEvent e) {


	}

	// method: keyReleased()
	// description: This method is called when a key is released. You can determine which key is released using the 
	//				KeyEvent object.  For example if(e.getKeyCode() == KeyEvent.VK_LEFT) would test to see if
	//				the left key was pressed.
	// parameters: KeyEvent e
	@Override
	public void keyReleased(KeyEvent e) {
		if (actualState == playState) {
			if(e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyCode() == KeyEvent.VK_LEFT)
				sprite.idle();
			else if(e.getKeyCode() ==  KeyEvent.VK_UP || e.getKeyCode() ==  KeyEvent.VK_DOWN)
				sprite.stop_Vertical();
			else if(e.getKeyCode() ==  KeyEvent.VK_SPACE)
				sprite.slowDown();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(e.getX() + " " + e.getY());

		if(a.contains(e.getX(), e.getY())) {
			actualState = 2;
			System.out.println(actualState);
			System.out.println(userState);
			this.repaint();
		}

		if(b.contains(e.getX(), e.getY())) {
			actualState = 1;
			this.repaint();
		}
		
		if(c.contains(e.getX(), e.getY())) {
			actualState = 2;
			this.repaint();
		}
		

	}

	
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}