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
        //2nd param depth is for the depth of the evaluation
        board = (Connect4Game) maxMove(board,3).get(1);
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

            System.out.print("Enter move ('a' for add , 's' for swap , 'd' for delete): ");
            m = s.next();
            System.out.println();
            if ((col > 0) && (col - 1 < board.getWidth())) {
                if (m.equals("a")) {
                    if (board.addSlide(human, col - 1)) {
                        return;
                    }
                }
                if (m.equals("s")) {
                    if (board.swapSlide(human, col - 1)) {
                        return;
                    }
                }
                if (m.equals("d")) {
                    if (board.deleteSlide(human, col - 1)) {
                        return;
                    }
                }
                System.out.println("Invalid move, try again");
            }
            System.out.println("Invalid Column: out of range " + board.getWidth() + ", try agine");
        }
    }

    private List<Object> maxMove(Connect4Game b,int depth) {
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
            int eval = (int) minMove(move, depth-1).get(0);
            if (eval > maxEval) {
                maxEval = eval;
                bestMove = move;
            }
        }

        result.add(maxEval);
        result.add(bestMove);
        return result;
    }

    private List<Object> minMove(Connect4Game b,int depth) {
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
            int eval = (int) maxMove(move ,depth -1).get(0);
            if (eval < minEval) {
                minEval = eval;
                minMove = move;
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
