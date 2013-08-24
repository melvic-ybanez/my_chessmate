/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mychessmate;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 *
 * @author Melvic
 */
public class PromotionPane extends JDialog implements ActionListener{
    int index;
    int location;
    JPanel main_pane;
    MyChessmate chessmate;

    public PromotionPane(MyChessmate chessmate){
        setTitle("New Piece");
        this.chessmate = chessmate;
        main_pane = new JPanel(new GridLayout(1,4,10,0));
        Resource resource = new Resource();

        int[] cmdActions = {
            Piece.QUEEN,Piece.ROOK,Piece.BISHOP,Piece.KNIGHT
        };        
        for(int i=0; i<cmdActions.length; i++){
            JButton button = new JButton();
            button.addActionListener(this);
            button.setActionCommand(cmdActions[i]+"");
            main_pane.add(button);
        }
        setContentPane(main_pane);        
        setResizable(false);
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                resumeGame(Piece.QUEEN);
            }
        });
    }
    public void setIcons(boolean white){
        Component[] components = main_pane.getComponents();
        Resource resource = new Resource();
        String[] resourceStrings = {"q","r","b","n"};
        for(int i=0; i<components.length; i++){
            JButton button = (JButton)components[i];
            button.setIcon(new ImageIcon(
                    resource.getResource((white?"w":"b")+resourceStrings[i])));
        }
        pack();
        setLocationRelativeTo(null);
    }
    public void actionPerformed(ActionEvent e){
        int promotion_piece = Integer.parseInt(e.getActionCommand());
        setVisible(false);
        resumeGame(promotion_piece);
    }
    public void resumeGame(int promotion_piece){  
        chessmate.position.human_pieces[index] = new Piece(promotion_piece,location);
        chessmate.newHistoryPosition();
        chessmate.board_pane.repaint();
        chessmate.state = GameData.COMPUTER_MOVE;
    }
}
