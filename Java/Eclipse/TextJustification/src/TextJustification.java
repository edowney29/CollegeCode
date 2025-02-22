import java.applet.Applet;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

public class TextJustification extends Applet implements MouseListener
{
	private static final long serialVersionUID = 9014911823384821878L;

	String strHamlet = "To be, or not to be, that is the question: Whether 'tis Nobler in the mind to suffer the Slings and Arrows of outrageous Fortune, Or to take Arms against a Sea of troubles, And by opposing end them: to die, to sleep No more; and by a sleep, to say we end The Heart-ache, and the thousand Natural shocks That Flesh is heir to? 'Tis a consummation Devoutly to be wished. To die, to sleep, To sleep, perchance to Dream; aye, there's the rub, For in that sleep of death, what dreams may come, When we have shuffled off this mortal coil, Must give us pause. There's the respect That makes Calamity of so long life: For who would bear the Whips and Scorns of time, The Oppressor's wrong, the proud man's Contumely, The pangs of despised Love, the Law�s delay, The insolence of Office, and the Spurns That patient merit of the unworthy takes, When he himself might his Quietus make With a bare Bodkin? Who would Fardels bear, To grunt and sweat under a weary life, But that the dread of something after death, The undiscovered Country, from whose bourn No Traveller returns, Puzzles the will, And makes us rather bear those ills we have, Than fly to others that we know not of. Thus Conscience does make Cowards of us all, And thus the Native hue of Resolution Is sicklied o'er, with the pale cast of Thought, And enterprises of great pitch and moment, With this regard their Currents turn awry, And lose the name of Action. Soft you now, The fair Ophelia? Nymph, in thy Orisons Be all my sins remembered.";	
	Font fnt = new Font( "Courier New", Font.PLAIN, 18 );
	String[] Lines;
	ArrayList<String> List = new ArrayList<String>();
	
	LineData[] memo;
	
	public void init()
	{
		setSize( 1200, 720 );
		addMouseListener(this);
		MakeDPStrings();
	}
	
	void MakeStrings()
	{
		String[] WordList = Utility.SplitTextIntoWordList( strHamlet );
		int charWidth = GetCharWidth();
		int index = 0;
		
		while( index < WordList.length )
		{
			ArrayList<String> LineWords = new ArrayList<String>();
			int lineLength = 0;
			int mandatorySpace = 0;
			
			while( index < WordList.length &&
				(lineLength+WordList[index].length()+mandatorySpace) < charWidth )
			{
				lineLength += WordList[index].length() + mandatorySpace;
				LineWords.add( WordList[index++] );
				mandatorySpace = 1;
			}
			
			String strLine;
			if( index < WordList.length )
			{
				strLine = Utility.WordListToJustifiedLineString( LineWords, charWidth );
			}
			else
			{
				strLine = Utility.WordListToLineString( LineWords, charWidth );
			}
			
			List.add( strLine );
		}
		
		Lines = new String[List.size()];
		for( int i=0; i<Lines.length; i++ ) Lines[i] = List.get(i);
	}

	void MakeDPStrings()
	{
		String[] WordList = Utility.SplitTextIntoWordList( strHamlet );
		
		memo = new LineData[WordList.length];
		
		LineData ld = new LineData();
		MakeDPStrings( WordList, GetCharWidth(), 0, ld );
		
		ld = ld.next;
		while( ld != null )
		{
			List.add( ld.getText() );
			ld = ld.next;
		}
		
		Lines = new String[List.size()];
		for( int i=0; i<Lines.length; i++ ) Lines[i] = List.get(i);
	}
	
	int MakeDPStrings( String[] WordList, int charWidth, int index, LineData ld )
	{
		
		if( index >= WordList.length ) return( 0 );

		if( memo[index] != null )
		{
			ld.next = memo[index].next;
			return( memo[index].next.getBadness() );
		}
		
		ArrayList<String> LineWords = GenerateLineWords( WordList, index, charWidth );
		index += LineWords.size();
		
		if( index < WordList.length )
		{
			String[] strLine2 = new String[5];
			int[] badness = new int[5];
			int min = Integer.MAX_VALUE;
			int minindex = 0;
			
			for( int i=0; i<5; i++ )
			{
				LineData ld2 = new LineData();
				strLine2[i] = Utility.WordListToJustifiedLineString( MakeSmallerList( LineWords, i ), charWidth );
				badness[i] = MakeDPStrings( WordList, charWidth, index - i, ld2 ) + Utility.GetBadness( strLine2[i] );
				if( badness[i] < min )
				{
					min = badness[i];
					minindex = i;
					ld2.setText( strLine2[i] );
					ld2.setBadness( badness[i] );
					ld.next = ld2;
				}
			}

			memo[index] = ld;
			
			return( badness[minindex] );
		}
		else
		{
			String strLine = Utility.WordListToLineString( LineWords, charWidth );
			LineData ld2 = new LineData();
			ld2.setText( strLine );
			ld.next = ld2;
			return( 0 );
		}
		
	}
	
	ArrayList<String> GenerateLineWords( String[] WordList, int index, int charWidth )
	{
		ArrayList<String> LineWords = new ArrayList<String>();
		
		int lineLength = 0;
		int mandatorySpace = 0;
			
		while( index < WordList.length &&
			(lineLength+WordList[index].length()+mandatorySpace) < charWidth )
		{
			lineLength += WordList[index].length() + mandatorySpace;
			LineWords.add( WordList[index++] );
			mandatorySpace = 1;
		}

		return( LineWords );
	}

	public ArrayList<String> MakeSmallerList( ArrayList<String> LineWords, int less )
	{
		ArrayList<String> Ret = new ArrayList<String>();
		for( int i=0; i<LineWords.size()-less; i++ ) Ret.add( LineWords.get(i));
		return( Ret );
	}

	public void paint( Graphics g )
	{
		g.setFont( fnt );
		int h = g.getFontMetrics().getHeight();
		for( int y=0; y<Lines.length; y++ )
		{
			g.drawString( Lines[y],  10,  40 + y * h );
		}
	}
	
	int GetCharWidth()
	{
		Graphics g = getGraphics();
		g.setFont(fnt);
		int w = getWidth() - 30;
		
		String strText = "A";
		while( g.getFontMetrics().stringWidth(strText+"A") < w )
		{
			strText += "A";
		}
		
		return( strText.length() );
	}

	@Override
	public void mouseClicked(MouseEvent arg0)
	{
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		memo = null;
		Lines = null;
		List = new ArrayList<String>();
		
		if( e.getX() < getWidth() / 2 )
		{
			MakeDPStrings();
			repaint();
		}
		else
		{
			MakeStrings();
			repaint();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
	}
	
}
