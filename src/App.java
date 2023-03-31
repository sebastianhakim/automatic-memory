import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class App extends Application {
    private static final int BOARD_SIZE = 5; // Board size is 5x5
    private static final int CELL_SIZE = 80; // Each cell size is 80x80
    private static final int WINDOW_WIDTH = CELL_SIZE * BOARD_SIZE + 50; 
    private static final int WINDOW_HEIGHT = CELL_SIZE * BOARD_SIZE + 100; 

    private char currentPlayer = 'X'; // Start with player X
    private boolean gameOver = false; 

    private char[][] board = new char[BOARD_SIZE][BOARD_SIZE]; // 2D array 

    private GridPane grid = new GridPane(); // GridPane to hold the board cells

    @Override
    public void start(Stage primaryStage) {
        // Initialize board 
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                board[i][j] = '-';
            }
        }
        
        // Create grid
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                StackPane cell = new StackPane();
                cell.setPrefSize(CELL_SIZE, CELL_SIZE);
                Rectangle rect = new Rectangle(CELL_SIZE, CELL_SIZE);
                rect.setFill(null);
                rect.setStroke(Color.GREEN);
                rect.setStrokeWidth(2);
                cell.getChildren().add(rect);
                Text text = new Text();
                text.setFont(Font.font(50));
                cell.getChildren().add(text);
                GridPane.setRowIndex(cell, i);
                GridPane.setColumnIndex(cell, j);
                grid.getChildren().add(cell);

                
                cell.setOnMouseClicked(event -> {
                    if (!gameOver) {
                        int row = GridPane.getRowIndex(cell);
                        int coloumn = GridPane.getColumnIndex(cell);
                        if (board[row][coloumn] == '-') {
                            board[row][coloumn] = currentPlayer;
                            text.setText(Character.toString(currentPlayer));
                            if (checkWin(row, coloumn)) {
                                gameOver = true;
                                showResult(currentPlayer + " wins!");
                            } else if (checkDraw()) {
                                gameOver = true;
                                showResult("Draw!");
                            } else {
                                currentPlayer = currentPlayer == 'X' ? 'O' : 'X';
                            }
                        }
                    }
                });
            }
        }

        
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        Scene scene = new Scene(grid, WINDOW_WIDTH, WINDOW_HEIGHT);

        
        primaryStage.setTitle("Tic Tac Toe");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean checkWin(int row, int col) {
        // Check row
        int count = 0;
        for (int j = 0; j < BOARD_SIZE; j++) {
            if (board[row][j] == currentPlayer) {
                count++;
            }
        }
        if (count == BOARD_SIZE) {
            return true;
            } 
        // Check column
        count = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][col] == currentPlayer) {
                count++;
            }
        }
        if (count == BOARD_SIZE) {
            return true;
        }
    
        // Check diagonal
        count = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][i] == currentPlayer) {
                count++;
            }
        }
        if (count == BOARD_SIZE) {
            return true;
        }
    
        // Check reverse diagonal
        count = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            if (board[i][BOARD_SIZE - 1 - i] == currentPlayer) {
                count++;
            }
        }
        if (count == BOARD_SIZE) {
            return true;
        }
    
        return false;
    }
    
    private boolean checkDraw() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }
    
    private void showResult(String result) {
        Text text = new Text(result);
        text.setFont(Font.font(100));
        StackPane pane = new StackPane();
        pane.getChildren().add(text);
        pane.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane, WINDOW_WIDTH, WINDOW_HEIGHT);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Game Over, thanks for playing!");
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}