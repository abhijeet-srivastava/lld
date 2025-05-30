GameManager=> initializeNewGame(int rows, int cols, List<int[]> food): Game

Game:
    Board
    
    makeMovement(Direction): boolean

Board:
    int[][]: rows*cols
    Food: List<int[]>
    FoodIndex: int
    Snake
    moveSnake(int[] nextCell, boolean isFoodCell): boolean
    printBoard()
Snake:
    List<int[]> cells
    printSnake()
    makeMovement(int[] nextCell, boolean isFoodCell):boolean
