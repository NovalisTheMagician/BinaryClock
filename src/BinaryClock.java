import java.awt.Color;
import java.awt.Graphics2D;

import main.engine.Engine;
import main.engine.InputHandler;

public class BinaryClock extends Engine
{
	private static final long serialVersionUID = 6152580868375750751L;

	private int time;
	
	public static final int SECOND_MASK = 0xff;			// 0x0000ff
	public static final int MINUTE_MASK = 0xff << 8;	// 0x00ff00
	public static final int HOUR_MASK 	= 0xff << 16;	// 0xff0000
	
	public static final int SECOND_SHIFT 	= 0;
	public static final int MINUTE_SHIFT 	= 8;
	public static final int HOUR_SHIFT 		= 16;
	
	private final int DIST_BORD_X = 16;
	private final int DIST_BORD_Y = 16;
	
	private final int LED_SIZE = 32;
	
	private final int LED_ADVANCE_X = 48;
	private final int LED_ADVANCE_Y = 64;
	
	private final Color lightColor, darkColor;
	
	public BinaryClock()
	{
		super("Binary Clock", 400, 256, false);
		
		lightColor = new Color(0x00CDFF); //BLUE
		//lightColor = new Color(0x00FF0D); //GREEN
		//lightColor = new Color(0xFF0D00); //RED
		
		darkColor = new Color(0x00268C); //BLUE
		//darkColor = new Color(0x00490D); //GREEN
		//darkColor = new Color(0x5A0D00); //RED
	}
	
	@Override
	protected void init()
	{
	}

	@Override
	protected void update(float delta, InputHandler input)
	{
		if(input.isButtonDown(3))
			exit();
		
		time = 0;
		
		long curTime = System.currentTimeMillis();
		
		long sec 	= curTime / 1000 % 60;
		long min 	= curTime / 1000 / 60 % 60;
		long hour 	= (curTime / 1000 / 60 / 60 + 1) % 24;
		
		time |= (hour << 16);
		time |= (min << 8);
		time |= sec;
	}

	@Override
	protected void draw(Graphics2D g)
	{
		int j = 0;
		
		int hourTens = getTime(time, BinaryClock.HOUR_MASK, BinaryClock.HOUR_SHIFT) / 10;
		int hour = getTime(time, BinaryClock.HOUR_MASK, BinaryClock.HOUR_SHIFT) - hourTens * 10;
		
		int minTens = getTime(time, BinaryClock.MINUTE_MASK, BinaryClock.MINUTE_SHIFT) / 10;
		int min = getTime(time, BinaryClock.MINUTE_MASK, BinaryClock.MINUTE_SHIFT) - minTens * 10;
		
		int secTens = getTime(time, BinaryClock.SECOND_MASK, BinaryClock.SECOND_SHIFT) / 10;
		int sec = getTime(time, BinaryClock.SECOND_MASK, BinaryClock.SECOND_SHIFT) - secTens * 10;
		
		//HOUR tens
		for(int i = 0, b = 8; i < 4; ++i, b /= 2)
		{
			if(i == 0 || i == 1)
				continue;
			
			g.setColor(darkColor);
			
			if((b & hourTens) > 0)
				g.setColor(lightColor);
			
			g.fillRect(j * LED_ADVANCE_X + DIST_BORD_X, i * LED_ADVANCE_Y + DIST_BORD_Y, LED_SIZE, LED_SIZE);
		}
		
		j++;
		
		//HOUR units
		for(int i = 0, b = 8; i < 4; ++i, b /= 2)
		{	
			g.setColor(darkColor);
			
			if((b & hour) > 0)
				g.setColor(lightColor);
			
			g.fillRect(j * LED_ADVANCE_X + DIST_BORD_X, i * LED_ADVANCE_Y + DIST_BORD_Y, LED_SIZE, LED_SIZE);
		}
		
		j += 2;
		
		//MIN tens
		for(int i = 0, b = 8; i < 4; ++i, b /= 2)
		{	
			if(i == 0)
				continue;
			
			g.setColor(darkColor);
			
			if((b & minTens) > 0)
				g.setColor(lightColor);
			
			g.fillRect(j * LED_ADVANCE_X + DIST_BORD_X, i * LED_ADVANCE_Y + DIST_BORD_Y, LED_SIZE, LED_SIZE);
		}
		
		j++;
		
		//MIN units
		for(int i = 0, b = 8; i < 4; ++i, b /= 2)
		{	
			g.setColor(darkColor);
			
			if((b & min) > 0)
				g.setColor(lightColor);
			
			g.fillRect(j * LED_ADVANCE_X + DIST_BORD_X, i * LED_ADVANCE_Y + DIST_BORD_Y, LED_SIZE, LED_SIZE);
		}
		
		j += 2;
		
		//SEC tens
		for(int i = 0, b = 8; i < 4; ++i, b /= 2)
		{	
			if(i == 0)
				continue;
			
			g.setColor(darkColor);
			
			if((b & secTens) > 0)
				g.setColor(lightColor);
			
			g.fillRect(j * LED_ADVANCE_X + DIST_BORD_X, i * LED_ADVANCE_Y + DIST_BORD_Y, LED_SIZE, LED_SIZE);
		}
		
		j++;
		
		//SEC units
		for(int i = 0, b = 8; i < 4; ++i, b /= 2)
		{	
			g.setColor(darkColor);
			
			if((b & sec) > 0)
				g.setColor(lightColor);
			
			g.fillRect(j * LED_ADVANCE_X + DIST_BORD_X, i * LED_ADVANCE_Y + DIST_BORD_Y, LED_SIZE, LED_SIZE);
		}
	}

	private int getTime(int time, int mask, int shift)
	{
		return (time & mask) >> shift;
	}
	
	public static void main(String... args) throws InterruptedException
	{
		new BinaryClock().run();
		System.exit(0);
	}

}
