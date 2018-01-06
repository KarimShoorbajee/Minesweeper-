import java.util.*;

public class MineSweeper
{
	public static void main (String [] args)
	{
		boolean gameEnd = false;
		boolean finalVerdict = true;
		char board[][] = initializeBoard();
		//char boardGuesser[][] = board;
		while (!gameEnd)
		{
			int[] coords = getInput(board);
			board = revealSpace(coords, board);
			printBoard(board);
			if (board[coords[0]][coords[1]] == '&')
			{
				System.out.println("you clicked on a bomb!!!!");
				break;
			}
			gameEnd = checkWin(board);
		}
		if (gameEnd == true)
			System.out.println("You win!");
	}
	public static char[][] initializeBoard()
	{
		Scanner input = new Scanner(System.in);
		int n = 0;
		do 
		{
			System.out.println("Enter n for the width and height of the board");
		    n = input.nextInt();
			if (n <= 4 || n > 100)
			{
				System.out.println("please pick a number between 5 and 100");
			}
		}
		while (n <= 4 || n > 100);
		char board[][] = new char[n][n];
		int bombX[] = new int[n];
		int bombY[] = new int[n];
		Random rand = new Random();
		for (int i = 0; i < n; i++)
		{
			for (int j = 0; j < n; j++)
				board[i][j] = '#';
		}
		for (int i = 0; i < n; i++)
		{
			bombX[i] = rand.nextInt(n-1) + 1;
			bombY[i] = rand.nextInt(n-1) + 1;
			board[bombX[i]][bombY[i]] = '@';
		}
		return board;
	}
	
	public static void printBoard(char board[][])
	{
		System.out.print("     ");
		for (int i = 0; i < board[0].length; i++)
		{
			System.out.print(i + "  ");
		}
		System.out.println(" ");
		for (int i = 0; i < board.length; i++)
		{
			System.out.println(" ");
			System.out.print(i + "    ");
			for (int j = 0; j < board[0].length; j++)
			{
				if (board[i][j] == '@')
					System.out.print('#' + "  ");
				else
					System.out.print(board[i][j] + "  ");
			}
		}
		System.out.println(" ");
	}
	
	public static int[] getInput(char board[][])
	{
		Scanner input = new Scanner(System.in);
		int x = 0;
		int y = 0;
		int c = 0;
		do
		{
			System.out.println("Enter a 1 to reveal a space and a 2 guess where a bomb is");
			c = input.nextInt();	
			do
			{
				System.out.println("X coord?");
				x = input.nextInt();
				System.out.println("Y coord?");
				y = input.nextInt();
				if (!checkValidCoord(board,x,y,c))
				{
					System.out.println("This coordinate is invalid. Please select another coordinate");
				}
			}
			while(!checkValidCoord(board,x,y,c));
			if (!(c==1 || c==2))
			{
				System.out.println("Please select 1 for revealing a space or 2 for marking a bomb.");
			}
		}
		while (!(c==1 || c==2));
		int a[] = new int[3];
		a[1] = x;
		a[0] = y;
		a[2] = c; 
		return a;
	}
	
	public static boolean checkValidCoord(char board[][], int x, int y, int c)
	{
		if (c == 1)
		{
			if (oob(board,x,y))
				return false;
			if (board[y][x] == 'X')
				return false;
			else return true;	
		}
		else 
		{
			if (oob(board,x,y))
				return false;
			if (board[y][x] == '#' || board[y][x] == '@')
				return true;
			else return false;
		}
	}
	
	public static char[][] revealSpace(int coord[],char board[][])
	{
		int b = 0;
		if (coord[2] == 1)
		{	
			if (board[coord[0]][coord[1]] == '@')
			{
				board[coord[0]][coord[1]] = '&';
				return board;
			}
			if(!oob(board,coord[0]+1,coord[1]))
				if (board[coord[0]+1][coord[1]] == '@')
					b+=1;
			if(!oob(board,coord[0],coord[1]+1))
				if (board[coord[0]][coord[1]+1] == '@')
					b+=1;
			if(!oob(board,coord[0]+1,coord[1]+1))
				if (board[coord[0]+1][coord[1]+1] == '@')
					b+=1;
			if(!oob(board,coord[0]-1,coord[1]))
				if (board[coord[0]-1][coord[1]] == '@')
					b+=1;
			if(!oob(board,coord[0],coord[1]-1))
				if (board[coord[0]][coord[1]-1] == '@')
					b+=1;
			if(!oob(board,coord[0]-1,coord[1]-1))
				if (board[coord[0]-1][coord[1]-1] == '@')
					b+=1;
			if(!oob(board,coord[0]+1,coord[1]-1))
				if (board[coord[0]+1][coord[1]-1] == '@')
					b+=1;
			if(!oob(board,coord[0]-1,coord[1]+1))
				if (board[coord[0]-1][coord[1]+1] == '@')
					b+=1;
			b+=48;
			char bombsAbout = (char)b;
			board[coord[0]][coord[1]] = bombsAbout;
		}
		else if (coord[2] == 2)
		{
			board[coord[0]][coord[1]] = 'X';
		}
		return board;
	}
	
	public static boolean oob(char board[][], int x, int y)
	{
		if ((x >= board.length || y >= board.length) || (x < 0 || y < 0))
			return true;
		else return false;
	}
	
	public static boolean checkWin(char board[][])
	{
		boolean won = true; 
		for (int i = 0; i < board.length; i++)
		{
			for (int j = 0; j < board.length; j++)
			{
				if (board[i][j] == '@')
					won = false;
			}
		}
		return won; 
	}
}