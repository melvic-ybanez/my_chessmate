/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mychessmate;

/**
 *
 * @author Melvic
 */
public class Game {          
    Position position;
    Piece human_king;
    Piece computer_king;
    
    public Game(Position position){
        human_king = position.human_pieces[8];
        computer_king = position.computer_pieces[8];
        this.position = position;
    }
    public int getResult(int player){
        int state = -1;
        MoveGenerator mg = new MoveGenerator(position,player);
        mg.generateMoves();
        Position[] positions = mg.getPositions();
        if(positions.length == 0){
            if(isChecked(player)) {
                state = GameData.CHECKMATE;
            }
            else state = GameData.DRAW;
        }
        return state;
    }    
    public boolean safeMove(int player, int source,int destination){
        Move _move = new Move(source,destination);
        Position _position = new Position(position,_move);  
        Game gs = new Game(_position);   
        return !gs.isChecked(player);
    }
    public boolean isChecked(int player){
        boolean checked = false;
        Piece king = (player == GameData.HUMAN)?human_king:computer_king;
        if(king == null) return false;
        checked = checkedByPawn(king);
        if(!checked) checked = checkedByKnight(king);
        if(!checked) checked = checkedByBishop(king);
        if(!checked) checked = checkedByRook(king);
        if(!checked) checked = checkedByQueen(king);
        if(!checked) checked = desSquareAttackedByKing(king);       
        return checked;
    }
    private boolean checkedByPawn(Piece king){
        boolean checked = false;   
        int location = king.location;
        if(king == human_king){
            int right_square = position.board[location-9];
            int left_square = position.board[location-11];
            if(right_square == GameData.ILLEGAL || left_square == GameData.ILLEGAL) return false;
            if(right_square<0 && position.computer_pieces[-right_square].value == Piece.PAWN)
                checked = true;
            if(left_square<0 && position.computer_pieces[-left_square].value == Piece.PAWN)
                checked = true;
        }else{
            int right_square = position.board[location+11];
            int left_square = position.board[location+9];
            if(right_square != GameData.ILLEGAL){
                if(right_square>0 && right_square != GameData.EMPTY && 
                        position.human_pieces[right_square].value == Piece.PAWN)
                    checked = true;
            }
            if(left_square != GameData.ILLEGAL){
                if(left_square>0 && left_square != GameData.EMPTY && 
                        position.human_pieces[left_square].value == Piece.PAWN)
                    checked = true;
            }
        }
        return checked;
    }
    private boolean checkedByKnight(Piece king){
        boolean checked = false;
        int location = king.location;
        int[] destinations = {location-21,location+21,location+19,location-19,
            location-12,location+12,location-8,location+8};
        for(int destination:destinations){
            int des_square = position.board[destination];
            if(des_square == GameData.ILLEGAL) continue;
            if(king == human_king){                
                if(des_square<0 && position.computer_pieces[-des_square].value == Piece.KNIGHT){
                    checked = true;
                    break;
                }
            }else{
                if(des_square>0 && des_square != GameData.EMPTY && 
                        position.human_pieces[des_square].value == Piece.KNIGHT){
                    checked = true;
                    break;
                }
            }
        }
        return checked;
    }
    private boolean desSquareAttackedByKing(Piece king){
        boolean checked = false;
        int location = king.location;
        int[] destinations = {location+1,location-1,location+10,location-10,
            location+11,location-11,location+9,location-9};
        for(int destination:destinations){
            int des_square = position.board[destination];
            if(des_square == GameData.ILLEGAL) continue;
            if(king == human_king){                
                if(des_square<0 && position.computer_pieces[-des_square].value == Piece.KING){
                    checked = true;
                    break;
                }
            }else{
                if(des_square>0 && des_square != GameData.EMPTY && 
                        position.human_pieces[des_square].value == Piece.KING){
                    checked = true;
                    break;
                }
            }
        }
        return checked;
    }
    private boolean checkedByBishop(Piece king){
        boolean checked = false;
        int[] deltas = {11,-11,9,-9};
        for(int i=0; i<deltas.length; i++){
            int delta = king.location+deltas[i];
            while(true){
                int des_square = position.board[delta];
                if(des_square == GameData.ILLEGAL) {
                    checked = false;
                    break;
                }
                if(king == human_king){
                    if(des_square<0 && position.computer_pieces[-des_square].value == Piece.BISHOP){
                        checked = true;
                        break;
                    }else if(des_square != GameData.EMPTY) break;
                }else if(king == computer_king){
                    if(des_square>0 && des_square != GameData.EMPTY && 
                            position.human_pieces[des_square].value == Piece.BISHOP){
                        checked = true;
                        break;
                    }else if(des_square != GameData.EMPTY) break;
                }
                delta += deltas[i];
            }
            if(checked) break;
        }
        return checked;
    }    
    private boolean checkedByRook(Piece king){
        boolean checked = false;
        int[] deltas = {1,-1,10,-10};
        for(int i=0; i<deltas.length; i++){
            int delta = king.location+deltas[i];
            while(true){
                int des_square = position.board[delta];
                if(des_square == GameData.ILLEGAL) {
                    checked = false;
                    break;
                }
                if(king == human_king){
                    if(des_square<0 && position.computer_pieces[-des_square].value == Piece.ROOK){
                        checked = true;
                        break;
                    }else if(des_square != GameData.EMPTY) break;
                }else if(king == computer_king){
                    if(des_square>0 && des_square != GameData.EMPTY && 
                            position.human_pieces[des_square].value == Piece.ROOK){
                        checked = true;
                        break;
                    }else if(des_square != GameData.EMPTY) break;
                }
                delta += deltas[i];
            }
            if(checked) break;
        }
        return checked;
    }    
    private boolean checkedByQueen(Piece king){
        boolean checked = false;
        int[] deltas = {1,-1,10,-10,11,-11,9,-9};
        for(int i=0; i<deltas.length; i++){
            int delta = king.location+deltas[i];
            while(true){
                int des_square = position.board[delta];
                if(des_square == GameData.ILLEGAL) {
                    checked = false;
                    break;
                }
                if(king == human_king){
                    if(des_square<0 && position.computer_pieces[-des_square].value == Piece.QUEEN){
                        checked = true;
                        break;
                    }else if(des_square != GameData.EMPTY) break;
                }else if(king == computer_king){
                    if(des_square>0 && des_square != GameData.EMPTY && 
                            position.human_pieces[des_square].value == Piece.QUEEN){
                        checked = true;
                        break;
                    }else if(des_square != GameData.EMPTY) break;
                }
                delta += deltas[i];
            }
            if(checked) break;
        }
        return checked;
    }
}
