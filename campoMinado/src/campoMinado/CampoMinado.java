package campoMinado;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CampoMinado extends JFrame implements MouseListener {

    ArrayList<JLabel> qdds = new ArrayList<>();

    ArrayList<Integer> sorteados = new ArrayList<>();

    CampoMinado(){
    	
    	for (int i=0; i < 64; i++) {
    		qdds.add(new JLabel());
    	}

        for (int i=0; i < 7; i++){
            sorteados.add(ThreadLocalRandom.current().nextInt(0, 63));
        }

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(460,620);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Campo Minado - por Viktor Marinho na Bosch");

        int j = 0, k = 0;
        for (int i=0; i < qdds.size(); i++){
            qdds.get(i).setBounds(5 + (k*55), 5 + (j*55), 50, 50);
            qdds.get(i).setBackground(Color.gray);
            qdds.get(i).setOpaque(true);
            qdds.get(i).addMouseListener(this);
            k++;
            if ((i+1) % 8 == 0){
                k = 0;
                j++;
            }
        }

        for (JLabel qdd : qdds) {
            this.add(qdd);
        }
        JLabel tutorial = new JLabel();
        tutorial.setText("<html><body><h1 style='text-align: center;'>Instruções</h1><p>" +
                "Clique em um quadrado e ele irá formar um 'mais'(Contando com os quadrados de cima, baixo, e dos lados.)Caso algum dos quadrados em volta não mudem de cor, significa que ele é uma bomba. Boa sorte em tentar limpar todos que não são bombas!</p></body></html>");
        tutorial.setBounds(5, 350, 430, 300);
        this.add(tutorial);
        this.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (int i=0; i < qdds.size(); i++){
            if (e.getSource() == qdds.get(i)){
                boolean boom = false;
                for (int num : sorteados){
                    if (num == i){
                        qdds.get(i).setBackground(Color.red);
                        boom = true;
                        break;
                    }
                }
                if (!boom){
                    qdds.get(i).setBackground(Color.lightGray);
                    int bombas = 0;
                        try{
                            if (!sorteados.contains(i - 8)){
                                qdds.get(i-8).setBackground(Color.lightGray);
                            }else {
                            	bombas++;
                            }
                        }catch(Exception IndexOutOfBoundsException){
                            System.out.println("top");
                        }

                        try{
                            if (!sorteados.contains(i + 8)){
                                qdds.get(i+8).setBackground(Color.lightGray);
                            }else {
                            	bombas++;
                            }
                        }catch(Exception IndexOutOfBoundsException){
                            System.out.println("bottom");
                        }

                        try{
                            if (!sorteados.contains(i -1)){
                                int left = i-1;
                                if (left==7 || left==15 || left==23 || left==31 || left==39 || left==47 || left==55 || left==63 ) {

                                }else{
                                    qdds.get(i - 1).setBackground(Color.lightGray);
                                }
                            }else {
                            	int left = i-1;
                            	if (left==7 || left==15 || left==23 || left==31 || left==39 || left==47 || left==55 || left==63 ) {
                            		
                                }else{
                                    bombas++;
                                }
                            }
                        }catch(Exception IndexOutOfBoundsException){
                            System.out.println("left");
                        }

                        try{
                            if (!sorteados.contains(i + 1)){
                                int right = i +1;
                                if (right==0 || right==8 || right==16 || right==24 || right==32 || right==40 || right==48 || right==56){

                                }else {
                                    qdds.get(i + 1).setBackground(Color.lightGray);
                                }
                            }else {
                            	int right = i +1;
                                if (right==0 || right==8 || right==16 || right==24 || right==32 || right==40 || right==48 || right==56){

                                }else {
                                    bombas++;
                                }
                            }
                        }catch(Exception IndexOutOfBoundsException){
                            System.out.println("right");
                        }
                      if (bombas > 0) {
                    	  qdds.get(i).setText("<html><body style='text-align: center'><h1>"+bombas+"</h1></body></html>");
                      }

                }else{
                    JOptionPane.showMessageDialog(null, "Boom! Você perdeu :)");
                }
            }
        }
        boolean acabou = true;
        for (int i=0; i< qdds.size(); i++){
        	if (!sorteados.contains(i)){
	            if (qdds.get(i).getBackground() == Color.gray){
	                acabou = false;
	            }
        	}
        }
        if (acabou){
            JOptionPane.showMessageDialog(null, "Você VENCEU! Parabéns ^^");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}