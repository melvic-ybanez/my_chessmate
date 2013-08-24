/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mychessmate;

/**
 *
 * @author Melvic
 */
public class Evaluator {   
    public int evaluate(Position position){        
        int human_score = 0;
        int computer_score = 0;
        for(int i=1; i<position.human_pieces.length; i++){
            if(position.human_pieces[i] != null){
                Piece piece = position.human_pieces[i];
                int value = piece.value;
                human_score += value;
                int location = piece.location;
                int col = (location % 10)-1;
                int row = ((location-col)/10)-2;
                switch(value){
                    case Piece.PAWN: 
                        if(row<4) human_score+=(8-row);
                        if(col>4){
                            human_score -= ((col-4));
                        }else if(col<3){
                            human_score -= ((3-col));
                        }
                        if(col>1 && col<6 && row>1) human_score +=2;
                        if(row == 0) human_score += Piece.QUEEN;
                        //human_score += GameData.HUMAN_PAWN_TABLE[piece.location];
                        break;
                    case Piece.KNIGHT: 
                        if(row == 7) human_score -=1;
                        if(col == 0 || col == 7) human_score -= 1;
                        if(col>1 && col<6 && row>1 && row<6) human_score +=1;
                        //human_score += GameData.HUMAN_KNIGHT_TABLE[piece.location];                        
                        break;
                    case Piece.BISHOP: 
                        if(row == 7) human_score-=1;
                        if(col == 0 || col == 7) human_score -= 1;
                        if(col>0 && col<7 && row>0 && row<7) human_score +=1;
                        //human_score += GameData.HUMAN_BISHOP_TABLE[piece.location];
                        break;
                }
            }
            if(position.computer_pieces[i] != null){
                Piece piece = position.computer_pieces[i];
                int value = piece.value;
                computer_score += value;
                int location = piece.location;
                int col = (location % 10)-1;
                int row = ((location-col)/10)-2;
                switch(value){
                    case Piece.PAWN: 
                        if(row>3)computer_score+=row;
                        if(col>4){
                            computer_score -= ((col-4));
                        }else if(col<3){
                            computer_score -= ((3-col));
                        }
                        if(col>1 && col<6 && row>1) computer_score +=2;
                        if(row == 7) computer_score += Piece.QUEEN;
                        //computer_score += GameData.COMPUTER_PAWN_TABLE[piece.location];
                        break;
                    case Piece.KNIGHT: 
                        if(row == 0) computer_score-=1;
                        if(col == 0 || col == 7) computer_score -= 1;
                        if(col>1 && col<6 && row>1 && row<6) computer_score +=1;
                        //computer_score += GameData.COMPUTER_KNIGHT_TABLE[piece.location];
                        break;
                    case Piece.BISHOP: 
                        if(row == 0) computer_score-=1;
                        if(col == 0 || col == 7) computer_score -= 1;
                        if(col>0 && col<7 && row>0 && row<7) computer_score +=1;
                        //computer_score += GameData.COMPUTER_BISHOP_TABLE[piece.location];
                        break;
                }
            }
        }
        return human_score - computer_score;
    }    
}
