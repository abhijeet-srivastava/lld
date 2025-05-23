Develop a game of Chess
2 Players- Each assigned a particular type of peice 
Play turn by Turn, With One player will be starting the game


Board: 8 * 8 metrics
Each cell will be either empty or have atmost one piece on it
Different type of pieces of each color:
8 Pawns - 1 step forward
2 Rooks - Diagnoally(Any number of cell)
1 King - Moves in all 8 direction(1 step)
1 Queen - Moves in all 8 direction(any number of times)
2 Bishops - Either horizontally (left/right) or Backward/forewarss
2 knights - Moving in L shape


1. Game:
   1. Board
   2. Players - 2
   3. CurrentTurn = Player

Board:
    8*8 metric of cells:
    Map<Cell, Piece>

Cell:
    Position - Rows A-H
    Cols- 1-8
    It can Accomodate a Piece

2. Player:
    id:
    Color:
    List of Piece
    MakeNextMove():Cell

   3. Piece(Abstract)-
       Color
       CurrentPosition
   Concrete Class Implementing
      Name
          NextAvailableMoves: List[Cell]
   
    


