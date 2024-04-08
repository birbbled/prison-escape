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
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.util.Scanner;

public class GraphicsPanel extends JPanel implements KeyListener, MouseListener, ActionListener{

	private Timer timer;					// The timer is used to move objects at a consistent time interval.

	private Background background1;			// The background object will display a picture in the background.
	private Background background2;			// There has to be two background objects for scrolling.


	public int actualState = 0;
	Graphics2D g2;

	private final int titleState = 0;
	private final int leaderboard = 1;
	private final int playState = 2;
	private final int loseState = 3;
	private final int winState = 4; 
	private final int keyState = 5;

	private Sprite sprite;					// create a Sprite object
	private Item guardSpeech;
	private Rectangle a;
	private Rectangle b;
	private Rectangle c = new Rectangle(870, 570, 100, 50);
	private Rectangle d = new Rectangle(870, 570, 100, 50);
	private int top1;
	private int top2;
	private int top3;

	//private int plays;
	private int countdown;

	String text = "";
	int user = 1;


	private double elapseTime = 0;


	// pretty much any image that you would like by passing it
	// the path for the image.
	private ArrayList<Item> items;
	private ArrayList<Double> times;
	public ArrayList<String> leaders;
	private int boxCounter;
	
	
	JButton clickMeButton;
	public String name;
	
	JFrame frame = new JFrame("Welcome!");
	JTextField textfield = new JTextField(" Enter your name!", 10);
	JButton button = new JButton("Click me");


