import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class EightQueens extends Applet implements MouseListener, MouseMotionListener, Runnable, ActionListener
{
	private static final long serialVersionUID = 1L;
	
	static final int NUMROWS = 8;
	static final int NUMCOLS = 8;
	static final int SQUAREWIDTH = 50;
	static final int SQUAREHEIGHT = 50;
	static final int BOARDLEFT = 50;
	static final int BOARDTOP = 50;
	int m_nBoard[][] = new int[8][8];
	
	MediaTracker tracker = new MediaTracker(this);
	BufferedImage m_imgQueen;
	Button m_objButton = new Button("Solve");
	Image m_objOffscreen;

	boolean m_bClash;
	boolean m_bSolving;
	String m_strStatus = "";
	int m_nCount = 0;
	
	Thread m_objThread;

	public void init()
	{
		addMouseListener(this);
        setSize(1020,700);
        add(m_objButton);
        m_objButton.addActionListener(this);
        
        m_objThread = new Thread(this);
        m_objThread.start();
         
        try 
        {
			m_imgQueen = ImageIO.read(EightQueens.class.getResourceAsStream("queen.png"));
			tracker.addImage(m_imgQueen, 100);
			tracker.waitForAll();
		} 
        catch (Exception e) {}
	}
	
	public void paint (Graphics canvas)
	{
		m_bClash = false;
		DrawSquares( canvas );
		canvas.setColor(Color.RED);
		CheckColumns( canvas );
		CheckRows( canvas );
		CheckDiagonal1( canvas );
		CheckDiagonal2( canvas );
		canvas.setColor(Color.BLUE);
		canvas.drawString(m_strStatus, 
			BOARDLEFT, BOARDTOP + SQUAREHEIGHT * 8 + 20);
	}

	void DrawSquares( Graphics canvas )
	{
		canvas.setColor(Color.BLACK);
		for( int nRow=0; nRow<NUMROWS; nRow++ )
		{
			for( int nCol=0; nCol<NUMCOLS; nCol++ )
			{
				canvas.drawRect( BOARDLEFT + nCol * SQUAREWIDTH,
					BOARDTOP + nRow * SQUAREHEIGHT, SQUAREWIDTH, SQUAREHEIGHT );
					
				if( m_nBoard[nRow][nCol] != 0 )
				{
					canvas.drawImage( m_imgQueen,
						BOARDLEFT + nCol * SQUAREWIDTH + 8, BOARDTOP + nRow * SQUAREHEIGHT + 6, null );
				}
			}
		}
	}
		
	void CheckColumns( Graphics canvas )
	{
		// Check all columns
		for(  int nCol=0; nCol<NUMCOLS; nCol++ )
		{
			int nColCount = 0;
			for( int nRow=0; nRow<NUMROWS; nRow++ )
			{
				if( m_nBoard[nRow][nCol] != 0 )
				{
					nColCount++;
				}
			}
			if( nColCount > 1 )
			{
				canvas.drawLine( BOARDLEFT + nCol * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
					BOARDTOP + ( SQUAREHEIGHT / 2 ),	
					BOARDLEFT + nCol * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
					BOARDTOP + SQUAREHEIGHT * 7 + ( SQUAREHEIGHT / 2 ) );
					
				m_bClash = true;
			}
		}
	}

	void CheckRows( Graphics canvas )
	{
		for(  int nRow=0; nRow<NUMROWS; nRow++ )
		{
			int nRowCount = 0;
			for( int nCol=0; nCol<NUMCOLS; nCol++ )
			{
				if( m_nBoard[nRow][nCol] != 0 )
				{
					nRowCount++;
				}
			}
			if( nRowCount > 1 )
			{
				canvas.drawLine( BOARDLEFT + ( SQUAREWIDTH / 2 ),
					BOARDTOP + nRow * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ),	
					BOARDLEFT + 7 * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
					BOARDTOP + nRow * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ) );
					
				m_bClash = true;
			}
		}
	}
		
	void CheckDiagonal1( Graphics canvas )
	{
		// Check diagonal 1
		
		for( int nRow=NUMROWS-1; nRow>=0; nRow-- )
		{
			int nCol = 0;
				
			int nThisRow = nRow;
			int nThisCol = nCol;

			int nColCount = 0;
				
			while( nThisCol < NUMCOLS &&
				nThisRow < NUMROWS )
			{
				if( m_nBoard[nThisRow][nThisCol] != 0 )
				{
					nColCount++;
				}
				nThisCol++;
				nThisRow++;
			}
				
			if( nColCount > 1 )
			{
				canvas.drawLine( BOARDLEFT + nCol * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
						BOARDTOP + nRow * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ),	
						BOARDLEFT + ( nThisCol - 1 ) * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
						BOARDTOP + ( nThisRow - 1 ) * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ) );
					
				m_bClash = true;
			}
		}

		for( int nCol=1; nCol<NUMCOLS; nCol++)
		{
			int nRow = 0;
			
			int nThisRow = nRow;
			int nThisCol = nCol;

			int nColCount = 0;
				
			while( nThisCol < NUMCOLS &&
				nThisRow < NUMROWS )
			{
				if( m_nBoard[nThisRow][nThisCol] != 0 )
				{
					nColCount++;
				}
				nThisCol++;
				nThisRow++;
			}
				
			if( nColCount > 1 )
			{
				canvas.drawLine( BOARDLEFT + nCol * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
						BOARDTOP + nRow * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ),	
						BOARDLEFT + ( nThisCol - 1 ) * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
						BOARDTOP + ( nThisRow - 1 ) * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ) );
					
				m_bClash = true;
			}
		}
	}
		
	void CheckDiagonal2( Graphics canvas )
	{
		// Check diagonal 2
			
		for( int nRow=NUMROWS-1; nRow>=0; nRow-- )
		{
			int nCol = NUMCOLS - 1;
				
			int nThisRow = nRow;
			int nThisCol = nCol;

			int nColCount = 0;
				
			while( nThisCol >= 0 &&
				nThisRow < NUMROWS )
			{
				if( m_nBoard[nThisRow][nThisCol] != 0 )
				{
					nColCount++;
				}
				nThisCol--;
				nThisRow++;
			}
				
			if( nColCount > 1 )
			{
				canvas.drawLine( BOARDLEFT + nCol * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
						BOARDTOP + nRow * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ),	
						BOARDLEFT + ( nThisCol + 1 ) * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
						BOARDTOP + ( nThisRow - 1 ) * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ) );
					
				m_bClash = true;
			}
		}

		for( int nCol=NUMCOLS-1; nCol>=0; nCol--)
		{
			int nRow = 0;
			
			int nThisRow = nRow;
			int nThisCol = nCol;

			int nColCount = 0;
				
			while( nThisCol >= 0 &&
				nThisRow < NUMROWS )
			{
				if( m_nBoard[nThisRow][nThisCol] != 0 )
				{
					nColCount++;
				}
				nThisCol--;
				nThisRow++;
			}
				
			if( nColCount > 1 )
			{
				canvas.drawLine( BOARDLEFT + nCol * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
						BOARDTOP + nRow * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ),	
						BOARDLEFT + ( nThisCol + 1 ) * SQUAREWIDTH + ( SQUAREWIDTH / 2 ),
						BOARDTOP + ( nThisRow - 1 ) * SQUAREHEIGHT + ( SQUAREHEIGHT / 2 ) );
					
				m_bClash = true;
			}
				
		}
	}

	public boolean SolveIt(int nCol) 
	{
		if (nCol >= NUMCOLS)
			return false;
		for (int nRow = 0; nRow<NUMROWS; nRow++)
		{
			m_nCount++;
			m_strStatus = "Placing Piece";
			
			m_nBoard[nRow][nCol] = 1;
			
			Graphics g = getGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, 1020, 700);
			paint(g);
			getGraphics().drawImage(m_objOffscreen, 0, 0, null);
			
			try
			{
				Thread.sleep(100);
			}
			catch (Exception e){}
		
			if (m_bClash)
			{
				m_strStatus = "Conflict, Removing Piece";
				m_nBoard[nRow][nCol] = 0;
			} else 
			{
				boolean result = SolveIt(nCol+1);
				if (result) {
					return true;
				} else
				{
					m_nBoard[nRow][nCol] = 0;
					
					g = getGraphics();
					g.setColor(Color.white);
					g.fillRect(0, 0, 1020, 700);
					paint(g);
					getGraphics().drawImage(m_objOffscreen, 0, 0, null);
					
					try
					{
						Thread.sleep(100);
					}
					catch (Exception e){}
				}
			}
		}
		return false;
	}
	
	public void repaint()
	{
		Graphics g = m_objOffscreen.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, 1020, 700);
		paint(g);
		getGraphics().drawImage(m_objOffscreen, 0, 0, null);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		int nCol = (arg0.getX() - BOARDLEFT) / SQUAREWIDTH;
		int nRow = (arg0.getY() - BOARDLEFT) / SQUAREHEIGHT;
		if (nCol >= 0 &&
			nRow >= 0 &&
			nCol < NUMCOLS &&
			nRow < NUMROWS)
		{
			m_nBoard[nRow][nCol] ^= 1;
			
			Graphics g = getGraphics();
			g.setColor(Color.white);
			g.fillRect(0, 0, 1020, 700);
			paint(g);
			getGraphics().drawImage(m_objOffscreen, 0, 0, null);		
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		m_objButton.setEnabled(false);
		m_bClash = false;
		Graphics g = getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, 1020, 700);
		paint(g);
		getGraphics().drawImage(m_objOffscreen, 0, 0, null);
		m_bSolving = true;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
			if (m_bSolving)
			{
				SolveIt(0);
				m_bSolving = false;
				m_objButton.setEnabled(true);
			}			
			try {
				Thread.sleep(100);
			} catch (Exception e) {}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}


