/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mychessmate;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Melvic
 */
public class MoveGenerator {    
    Position position;
    int player;
    List<Position> positions = new ArrayList<Position>();   
    Game gs;
    
    public MoveGenerator(Position position, int player){
        this.position = position;
        this.player = player;
        this.gs = new Game(position);
    }
    public Position[] getPositions(){                
        return positions.toArray(new Position[positions.size()]);
    }
    public void generateMoves(){
        if(player == GameData.HUMAN){
            for(int i=1; i<position.human_pieces.length; i++){                
                Piece piece = position.human_pieces[i];       
                if(piece == null) continue;
                switch(piece.value){
                    case Piece.PAWN:
                        humanPawnMoves(piece);
                        break;
                    case Piece.KNIGHT:
                        humanKnightMoves(piece);
                        break;
                    case Piece.KING:
                        humanKingMoves(piece);
                        break;
                    case Piece.BISHOP:
                        humanBishopMoves(piece);
                        break;
                    case Piece.ROOK:
                        humanRookMoves(piece);
                        break;
                    case Piece.QUEEN:
                        humanQueenMoves(piece);
                }
            }
        }else{
            for(int i=1; i<position.computer_pieces.length; i++){
                Piece piece = position.computer_pieces[i];    
                if(piece == null) continue;
                switch(piece.value){
                    case Piece.PAWN:
                        computerPawnMoves(piece);
                        break;
                    case Piece.KNIGHT:
                        computerKnightMoves(piece);
                        break;
                    case Piece.KING:
                        computerKingMoves(piece);
                        break;
                    case Piece.BISHOP:
                        computerBishopMoves(piece);
                        break;
                    case Piece.ROOK:
                        computerRookMoves(piece);
                        break;
                    case Piece.QUEEN:
                        computerQueenMoves(piece);
                }
            }
        }
    }
    public void humanPawnMoves(Piece pawn){        
        int location = pawn.location;
        int forward_piece_index = position.board[location-10];
        if(forward_piece_index != GameData.ILLEGAL){
            if(forward_piece_index == GameData.EMPTY && gs.safeMove(GameData.HUMAN,location,location-10)) {
                positions.add(new Position(position,new Move(location,location-10)));
            }
        }
        
        if(location > 80 && forward_piece_index == GameData.EMPTY && 
                position.board[location-20] == GameData.EMPTY && gs.safeMove(GameData.HUMAN,location,location-20)) {            
                positions.add(new Position(position,new Move(location,location-20)));
        }
        
        int right_piece_index = position.board[location-9];
        if(right_piece_index != GameData.ILLEGAL) {
            if(right_piece_index<0 && gs.safeMove(GameData.HUMAN,location,location-9))
                positions.add(new Position(position,new Move(location,location-9)));
        }
        int left_piece_index = position.board[location-11];
        if(left_piece_index != GameData.ILLEGAL) {
            if(left_piece_index<0 && gs.safeMove(GameData.HUMAN,location,location-11))
                positions.add(new Position(position,new Move(location,location-11)));
        }        
    }    
    public void computerPawnMoves(Piece pawn){      
        int location = pawn.location;
        int forward_piece_index = position.board[location+10];
        if(forward_piece_index != GameData.ILLEGAL){
            if(forward_piece_index == GameData.EMPTY && gs.safeMove(GameData.COMPUTER,location,location+10)){ 
                positions.add(new Position(position,new Move(location,location+10)));
            }
        }
        
        if(location < 39 && forward_piece_index == GameData.EMPTY && 
                position.board[location+20] == GameData.EMPTY && gs.safeMove(GameData.COMPUTER,location,location+20)) {            
                positions.add(new Position(position,new Move(location,location+20)));
        }
        
        int right_piece_index = position.board[location+11];
        if(right_piece_index != GameData.ILLEGAL) {
            if(right_piece_index>0 && right_piece_index != GameData.EMPTY &&
                    gs.safeMove(GameData.COMPUTER,location,location+11))
                positions.add(new Position(position,new Move(location,location+11)));
        }
        int left_piece_index = position.board[location+9];
        if(left_piece_index != GameData.ILLEGAL) {
            if(left_piece_index>0 && left_piece_index != GameData.EMPTY &&
                    gs.safeMove(GameData.COMPUTER,location,location+9))
                positions.add(new Position(position,new Move(location,location+9)));
        }        
    }    
    public void humanKnightMoves(Piece knight){
        int location = knight.location;
        int[] destinations = {location-21,location+21,location+19,location-19,
            location-12,location+12,location-8,location+8};
        for(int i=0; i<destinations.length; i++){
            int des_piece_index = position.board[destinations[i]];
            if(des_piece_index != GameData.ILLEGAL && (des_piece_index == GameData.EMPTY || des_piece_index<0)
                     && gs.safeMove(GameData.HUMAN,location,destinations[i]))
                positions.add(new Position(position,new Move(location,destinations[i])));
        }
    }
    public void computerKnightMoves(Piece knight){
        int location = knight.location;
        int[] destinations = {location-21,location+21,location+19,location-19,
            location-12,location+12,location-8,location+8};
        for(int i=0; i<destinations.length; i++){
            int des_piece_index = position.board[destinations[i]];
            if(des_piece_index != GameData.ILLEGAL && (des_piece_index == GameData.EMPTY || des_piece_index>0) &&
                    gs.safeMove(GameData.COMPUTER,location,destinations[i])){
                positions.add(new Position(position,new Move(location,destinations[i])));
            }
        }
    }
    public void humanKingMoves(Piece king){
        int location = king.location;
        int[] destinations = {location+1,location-1,location+10,location-10,
            location+11,location-11,location+9,location-9};
        for(int i=0; i<destinations.length; i++){
            int des_piece_index = position.board[destinations[i]];
            if(des_piece_index != GameData.ILLEGAL && (des_piece_index == GameData.EMPTY || des_piece_index<0)
                    && gs.safeMove(GameData.HUMAN,location,destinations[i])){
                positions.add(new Position(position,new Move(location,destinations[i])));
            }
        }
    }
    public void computerKingMoves(Piece king){
        int location = king.location;
        int[] destinations = {location+1,location-1,location+10,location-10,
            location+11,location-11,location+9,location-9};
        for(int i=0; i<destinations.length; i++){
            int des_piece_index = position.board[destinations[i]];
            if(des_piece_index != GameData.ILLEGAL && (des_piece_index == GameData.EMPTY || des_piece_index>0) &&
                    gs.safeMove(GameData.COMPUTER,location,destinations[i])){
                positions.add(new Position(position,new Move(location,destinations[i])));
            }
        }
    }
    public void humanBishopMoves(Piece bishop){
        int location = bishop.location;
        int[] deltas = {11,-11,9,-9};
        for (int i = 0; i < deltas.length; i++) {
            int des = location + deltas[i];            
            while (true) {
                int des_piece_index = position.board[des];
                if (des_piece_index == GameData.ILLEGAL) {
                    break;
                }
                boolean safe_move = gs.safeMove(GameData.HUMAN,location,des);
                if (des_piece_index == GameData.EMPTY || des_piece_index < 0){
                    if(safe_move){
                        positions.add(new Position(position,new Move(location,des)));
                        if (des_piece_index != GameData.EMPTY || !safe_move) {                        
                            break;
                        }
                    }else if(des_piece_index != GameData.EMPTY) break;
                } else if(des_piece_index>0 && des_piece_index != GameData.EMPTY){
                    break;
                }
                des += deltas[i];
            }
        }
    }
    public void computerBishopMoves(Piece bishop){
        int location = bishop.location;
        int[] deltas = {11,-11,9,-9};
        for (int i = 0; i < deltas.length; i++) {
            int des = location + deltas[i];            
            while (true) {
                int des_piece_index = position.board[des];
                if (des_piece_index == GameData.ILLEGAL) {
                    break;
                }
                boolean safe_move = gs.safeMove(GameData.COMPUTER,location,des);
                if (des_piece_index == GameData.EMPTY || des_piece_index > 0) {
                    if(safe_move){
                        positions.add(new Position(position,new Move(location,des)));
                        if (des_piece_index != GameData.EMPTY || !safe_move) {                        
                            break;
                        }
                    }else if(des_piece_index != GameData.EMPTY) break;
                } else if(des_piece_index<0){
                    break;
                }
                des += deltas[i];
            }
        }
    }
    public void humanRookMoves(Piece rook){
        int location = rook.location;
        int[] deltas = {1,-1,10,-10};
        for (int i = 0; i < deltas.length; i++) {
            int des = location + deltas[i];            
            while (true) {
                int des_piece_index = position.board[des];
                if (des_piece_index == GameData.ILLEGAL) {
                    break;
                }
                boolean safe_move = gs.safeMove(GameData.HUMAN,location,des);
                if (des_piece_index == GameData.EMPTY || des_piece_index < 0) {
                    if(safe_move){
                        positions.add(new Position(position,new Move(location,des)));
                        if (des_piece_index != GameData.EMPTY) {          
                            break;
                        }
                    }else if(des_piece_index != GameData.EMPTY) break;
                } else if(des_piece_index>0 && des_piece_index != GameData.EMPTY){
                    break;
                }
                des += deltas[i];
            }
        }
    }
    public void computerRookMoves(Piece rook){
        int location = rook.location;
        int[] deltas = {1,-1,10,-10};
        for (int i = 0; i < deltas.length; i++) {
            int des = location + deltas[i];           
            while (true) {
                 int des_piece_index = position.board[des];
                if (des_piece_index == GameData.ILLEGAL) {
                    break;
                }
                boolean safe_move = gs.safeMove(GameData.COMPUTER,location,des);
                if (des_piece_index == GameData.EMPTY || des_piece_index > 0) {
                    if(safe_move){
                        positions.add(new Position(position,new Move(location,des)));
                        if (des_piece_index != GameData.EMPTY) {                        
                            break;
                        }
                    }else if(des_piece_index != GameData.EMPTY) break;
                } else if(des_piece_index<0){
                    break;
                }
                des += deltas[i];
            }
        }
    }
    public void humanQueenMoves(Piece queen){
        int location = queen.location;
        int[] deltas = {1,-1,10,-10,11,-11,9,-9};
        for (int i = 0; i < deltas.length; i++) {
            int des = location + deltas[i];            
            while (true) {
                int des_piece_index = position.board[des];
                if (des_piece_index == GameData.ILLEGAL) {
                    break;
                }
                boolean safe_move = gs.safeMove(GameData.HUMAN,location,des);
                if (des_piece_index == GameData.EMPTY || des_piece_index < 0) {
                    if(safe_move){
                        positions.add(new Position(position,new Move(location,des)));
                        if (des_piece_index != GameData.EMPTY) {                        
                            break;
                        }
                    }else if(des_piece_index != GameData.EMPTY) break;
                } else if(des_piece_index>0 && des_piece_index != GameData.EMPTY){
                    break;
                }
                des += deltas[i];
            }
        }
    }
    public void computerQueenMoves(Piece queen){
        int location = queen.location;
        int[] deltas = {1,-1,10,-10,11,-11,9,-9};
        for (int i = 0; i < deltas.length; i++) {
            int des = location + deltas[i];            
            while (true) {
                int des_piece_index = position.board[des];
                if (des_piece_index == GameData.ILLEGAL) {
                    break;
                }
                boolean safe_move = gs.safeMove(GameData.COMPUTER,location,des);
                if (des_piece_index == GameData.EMPTY || des_piece_index > 0) {
                    if(safe_move){
                        positions.add(new Position(position,new Move(location,des)));
                        if (des_piece_index != GameData.EMPTY) {                        
                            break;
                        }
                    }else if(des_piece_index != GameData.EMPTY) break;
                } else if(des_piece_index<0){
                    break;
                }
                des += deltas[i];
            }
        }
    }
}