	public GraphicsPanel(){
		//gui = new GUI();
		top1 = 0;
		top2 = 1;
		top3 = 2;

		countdown = 200;
		background1 = new Background();	// You can set the background variable equal to an instance of any of  
		background2 = new Background(-background1.getImage().getIconWidth());								

		this.addMouseListener(this);
		this.setFocusable(true);
		// The Item constructor has 4 parameters - the x coordinate, y coordinate
		// the path for the image, and the scale. The scale is used to make the
		// image smaller, so the bigger the scale, the smaller the image will be.
		items = new ArrayList<Item>();
		times = new ArrayList<Double>();
		leaders = new ArrayList<String>();
		
		leaders.add(" ");
		times.add(999.9);
		leaders.add(" ");
		times.add(999.9);
		leaders.add(" ");
		times.add(999.9);
		
		

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
		
		frame.add(textfield);
		textfield.setEditable(true);
		textfield.getAccessibleContext();
		
		textfield.setLocation(660, 270);
		name = (textfield.getText());
		
		//System.out.println(name);
		//frame.setTitle("Leaderboard");
		//frame.setDefaultCloseOperation();
		frame.pack();
		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
	
		
		button.setPreferredSize(new Dimension(10,10));
	//	System.out.println(frame.getBounds());
		button.setLocation(580,290);
		
		frame.add(button);
		button.addActionListener(this);
		
		
		if (actualState == titleState) {
			frame.setVisible(true);
			
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

		
			SwingUtilities.invokeLater(new Runnable(){
				public void run() {
					new TextField();
				}
			});
		}

		else if (actualState == leaderboard) {
			for (int i = 0 ; i < times.size(); i++) {
				System.out.println(times.get(i));
			}
			Item gj = new Item(600, 350, "images/background/gj2.png", 2);  
			gj.draw(g2, this);

			//Collections.sort(ints);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 35)); 
			g2.drawString("LEADERBOARD", 200, 100);

			if (times.get(top1) == 999.9) {
				g2.drawString("1st- " + " USER: " + " "+ "  SCORE: " + 0, 200, 150);
			}
			else {
				g2.drawString("1st- " + " USER: " + leaders.get(top1) + "  SCORE: " + times.get(top1), 200, 150);
			}
			
			
			if (times.get(top2) == 999.9) {
				g2.drawString("2nd- " + " USER: " + " "+ "  SCORE: " + 0, 200, 200);
			}
			else {
				g2.drawString("2nd- " + " USER: " + leaders.get(top2) + "  SCORE: " + times.get(top2), 200, 200);
			}
		
			if (times.get(top3) == 999.9) {
				g2.drawString("3rd- " + " USER: " + " "+ "  SCORE: " + 0, 200, 250);
			}
			else {
				g2.drawString("3rd- " + " USER: " + leaders.get(top3) + "  SCORE: " + times.get(top3), 200, 250);
			}
	

			g2.draw(c);
			g2.fillRect(870, 570, 254, 50);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
			g2.drawString("PLAY GAME!", 900, 588);

		}
		else if (actualState == playState) {
			sprite = new Sprite(50, 340);	
			guardSpeech = new Item(1500, 250, "images/objects/guardSpeech.png", 3); 
			
			guardSpeech.draw(g2, this);
			countdown = 200;
			sprite.resurrect();
			
			
			//System.out.println(sprite.getX());
			background1.draw(this, g);
			background2.draw(this, g);

			sprite.draw(g2, this);
			//guard.draw(g2, this);
			guardSpeech.draw(g2, this);


			g2.setColor(Color.RED);

			for(int i = 0; i < items.size(); i++) {
				items.get(i).draw(g2, this);
				//g2.draw(new Rectangle(items.get(i).getBounds()));
			}

			//g2.drawRect(10, 10, 40, 40);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 35)); 
			g2.drawString("TIME: " + (int)elapseTime, 15, 30);
			
		
		}

		else if (actualState == loseState) {
			Timer timer = new Timer(1000, new ActionListener() {
				  @Override
				  public void actionPerformed(ActionEvent arg0) {
					  actualState = 0;
					  repaint();
				  }
				});  
			//System.out.println(leaders.get(0));
			
			Item title = new Item(290, 100, "images/background/lose.png", 2);  
			title.draw(g2, this);

			sprite.die();
			//System.out.println(elapseTime);
			
			elapseTime = 0;
			
			for (int i = items.size() -1; i >= 0; i--) {
				items.remove(i);
			}
			
			timer.setRepeats(false);
			timer.start();
			
		}
		
		else if (actualState == 4) {
			Item win = new Item(100, 200, "images/background/Finish.png", 4);
			win.draw(g2, this);
			g2.draw(d);
			g2.fillRect(870, 570, 254, 50);
			g2.setColor(Color.WHITE);
			g2.setFont(new Font("TimesRoman", Font.PLAIN, 25)); 
			g2.drawString("PLAY GAME!", 900, 588);
			
			leaders.add(getName());
			System.out.println(elapseTime);
			times.add(round(elapseTime, 2));
			
			for (int i = 0; i < times.size(); i++) {
				//System.out.println(ints.get(i));
				if (times.get(i) < times.get(top1)) {
					top1 = i;
					System.out.println("PLEASE");
				}
			}
			for (int i = 0; i < times.size(); i++) {
				if (times.size() > 4 && times.get(i) < times.get(top2) && times.get(i) > times.get(top1)) {
					top2 = i;
				}
			}
			
			for (int i = 0; i<times.size(); i++) {
				if (times.size() > 5 && times.get(i) < times.get(top3) && times.get(i) > times.get(top2)) {
					top3 = i;
				}
			}
			
			elapseTime = 0;
		}
		
		else if (actualState == 5) {
			Item key = new Item(100, 200, "images/objects/key.png", 2);
			key.draw(g2, this);
			g2.drawString("Congrats! You stole the key and escaped!", 100, 200);
			
			Timer timer = new Timer(3000, new ActionListener() {
				  @Override
				  public void actionPerformed(ActionEvent arg0) {
					  actualState = 4;
					  repaint();
				  }
				});  
			
			timer.setRepeats(false);
			timer.start();
		}

	}

