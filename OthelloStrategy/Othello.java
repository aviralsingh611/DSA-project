import java.io.*;
import java.util.*;


public class Othello {
    int turn;
    int winner;
    int board[][];
    //add required class variables here
    
    public Othello(String filename) throws Exception {
        File file = new File(filename);
        Scanner sc = new Scanner(file);
        turn = sc.nextInt();
        board = new int[8][8];
        for(int i = 0; i < 8; ++i) {
            for(int j = 0; j < 8; ++j){
                board[i][j] = sc.nextInt();
            }
        }
        winner = -1;
        //Student can choose to add preprocessing here
    }

    //add required helper functions here
    private ArrayList<Integer> is_valid_directions(int[][] board, int pseudo_turn, int pos_x, int pos_y){
        ArrayList<Integer> directions = new ArrayList<>();
        int x = pos_x;
        int y = pos_y;
        int c = (pseudo_turn+1)%2;
        // 1
        if(is_valid(x+1, y)&&board[x+1][y]==c){
            while(is_valid(x+1, y)&&board[x+1][y]==c){
                x++;
                if(is_valid(x+1, y)&&board[x+1][y]==pseudo_turn){
                    directions.add(1);
                    break;
                }
            }
        }

        // 2
        x=pos_x;
        y=pos_y;
        if(is_valid(x+1, y-1)&&board[x+1][y-1]==c){
            while(is_valid(x+1, y-1)&&board[x+1][y-1]==c){
                x++;
                y--;
                if(is_valid(x+1, y-1)&&board[x+1][y-1]==pseudo_turn){
                    directions.add(2);
                    break;
                }
            }
        }

        // 3
        x=pos_x;
        y=pos_y;
        if(is_valid(x, y-1)&&board[x][y-1]==c){
            while(is_valid(x, y-1)&&board[x][y-1]==c){
                y--;
                if(is_valid(x, y-1)&&board[x][y-1]==pseudo_turn){
                    directions.add(3);
                    break;
                }
            }
        }  
        
        // 4
        x=pos_x;
        y=pos_y;
        if(is_valid(x-1, y-1)&&board[x-1][y-1]==c){
            while(is_valid(x-1, y-1)&&board[x-1][y-1]==c){
                y--;
                x--;
                if(is_valid(x-1, y-1)&&board[x-1][y-1]==pseudo_turn){
                    directions.add(4);
                    break;
                }
            }
        }  
        
        // 5
        x=pos_x;
        y=pos_y;
        if(is_valid(x-1, y)&&board[x-1][y]==c){
            while(is_valid(x-1, y)&&board[x-1][y]==c){
                x--;
                if(is_valid(x-1, y)&&board[x-1][y]==pseudo_turn){
                    directions.add(5);
                    break;
                }
            }
        } 
        
        // 6
        x=pos_x;
        y=pos_y;
        if(is_valid(x-1, y+1)&&board[x-1][y+1]==c){
            while(is_valid(x-1, y+1)&&board[x-1][y+1]==c){
                x--;
                y++;
                if(is_valid(x-1, y+1)&&board[x-1][y+1]==pseudo_turn){
                    directions.add(6);
                    break;
                }
            }
        }
        
        // 7
        x=pos_x;
        y=pos_y;
        if(is_valid(x, y+1)&&board[x][y+1]==c){
            while(is_valid(x, y+1)&&board[x][y+1]==c){
                y++;
                if(is_valid(x, y+1)&&board[x][y+1]==pseudo_turn){
                    directions.add(7);
                    break;
                }
            }
        } 
        
        // 8
        x=pos_x;
        y=pos_y;
        if(is_valid(x+1, y+1)&&board[x+1][y+1]==c){
            while(is_valid(x+1, y+1)&&board[x+1][y+1]==c){
                y++;
                x++;
                if(is_valid(x+1, y+1)&&board[x+1][y+1]==pseudo_turn){
                    directions.add(8);
                    break;
                }
            }
        }          
        return directions;
    }

    private boolean is_valid(int x, int y){
        if(x<8&&y<8&&x>=0&&y>=0){
            return true;
        }
        return false;
    }

