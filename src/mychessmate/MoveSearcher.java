/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mychessmate;

/**
 *
 * @author Melvic
 */
public class MoveSearcher {
    MyChessmate chessmate;
    int level;
    public MoveSearcher(MyChessmate chessmate){
        this.chessmate = chessmate;
    }
    public Position alphaBeta(int player, Position position, int alpha, int beta, int depth){
        if(depth == 0) return position;
        Position best_position = null;
        MoveGenerator move_generator = new MoveGenerator(position,player);
        move_generator.generateMoves();
        Position[] positions = move_generator.getPositions();
        if(positions.length == 0) return position;    
        
        Evaluator evaluator = new Evaluator();        
        for(Position _position:positions){
            if(best_position == null) best_position = _position;
            if(player == GameData.HUMAN){
                Position opponent_position = alphaBeta(GameData.COMPUTER,_position,alpha,beta,depth-1);                
                int score = evaluator.evaluate(opponent_position);
                //if(score>=beta && level > 4) return _position;
                if(score>alpha){
                    best_position = _position;
                    alpha = score;
                }
            }else{
                Position opponent_position = alphaBeta(GameData.HUMAN,_position,alpha,beta,depth-1);                
                if(new Game(opponent_position).isChecked(GameData.HUMAN)){
                    return _position;
                }
                int score = evaluator.evaluate(opponent_position);
                if(score<=alpha && level > 4) return _position;
                if(score<beta){
                    best_position = _position;
                    beta = score;
                }              
            }
        }
        return best_position;
    }
}
