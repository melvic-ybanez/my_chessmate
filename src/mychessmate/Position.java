/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mychessmate;

/**
 *
 * @author Melvic
 */
public class Position {
    Move last_move;
    int[] board = new int[120];
    Piece[] human_pieces = new Piece[17];
    Piece[] computer_pieces = new Piece[17];
    
    public Position(){
        for(int i=0; i<board.length; i++){
            board[i] = GameData.EMPTY;
        }
    }
    public Position(Position position){
        this(position,null);
    }
    public Position(Position position, Move last_move){
        System.arraycopy(position.board, 0, this.board, 0, board.length);
        for(int i=1; i<human_pieces.length; i++){
            if(position.human_pieces[i] != null){
                this.human_pieces[i] = position.human_pieces[i].clone();
            }
            if(position.computer_pieces[i] != null){
                this.computer_pieces[i] = position.computer_pieces[i].clone();
            }
        }
        if(last_move != null) update(last_move);
    }    
    public void initialize(boolean humanWhite){         
        human_pieces[1] = new Piece(Piece.KNIGHT,92);
        human_pieces[2] = new Piece(Piece.KNIGHT,97);
        human_pieces[3] = new Piece(Piece.BISHOP,93);
        human_pieces[4] = new Piece(Piece.BISHOP,96);
        human_pieces[5] = new Piece(Piece.ROOK,91);
        human_pieces[6] = new Piece(Piece.ROOK,98);
        human_pieces[7] = new Piece(Piece.QUEEN,humanWhite?94:95);
        human_pieces[8] = new Piece(Piece.KING,humanWhite?95:94);
        
        computer_pieces[1] = new Piece(Piece.KNIGHT,22);
        computer_pieces[2] = new Piece(Piece.KNIGHT,27);
        computer_pieces[3] = new Piece(Piece.BISHOP,23);
        computer_pieces[4] = new Piece(Piece.BISHOP,26);
        computer_pieces[5] = new Piece(Piece.ROOK,21);
        computer_pieces[6] = new Piece(Piece.ROOK,28);
        computer_pieces[7] = new Piece(Piece.QUEEN,humanWhite?24:25);
        computer_pieces[8] = new Piece(Piece.KING,humanWhite?25:24); 
        
        int j = 81;
        for(int i=9; i<human_pieces.length; i++){
            human_pieces[i] = new Piece(Piece.PAWN,j);
            computer_pieces[i] = new Piece(Piece.PAWN,j-50);
            j++;
        }                      
        board = new int[]{
            GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,
            GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,
            GameData.ILLEGAL,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.ILLEGAL,
            GameData.ILLEGAL,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.ILLEGAL,
            GameData.ILLEGAL,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.ILLEGAL,
            GameData.ILLEGAL,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.ILLEGAL,
            GameData.ILLEGAL,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.ILLEGAL,
            GameData.ILLEGAL,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.ILLEGAL,
            GameData.ILLEGAL,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.ILLEGAL,
            GameData.ILLEGAL,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.EMPTY,GameData.ILLEGAL,
            GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,
            GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL,GameData.ILLEGAL
        };        
        for(int i=0; i<board.length; i++){                        
            for(int k=1; k<human_pieces.length; k++){
                if(i==human_pieces[k].location){
                    board[i] = k;
                }else if(i==computer_pieces[k].location){
                    board[i] = -k;
                }
            }
        }
    }    
    public void update(Move move){
        this.last_move = move;   
        int source_index = board[move.source_location];
        int destination_index = board[move.destination];  
        if(source_index>0){
            human_pieces[source_index].has_moved = true;
            human_pieces[source_index].location = move.destination;
            if(destination_index<0){                
                computer_pieces[-destination_index] = null;
            }            
        }else{
            computer_pieces[-source_index].has_moved = true;
            computer_pieces[-source_index].location = move.destination;
            if(destination_index>0 && destination_index != GameData.EMPTY){                
                human_pieces[destination_index] = null;
            }            
        }
        board[move.source_location] = GameData.EMPTY;
        board[move.destination] = source_index;
    }
}