    private int count_score(int [][] board, int pseudo_turn){
        int black = 0;
        int white = 0;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j]==0){
                    black++;
                }
                if(board[i][j]==1){
                    white++;
                }
            }
        }
        if(pseudo_turn==0){
            return black-white;
        }
        else{
            return white-black;
        }
    }
    private ArrayList<Integer> minimaxi(int[][] prev_board, int node_x, int node_y, ArrayList<Integer> node, int k, int level, boolean bool){
        int pseudo_turn = (turn+level)%2;
        if(k==level){
            int score = count_score(prev_board, turn);
            node.add(node_x);
            node.add(node_y);
            node.add(score);
            return node;
        }

        boolean valid = false;
        ArrayList<ArrayList<Integer>> possibles = new ArrayList<ArrayList<Integer>>();
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(prev_board[i][j]==-1){
                    ArrayList<Integer> directions = is_valid_directions(prev_board, pseudo_turn, i, j);
                    if(directions.size()!=0){
                        valid = true;
                        int[][] new_board = new int[8][8];
                        for(int m=0; m<8; m++){
                            for(int n=0; n<8; n++){
                                new_board[m][n] = prev_board[m][n];
                            }
                        }
                        new_board[i][j] = pseudo_turn;
                        for(int z=0; z<directions.size(); z++){
                            int a = directions.get(z);
                            if(a==1){
                                int new_x = i+1;
                                int new_y = j;
                                while(new_board[new_x][new_y]==(pseudo_turn+1)%2){
                                    new_board[new_x][new_y] = pseudo_turn;
                                    new_x++;
                                }
                            }
                            if(a==2){
                                int new_x = i+1;
                                int new_y = j-1;
                                while(new_board[new_x][new_y]==(pseudo_turn+1)%2){
                                    new_board[new_x][new_y] = pseudo_turn;
                                    new_x++;
                                    new_y--;
                                }
                            }
                            if(a==3){
                                int new_x = i;
                                int new_y = j-1;
                                while(new_board[new_x][new_y]==(pseudo_turn+1)%2){
                                    new_board[new_x][new_y] = pseudo_turn;
                                    new_y--;
                                }
                            }
                            if(a==4){
                                int new_x = i-1;
                                int new_y = j-1;
                                while(new_board[new_x][new_y]==(pseudo_turn+1)%2){
                                    new_board[new_x][new_y] = pseudo_turn;
                                    new_x--;
                                    new_y--;
                                }
                            }
                            if(a==5){
                                int new_x = i-1;
                                int new_y = j;
                                while(new_board[new_x][new_y]==(pseudo_turn+1)%2){
                                    new_board[new_x][new_y] = pseudo_turn;
                                    new_x--;
                                }
                            }
                            if(a==6){
                                int new_x = i-1;
                                int new_y = j+1;
                                while(new_board[new_x][new_y]==(pseudo_turn+1)%2){
                                    new_board[new_x][new_y] = pseudo_turn;
                                    new_x--;
                                    new_y++;
                                }
                            }
                            if(a==7){
                                int new_x = i;
                                int new_y = j+1;
                                while(new_board[new_x][new_y]==(pseudo_turn+1)%2){
                                    new_board[new_x][new_y] = pseudo_turn;
                                    new_y++;
                                }
                            }
                            if(a==8){
                                int new_x = i+1;
                                int new_y = j+1;
                                while(new_board[new_x][new_y]==(pseudo_turn+1)%2){
                                    new_board[new_x][new_y] = pseudo_turn;
                                    new_x++;
                                    new_y++;
                                }
                            }
                        }
                        ArrayList<Integer> naya_node = new ArrayList<>();
                        possibles.add(minimaxi(new_board, i, j, naya_node, k, level+1, true));
                    }
                }
            }
        }
        if(valid==false&&bool==true){
            int[][] new_board = new int[8][8];
            for(int m=0; m<8; m++){
                for(int n=0; n<8; n++){
                    new_board[m][n] = prev_board[m][n];
                }
            }
            ArrayList<Integer> naya_node = new ArrayList<>();
            possibles.add(minimaxi(new_board, node_x, node_y, naya_node, k, level+1, false));               
        }

        if(valid==false&&bool==false){
            int c = count_score(prev_board, turn);
            node.add(node_x);
            node.add(node_y);
            if(c>0){
                node.add(c);
            }
            else if(c==0){
                node.add(0);
            }
            else{
                node.add(c);
            }
            return node;
        }

        node.add(node_x);
        node.add(node_y);
        if(level%2 == 0){
            node.add(Integer.MIN_VALUE);
            for(int i=0; i<possibles.size(); i++){
                if(node.get(2)<possibles.get(i).get(2)){
                    node.set(2,possibles.get(i).get(2));
                    if(node_x==-1){
                        node.set(0, possibles.get(i).get(0));
                        node.set(1, possibles.get(i).get(1));
                    }
                }
            } 
        }
        else{
            node.add(Integer.MAX_VALUE);
            for(int i=0; i<possibles.size(); i++){
                if(node.get(2)>possibles.get(i).get(2)){
                    node.set(2,possibles.get(i).get(2));
                    if(node_x==-1){
                        node.set(0, possibles.get(i).get(0));
                        node.set(1, possibles.get(i).get(1));
                    }
                }
            } 
        }
        return node;
    }

    public int boardScore() {
        /* Complete this function to return num_black_tiles - num_white_tiles if turn = 0, 
         * and num_white_tiles-num_black_tiles otherwise. 
        */
        int black = 0;
        int white = 0;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j]==0){
                    black++;
                }
                if(board[i][j]==1){
                    white++;
                }
            }
        }
        if(turn==0){
            return black-white;
        }
        else{
            return white-black;
        }
    }

    public int bestMove(int k) {
        /* Complete this function to build a Minimax tree of depth k (current board being at depth 0),
         * for the current player (siginified by the variable turn), and propagate scores upward to find
         * the best move. If the best move (move with max score at depth 0) is i,j; return i*8+j
         * In case of ties, return the smallest integer value representing the tile with best score.
         * 
         * Note: Do not alter the turn variable in this function, so that the boardScore() is the score
         * for the same player throughout the Minimax tree.
        */
        boolean valid = false;
        int level = 0;
        ArrayList<ArrayList<Integer>> possibles = new ArrayList<>(); 
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j]==-1){
                    ArrayList<Integer> directions = is_valid_directions(board, turn, i, j);
                    if(directions.size()!=0){
                        valid = true;

                        int[][] new_board = new int[8][8];
                        for(int m=0; m<8; m++){
                            for(int n=0; n<8; n++){
                                new_board[m][n] = board[m][n];
                            }
                        }
                        new_board[i][j] = turn;
                        for(int z=0; z<directions.size(); z++){
                            int a = directions.get(z);
                            if(a==1){
                                int new_x = i+1;
                                int new_y = j;
                                while(new_board[new_x][new_y]==(turn+1)%2){
                                    new_board[new_x][new_y] = turn;
                                    new_x++;
                                }
                            }
                            if(a==2){
                                int new_x = i+1;
                                int new_y = j-1;
                                while(new_board[new_x][new_y]==(turn+1)%2){
                                    new_board[new_x][new_y] = turn;
                                    new_x++;
                                    new_y--;
                                }
                            }
                            if(a==3){
                                int new_x = i;
                                int new_y = j-1;
                                while(new_board[new_x][new_y]==(turn+1)%2){
                                    new_board[new_x][new_y] = turn;
                                    new_y--;
                                }
                            }
                            if(a==4){
                                int new_x = i-1;
                                int new_y = j-1;
                                while(new_board[new_x][new_y]==(turn+1)%2){
                                    new_board[new_x][new_y] = turn;
                                    new_x--;
                                    new_y--;
                                }
                            }
                            if(a==5){
                                int new_x = i-1;
                                int new_y = j;
                                while(new_board[new_x][new_y]==(turn+1)%2){
                                    new_board[new_x][new_y] = turn;
                                    new_x--;
                                }
                            }
                            if(a==6){
                                int new_x = i-1;
                                int new_y = j+1;
                                while(new_board[new_x][new_y]==(turn+1)%2){
                                    new_board[new_x][new_y] = turn;
                                    new_x--;
                                    new_y++;
                                }
                            }
                            if(a==7){
                                int new_x = i;
                                int new_y = j+1;
                                while(new_board[new_x][new_y]==(turn+1)%2){
                                    new_board[new_x][new_y] = turn;
                                    new_y++;
                                }
                            }
                            if(a==8){
                                int new_x = i+1;
                                int new_y = j+1;
                                while(new_board[new_x][new_y]==(turn+1)%2){
                                    new_board[new_x][new_y] = turn;
                                    new_x++;
                                    new_y++;
                                }
                            }
                        }
                        ArrayList<Integer> naya_node = new ArrayList<>();
                        possibles.add(minimaxi(new_board, i, j, naya_node, k, level+1, true));
                    }
                }
            }
        }
        if(valid==false){
            return -1;
        }
        
        int index = -1;
        int temp = Integer.MIN_VALUE;
        for(int i=0; i<possibles.size(); i++){
            if(temp<possibles.get(i).get(2)){
                temp = possibles.get(i).get(2);
                index = i;
            }
            if(possibles.get(i).get(2)==Integer.MIN_VALUE){
                index = i;
            }
        } 
        if(index!=-1){
            return possibles.get(index).get(0)*8 + possibles.get(index).get(1);
        }
        else{
            return index;
        }
    }

    public ArrayList<Integer> fullGame(int k) {
        /* Complete this function to compute and execute the best move for each player starting from
         * the current turn using k-step look-ahead. Accordingly modify the board and the turn
         * at each step. In the end, modify the winner variable as required.
         */
        ArrayList<Integer> ans = new ArrayList<Integer>();
        int b = bestMove(k);
        while(b!=-1&&b!=-9){
            ans.add(b);
            int i = b/8;
            int j = b%8;
            ArrayList<Integer> directions = is_valid_directions(board, turn, i, j);
            if(directions.size()!=0){
                board[i][j] = turn;
            }
            for(int z=0; z<directions.size(); z++){
                int a = directions.get(z);
                if(a==1){
                    int new_x = i+1;
                    int new_y = j;
                    while(board[new_x][new_y]==(turn+1)%2){
                        board[new_x][new_y] = turn;
                        new_x++;
                    }
                }
                if(a==2){
                    int new_x = i+1;
                    int new_y = j-1;
                    while(board[new_x][new_y]==(turn+1)%2){
                        board[new_x][new_y] = turn;
                        new_x++;
                        new_y--;
                    }
                }
                if(a==3){
                    int new_x = i;
                    int new_y = j-1;
                    while(board[new_x][new_y]==(turn+1)%2){
                        board[new_x][new_y] = turn;
                        new_y--;
                    }
                }
                if(a==4){
                    int new_x = i-1;
                    int new_y = j-1;
                    while(board[new_x][new_y]==(turn+1)%2){
                        board[new_x][new_y] = turn;
                        new_x--;
                        new_y--;
                    }
                }
                if(a==5){
                    int new_x = i-1;
                    int new_y = j;
                    while(board[new_x][new_y]==(turn+1)%2){
                        board[new_x][new_y] = turn;
                        new_x--;
                    }
                }
                if(a==6){
                    int new_x = i-1;
                    int new_y = j+1;
                    while(board[new_x][new_y]==(turn+1)%2){
                        board[new_x][new_y] = turn;
                        new_x--;
                        new_y++;
                    }
                }
                if(a==7){
                    int new_x = i;
                    int new_y = j+1;
                    while(board[new_x][new_y]==(turn+1)%2){
                        board[new_x][new_y] = turn;
                        new_y++;
                    }
                }
                if(a==8){
                    int new_x = i+1;
                    int new_y = j+1;
                    while(board[new_x][new_y]==(turn+1)%2){
                        board[new_x][new_y] = turn;
                        new_x++;
                        new_y++;
                    }
                }
            }
            
            
            turn = (turn+1)%2;
            b = bestMove(k);
            if(b==-1||b==-9){
                turn = (turn+1)%2;
                b = bestMove(k);            
            }
        }
        int black = 0;
        int white = 0;
        for(int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                if(board[i][j]==0){
                    black++;
                }
                if(board[i][j]==1){
                    white++;
                }
            }
        }
        if(black>white){
            winner=0;
        }
        else if(white>black){
            winner=1;
        }
        else{
            winner=-1;
        }
        System.out.println(ans.toString());
        return ans;
    }

    public int[][] getBoardCopy() {
        int copy[][] = new int[8][8];
        for(int i = 0; i < 8; ++i)
            System.arraycopy(board[i], 0, copy[i], 0, 8);
        return copy;
    }

    public int getWinner() {
        return winner;
    }

    public int getTurn() {
        return turn;
    }
}