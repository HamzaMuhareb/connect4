package connect4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Random;
//import javafx.util.Pair;


public class Controller {

    char computer = 'o';
    char human = 'x';
    int n;
    int w;
    Connect4Game board;
    public void setDim(int n,int w){
        this.n = n;
        this.w = w;
        this.board = new Connect4Game(n, n, w);
    }
    public void play() {
        System.out.println(board);
        while (true) {
            humanPlay();
            System.out.println(board);

            if (board.isWin(human)) {
                System.out.println("Human wins");
                break;
            }
            if (board.isWithdraw()) {
                System.out.println("Draw");
                break;
            }
            computerPlay();
            System.out.println("_____Computer Turn______");
            System.out.println(board);
            if (board.isWin(computer)) {
                System.out.println("Computer wins!");
                break;
            }
            if (board.isWithdraw()) {
                System.out.println("Draw");
                break;
            }
        }

    }

    //         ************** YOUR CODE HERE ************            \\
    private void computerPlay() {

        // this is a random move, you should change this code to run you own code
//        Random random = new Random();
//        int r = random.nextInt(n);
//        board = board.allNextMoves(computer).get(r);
//        board = (Connect4Game) maxMove(board).get(2);
        List<Object> result = maxMove(board, 3); // You can adjust the depth as needed
        Connect4Game bestMoveState;

        if (result != null && result.size() == 2) {
            bestMoveState = (Connect4Game) result.get(1);
        } else {
            // Handle the case where no valid move is found
            return;
        }

        // Find the column of the move in the bestMoveState
        int bestMove = -1;
        for (int col = 0; col < n; col++) {
            if (board.getTopPieceIndex()[col] != bestMoveState.getTopPieceIndex()[col]) {
                bestMove = col;
                break;
            }
        }

        if (bestMove != -1) {
            board.play(computer, bestMove);
        }
    }



    /**
     * Human plays
     *
     * @return the column the human played in
     */
    private void humanPlay() {
        Scanner s = new Scanner(System.in);
        int col;
        String m;
        while (true) {
            System.out.print("Enter column: ");
            col = s.nextInt();

            System.out.print("Enter move: ");
            m = s.next();
            System.out.println();
            if ((col > 0) && (col - 1 < board.getWidth())) {
                if (m.equals("a")) {
                    if (board.play(human, col - 1)) {
                        return;
                    }
                }
                if (m.equals("s")) {
                    if (board.swap(human, col - 1)) {
                        return;
                    }
                }
                if (m.equals("d")) {
                    if (board.delete(human, col - 1)) {
                        return;
                    }
                }
                System.out.println("Invalid Column: Column " + col + " is full!, try agine");
            }
            System.out.println("Invalid Column: out of range " + board.getWidth() + ", try agine");
        }
    }

    private List<Object> maxMove(Connect4Game b, int depth) {
        // the fuction returns list of object the first object is the evaluation (type Integer), the second is the state with the max evaluation
//         ************** YOUR CODE HERE ************            \\
        if (b.isFinished() || depth == 0) {
            List<Object> result = new ArrayList<>();
            result.add(b.evaluate(computer));
            result.add(b);
            return result;
        }

        int maxEval = Integer.MIN_VALUE;
        Connect4Game bestMove = null;

        for (Connect4Game nextBoard : b.allNextMoves(computer)) {
            List<Object> evalAndState = minMove(nextBoard, depth - 1);
            int eval = (int) evalAndState.get(0);

            if (eval > maxEval) {
                maxEval = eval;
                bestMove = nextBoard;
            }
        }

        List<Object> result = new ArrayList<>();
        result.add(maxEval);
        result.add(bestMove);
        return result;
    }

    private List<Object> minMove(Connect4Game b , int depth) {
        // the fuction returns list of object the first object is the evaluation (type Integer), the second is the state with the min evaluation
//         ************** YOUR CODE HERE ************            \\
        if (b.isFinished() || depth == 0) {
            List<Object> result = new ArrayList<>();
            result.add(b.evaluate(human));
            result.add(b);
            return result;
        }

        int minEval = Integer.MAX_VALUE;
        Connect4Game bestMove = null;

        for (Connect4Game nextBoard : b.allNextMoves(human)) {
            List<Object> evalAndState = maxMove(nextBoard, depth - 1);
            int eval = (int) evalAndState.get(0);

            if (eval < minEval) {
                minEval = eval;
                bestMove = nextBoard;
            }
        }

        List<Object> result = new ArrayList<>();
        result.add(minEval);
        result.add(bestMove);
        return result;
    }

    public static void checkDim(Controller g){
        Scanner s = new Scanner(System.in);
        int n , w;
        while (true) {
            System.out.print("Enter Dim: ");
            n = s.nextInt();
            System.out.print("Enter Winning number: ");
            w = s.nextInt();
            if (n > 3 && w > 2 && n>=w) {

                g.setDim(n,w);
                return;
            }
            System.out.println("try agine");
        }
    }

    public static void main(String[] args) {
        Controller g = new Controller();
        checkDim(g);
        g.play();
    }

}
