//import java.util.HashMap;

public class MiniMax 
{
	int m_nMaxPly = 7;
	
	//  This is the piece to search for. Should be either RED or YELLOW.
	int m_nSearchPiece = 0;
	
	int nAlpha = -2000000;
	int nBeta = 2000000;
	
	//private static HashMap<String, Integer> memoboard = new HashMap<String, Integer>();
	
	// Set the piece for which a move will be found.
	public void SetSearchPiece( int nPiece )
	{
		m_nSearchPiece = nPiece;
	}

	// Compile a list of legal moves.
	int GetLegalMoves( int[][] nBoardData, int[] nMoveList )
	{
		int row, col;
		int nNumMoves = 0;

		// Loop through six rows.
		for( row=0; row<=5; row++ )
		{
			// Loop through seven columns.
			for( col=0; col<=6; col++ )
			{
				// If the board at this location is empty,
				//   then this  square is a legal move.
				if( nBoardData[row][col] == Connect4.EMPTY &&
					( row == 5 || nBoardData[row+1][col] != Connect4.EMPTY ) )
				{
					// Put the row and column in the move list array.
					nMoveList[nNumMoves*2] = row;
					nMoveList[nNumMoves*2+1] = col;
					// Increment the index pointer.
					nNumMoves++;
				}
			}
		}

		// Return the number of legal moves that were found.
		return( nNumMoves );
	}

	int DoSearch( Position pos, int nPiece, int nDepth, Board pBoard, int nAlpha, int nBeta )
	{
		// Local arrays for the legal move list and
		//   the result list.
		int[] nMoveList = new int[7*2];

		// First, see if a side has won.
		if( pBoard.DidSideWin( nPiece ) )
		{
			if( nPiece == m_nSearchPiece )
			{
				return( 2000000 );
			}
			return( -2000000 );
		}
		
		// See if we have a Cats game.
		else if( pBoard.IsCatsGame() )
		{
			// Score for Cats game is 0.
			return( 0 );
		}
		
		// If we are at a leaf node, return the score.
		else if( nDepth >= m_nMaxPly )
		{
			return( ScoreIt( m_nSearchPiece, pBoard.GetBoardData() ) );
		}

		// Get the legal moves.
		int nMoves = GetLegalMoves( pBoard.GetBoardData(), nMoveList );

		if( nDepth == 0 )
		{
			pos.Row = nMoveList[0];
			pos.Col = nMoveList[1]; 
		}
		
		// Check to see if this is the maximizer. You could also use
		//   the depth % 2 == 0 to test for maximizer.
		if( nDepth == 0 )
		{
			//String sBoard = pBoard.toString();
			//int[][] boardData = pBoard.GetBoardData();
			//int theScore = ScoreIt(m_nSearchPiece, boardData);
			//memoboard.put(sBoard, theScore);
			
			// Loop through the legal moves.
			for( int i=0; i<nMoves; i++ )
			{
				// We need a board clone so that we can place pieces
				//	without messing up previous board positions.
				Board SaveMe = pBoard.Clone();
				
				// Place the piece from the current move in the list.
				SaveMe.PlacePiece( nMoveList[i*2], nMoveList[i*2+1], nPiece );
			
				// We need to save a copy of the alpha for later.
				int saveAlpha = nAlpha;
			
				// Call DoSearch() recursively.
				int retvalue = DoSearch( pos, nPiece ^ 1, nDepth + 1, SaveMe, nAlpha, nBeta );
				
				// The new alpha will be the maximum of alpha and the return value.
		        //   We have to remember the maximum since this is the maximizer.
				if( nAlpha < retvalue )
				{
				// Set nMax.
					nAlpha = retvalue;
				}
				
				// If this move beats the previously recorded move, record this one in the data structure.
				if( nDepth == 0 && saveAlpha < nAlpha )
				{
					pos.Row = nMoveList[i*2];
					pos.Col = nMoveList[i*2+1]; 
				}        
		        
		        if( retvalue >= nBeta)
		        {
		        	return retvalue;
		        }		        		      
			}
			
			return nAlpha;
		}
		
		else 
		{
			// Loop through the legal moves.
			for( int i=0; i<nMoves; i++ )
			{
				// We need a board clone so that we can place pieces
				//	without messing up previous board positions.
				Board SaveMe = pBoard.Clone();
				
				// Place the piece from the current move in the list.
				SaveMe.PlacePiece( nMoveList[i*2], nMoveList[i*2+1], nPiece );
				
				// Call DoSearch() recursively.
				int retvalue = DoSearch( pos, nPiece ^ 1, nDepth + 1, SaveMe, nAlpha, nBeta );
				
				// Check to see if this result is less than the current min.
				if( nBeta > retvalue )
				{
					// Set nMin.
					nBeta = retvalue;
				}
				
		        if( retvalue <= nAlpha)
		        {
		        	return retvalue;
		        }		    		      
			}	
			
			return nBeta;
		}		
	}