	// method:clock
	// description: This method is called by the clocklistener every 5 milliseconds.  You should update the coordinates
	//				of one of your characters in this method so that it moves as time changes.  After you update the
	//				coordinates you should repaint the panel. 
	public void clock(){
		// You can move any of your objects by calling their move methods.

		if (elapseTime > 10) {
			if(countdown >= 150 && sprite.getX() >300) {
				countdown -= 1;
			}
		}
		if (actualState == playState) {
			background1.move();
			background2.move();
			sprite.move(this);

			boxCounter++;
			//guardTime++;
			
			if ((int)elapseTime % 10 <= 2 ) {
				//System.out.println("guard appears");
				guardSpeech.x_coordinate = 850;
			}
			else {
				guardSpeech.x_coordinate = 1500;
				repaint();	
			}
			if(boxCounter % countdown == 0) {
				int random = (int) ((Math.random() * 100) +1);
				if (random >55) {
					items.add(new Item(background1.getImage().getIconWidth() - 100, 
							(int)(Math.random() * background1.getImage().getIconHeight()), "images/objects/Cinderblock.png", 15));

				}
				else {
					items.add(new Item(background1.getImage().getIconWidth() - 100, 
							400, "images/objects/small pile.png", 20));
				}
			}
				

			elapseTime+=.005;
			//System.out.println((int)(elapseTime));

			// You can also check to see if two objects intersect like this. In this case if the sprite collides with the
			// item, the item will get smaller. 

			for (int i = items.size()-1; i >= 0; i--) {
				items.get(i).move(this);
				if (elapseTime > 10) {
					items.get(i).x_direction = -3;
					if (sprite.x_coordinate > 300) {
						if (elapseTime % countdown == 2) {
							items.add(new Item(background1.getImage().getIconWidth() - 100, 
									(int)(Math.random() * background1.getImage().getIconHeight()), "images/objects/Cinderblock.png", 15));
						}
						
					}
				}
				if (items.get(i).x_coordinate <= 0) {
					items.remove(i);
					
					
				}
				
				else if(sprite.collision(items.get(i))) {
					System.out.println("stop");
					items.remove(i);
					//sprite.die();

					actualState = 3;

					sprite.stopMove();

				}
				else if (sprite.collision(guardSpeech)){
					System.out.println("won!");
					items.remove(i);
					actualState = 5;
					
					sprite.stopMove();
				}
				this.repaint();
			}
//System.out.println(guardTime);
//			if (elapseTime >= guardTime && elapseTime <= period) {
//				guardSpeech.x_coordinate = 850;
//			}
//			else {
//				guardSpeech.x_coordinate = 1500;
//				repaint();	
//			}
//			
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
			else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
				sprite.walkLeft();
			}
			else if(e.getKeyCode() == KeyEvent.VK_SPACE)
				sprite.run();
			else if(e.getKeyCode() == KeyEvent.VK_UP) {
				sprite.walkRight();
				sprite.jump();			
			}
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
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if(evt.getSource() == button) {
			name = textfield.getText();
			//System.out.println(name);
			frame.setVisible(false);
		}
	}
	
	public static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = BigDecimal.valueOf(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	public String getName() {
		return name;
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
			else if(e.getKeyCode() ==  KeyEvent.VK_UP || e.getKeyCode() ==  KeyEvent.VK_DOWN) {
				sprite.stop_Vertical();
				sprite.idle();
			}
				
			else if(e.getKeyCode() ==  KeyEvent.VK_SPACE)
				sprite.slowDown();
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		System.out.println(e.getX() + " " + e.getY());

		if (actualState == 0) {
			if(a.contains(e.getX(), e.getY())) {
				actualState = 2;
				this.repaint();
			}

			if(b.contains(e.getX(), e.getY())) {
				actualState = 1;
				this.repaint();
			}
		}
		

		if (actualState == leaderboard) {
			if(c.contains(e.getX(), e.getY())) {
				actualState = 0;
				repaint();
			}
		}
		if (actualState == winState) {
			if (d.contains(e.getX(), e.getY())){
				actualState = 0;
				repaint();
			}
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