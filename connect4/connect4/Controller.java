package connect4;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
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
        board = (Connect4Game) maxMove(board,7, Integer.MIN_VALUE , Integer.MAX_VALUE).get(1);
    }


    /**
     * Human plays
     *
     * @return the column the human played in
     */
    private void humanPlay() {
        Scanner s = new Scanner(System.in);
        int col;
        int col2;
        String m;
        while (true) {
            System.out.print("Enter column: ");
            col = s.nextInt();

            System.out.print("Enter move ('a' for add , 's' for swap , 'd' for delete): ");
            m = s.next();

            if ((col > 0) && (col - 1 < board.getWidth())) {
                if (m.equals("a")) {
                    if (board.addSlide(human, col - 1)) {
                        System.out.println();
                        return;
                    }
                }
                if (m.equals("s")) {

                    System.out.print("Enter second column: ");
                    col2 = s.nextInt();

                    if (board.swapSlide(human, col - 1, col2 -1)) {
                        System.out.println();
                        return;
                    }
                }
                if (m.equals("d")) {
                    if (board.deleteSlide(human, col - 1)) {
                        System.out.println();
                        return;
                    }
                }
                System.out.println("Invalid move, try again");
            }
            else{
                System.out.println("Invalid Column: out of range " + board.getWidth() + ", try again");
            }
        }
    }

    private List<Object> maxMove(Connect4Game b, int depth, int alpha, int beta) {
        List<Object> result = new ArrayList<>();
        if (b.isFinished()|| depth < 1) {
            result.add(b.evaluate(human));
            result.add(b);
            return result;
        }
        List<Connect4Game> nextMoves = b.allNextMoves(computer);
        int maxEval = Integer.MIN_VALUE;
        Connect4Game bestMove = null;

        for (Connect4Game move : nextMoves) {
            int eval = (int) minMove(move, depth - 1, alpha, beta).get(0);
            if (eval > maxEval) {
                maxEval = eval;
                bestMove = move;
            }
            alpha = Math.max(alpha, maxEval);
            if (beta <= alpha) {
                break;
            }
        }

        result.add(maxEval);
        result.add(bestMove);
        return result;
    }

    private List<Object> minMove(Connect4Game b, int depth, int alpha, int beta) {
        List<Object> result = new ArrayList<>();
        if (b.isFinished()|| depth < 1) {
            result.add(b.evaluate(computer));
            result.add(b);
            return result;
        }

        List<Connect4Game> nextMoves = b.allNextMoves(human);
        int minEval = Integer.MAX_VALUE;
        Connect4Game minMove = null;

        for (Connect4Game move : nextMoves) {
            int eval = (int) maxMove(move, depth - 1, alpha, beta).get(0);
            if (eval < minEval) {
                minEval = eval;
                minMove = move;
            }
            beta = Math.min(beta, minEval);
            if (beta <= alpha) {
                break;
            }
        }

        result.add(minEval);
        result.add(minMove);
        return result;
    }

    public static void checkDim(Controller g){
        Scanner s = new Scanner(System.in);
        int n , w;
        while (true) {
            System.out.print("Enter Dim (>3): ");
            n = s.nextInt();
            System.out.print("Enter Winning Slides number (>2): ");
            w = s.nextInt();
            if (n > 3 && w > 2 && n>=w) {
                g.setDim(n,w);
                return;
            }
            System.out.println("try again");
        }
    }

    public static void main(String[] args) {
        Controller g = new Controller();
        checkDim(g);
        g.play();
    }

}