	// Wrapper method that kicks off minimax to get a move.
	public void GetMove( Position pos, int[][] BoardData, int nPiece )
	{
		// Set the search piece.
		SetSearchPiece( nPiece );
		
		// Create a new board with this board data.
		Board brd = new Board();
		brd.SetBoardData( BoardData );

		// Call the recursive method.
		DoSearch( pos, nPiece, 0, brd, nAlpha, nBeta );
	}
	
	int ScoreIt( int nPiece, int[][] BoardData )
	{
		int Twos = 0;
		int Threes = 0;
		int Fours = 0 ;
		
		for( int nRow=0; nRow<6; nRow++ )
		{
			int nCount = 0;
			for( int nCol=0; nCol<7; nCol++ )
			{
				if( BoardData[nRow][nCol] == nPiece )
				{
					nCount++;
				}
				else
				{
					if( nCount == 2 )
					{
						Twos++;
					}
					else if( nCount == 3 )
					{
						Threes++;
					}
					else if( nCount == 4 )
					{
						Fours++;
					}
					nCount = 0;
				}
			}
			if( nCount == 2 )
			{
				Twos++;
			}
			else if( nCount == 3 )
			{
				Threes++;
			}
			else if( nCount == 4 )
			{
				Fours++;
			}
		}
		
		for( int nCol=0; nCol<7; nCol++ )
		{
			int nCount = 0;
			for( int nRow=0; nRow<6; nRow++ )
			{
				if( BoardData[nRow][nCol] == nPiece )
				{
					nCount++;
				}
				else
				{
					if( nCount == 2 )
					{
						Twos++;
					}
					else if( nCount == 3 )
					{
						Threes++;
					}
					else if( nCount == 4 )
					{
						Fours++;
					}
					nCount = 0;
				}
			}
			if( nCount == 2 )
			{
				Twos++;
			}
			else if( nCount == 3 )
			{
				Threes++;
			}
			else if( nCount == 4 )
			{
				Fours++;
			}
		}
		
		// Loop through the diagonal data.
		for( int nDiagonalTest=0; nDiagonalTest<Board.m_nDiagonalData.length/4; nDiagonalTest++)
		{
			int nCount = 0;
			// Starting row.
			int nRow = Board.m_nDiagonalData[nDiagonalTest*4];
			// Starting column.
			int nCol = Board.m_nDiagonalData[nDiagonalTest*4+1];
			// YDirection for the iterations.
			int nYDir = Board.m_nDiagonalData[nDiagonalTest*4+2];
			// Number of iterations.
			int nIterations = Board.m_nDiagonalData[nDiagonalTest*4+3];

			// Loop through the iterations.
			for( int i=0; i<nIterations; i++ )
			{
				// If this is nSide then increment the counter.
				if( BoardData[nRow][nCol] == nPiece )
				{
					// Increment the counter.
					nCount++;
				}
				
				// This square does not equal nSide.
				else
				{
					if( nCount == 2 )
					{
						Twos++;
					}
					else if( nCount == 3 )
					{
						Threes++;
					}
					else if( nCount == 4 )
					{
						Fours++;
					}
					
					// Reset the counter.
					nCount = 0;
				}
				
				// Move the row position.
				nRow += nYDir;
				// Move the column position.
				nCol++;
			}
			
			if( nCount == 2 )
			{
				Twos++;
			}
			else if( nCount == 3 )
			{
				Threes++;
			}
			else if( nCount == 4 )
			{
				Fours++;
			}
			
		}

		int nPositionalAdvantage = 0;
		for( int nCol=0; nCol<7; nCol++ )
		{
			int nCount = 0;
			for( int nRow=0; nRow<6; nRow++ )
			{
				if( BoardData[nRow][nCol] == nPiece )
				{
					nCount++;
				}
			}
			
			if( nCol == 2 || nCol == 3 )
			{
				nPositionalAdvantage += nCount * 2;
			}
			else if( nCol == 1 || nCol == 4 )
			{
				nPositionalAdvantage += nCount;
			}
			
		}
		for( int nCol=0; nCol<7; nCol++ )
		{
			int nCount = 0;
			for( int nRow=0; nRow<6; nRow++ )
			{
				if( BoardData[nRow][nCol] != nPiece )
				{
					nCount++;
				}
			}
			
			if( nCol == 2 || nCol == 3 )
			{
				nPositionalAdvantage -= nCount * 2;
			}
			else if( nCol == 1 || nCol == 4 )
			{
				nPositionalAdvantage -= nCount;
			}
		}
		
		return( nPositionalAdvantage + Twos + Threes * 2 + Fours * 4 );
	}

	static String ArrayToString( int[] IntArray )
	{
		String strRet = "{ ";
		for( int i=0; i<IntArray.length; i++ )
		{
			strRet += (""+IntArray[i] );
			if( i < IntArray.length - 1 )
			{
				strRet += ", ";
			}
		}
		return( strRet + " }");
	}
	
}

