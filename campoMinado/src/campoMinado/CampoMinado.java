import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class CampoMinado extends JFrame implements MouseListener {

    // Array com todos os quadrados (casas)
    ArrayList<JLabel> qdds = new ArrayList<>();

    // Array com todos os números sorteados (casas com bombas)
    ArrayList<Integer> sorteados = new ArrayList<>();

    CampoMinado(){

        // Populando o array de casas com jlabels
        for (int i=0; i < 100; i++) {
            qdds.add(new JLabel());
        }

        //Sorteando as casas que terão bombas
        for (int i=0; i < 7; i++){
            sorteados.add(ThreadLocalRandom.current().nextInt(0, 63));
        }

        // Setando algumas opções da janela
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(570,725);
        this.setResizable(false);
        this.setLayout(null);
        this.setTitle("Campo Minado - por Viktor Marinho na Bosch");

        // Loop para definir as propriedades gráficas e posicionamento de todos os quadrados(casas)
        int j = 0, k = 0;
        for (int i=0; i < qdds.size(); i++){
            qdds.get(i).setBounds(5 + (k*55), 5 + (j*55), 50, 50);
            qdds.get(i).setBackground(Color.gray);
            qdds.get(i).setOpaque(true);
            qdds.get(i).addMouseListener(this);
            k++;
            if ((i+1) % 10 == 0){
                k = 0;
                j++;
            }
        }

        // Adicionando todos os quadrados à janela principal
        for (JLabel qdd : qdds) {
            this.add(qdd);
        }

        // Criando e adicionando uma label com as instruções para jogar
        JLabel tutorial = new JLabel();
        tutorial.setText("<html><body style='text-align: center;'><h1>Instruções</h1><p>" +
                "Clique em um quadrado e ele irá formar um quadrado 3x3. Caso algum dos quadrados em volta não mudem de cor, significa que ele é uma bomba. Boa sorte em tentar limpar todos que não são bombas!</p></body></html>");
        tutorial.setBounds(5, 460, 520, 300);
        this.add(tutorial);

        // Setando a janela principal como visível (Importante deixar por último)
        this.setVisible(true);
    }

    //Função para checar algum se algum quadrado no campo tem bomba
    //Servirá para check de todas as casas em volta da que foi clicada
    // Pos = A casa que quero checar
    // Col = A coluna que preciso checar para essa casa não ter problemas (0 para nenhuma)
    private boolean check(int pos, int col){
        // Nenhuma coluna para se preocupar, servirá apenas para checar quadrado de cima e de baixo
        if (col == 0) {
            try {
                if (!sorteados.contains(pos)) {
                    qdds.get(pos).setBackground(Color.lightGray);
                    return false;
                } else {
                    return true;
                }
            } catch (Exception IndexOutOfBoundsException) {
                System.out.println("Um para fora");
                return false;
            }
            //Check de coluna da direita
        }else if (col == 1){
            try{
                int left = pos;
                boolean direita = left == 9 || left == 19 || left == 29 || left == 39 || left == 49 || left == 59 || left == 69 || left == 79 || left == 89 || left == 99;
                if (!sorteados.contains(pos)){
                    if (direita) {
                        System.out.println("Um para fora");
                    }else{
                        qdds.get(pos).setBackground(Color.lightGray);
                        return false;
                    }
                }else {
                    if (direita) {
                        System.out.println("Um para fora");
                    }else{
                        return true;
                    }
                }
            }catch(Exception IndexOutOfBoundsException){
                System.out.println("Um para fora");
            }
            //Check de coluna da esquerda
        }else if (col == 2){
            try{
                int right = pos;
                boolean col_esquerda = right==0 || right==10 || right==20 || right==30 || right==40 || right==50 || right==60 || right==70 || right==80 || right==90;
                if (!sorteados.contains(pos)){
                    if (col_esquerda) {
                        System.out.println("Um para fora");
                    }else{
                        qdds.get(pos).setBackground(Color.lightGray);
                        return false;
                    }
                }else {
                    if (col_esquerda) {
                        System.out.println("Um para fora");
                    }else{
                        return true;
                    }
                }
            }catch(Exception IndexOutOfBoundsException){
                System.out.println("Um para fora");
            }
        }else{
            System.out.println("VC USOU A FUNÇÃO ERRADO VIKTOR!!");
            return false;
        }
        return false;
    }

    // Método que vai ser ativado quando qualquer click acontece na janela principal
    @Override
    public void mouseClicked(MouseEvent e) {
        // Fazendo um loop em todos os quadrados para descobrir de qual deles veio o evento de click
        for (int i=0; i < qdds.size(); i++){
            if (e.getSource() == qdds.get(i)){

                boolean boom = false;
                //Checando se o quadrado clicado era um dos sorteados para ser uma bomba
                for (int num : sorteados){
                    if (num == i){
                        qdds.get(i).setBackground(Color.red);
                        boom = true;
                        break;
                    }
                }
                // Se não for uma bomba, setar a cor dele e iniciar checagem dos quadrados em volta
                if (!boom){
                    qdds.get(i).setBackground(Color.lightGray);
                    int bombas = 0;

                    //Usando o método que eu fiz para checar todos os quadrados rodeando o clicado
                    //e caso algum dos que o rodeiam seja uma bomba, adicionar este número ao número de bombas em volta
                    //e não revelar a casa da bomba

                    // CHECHANDO SE TEM BOMBA EM CIMA
                    if (this.check(i - 10, 0)){
                        bombas++;
                    }
                    //CHECANDO SE TEM BOMBA EM BAIXO
                    if (this.check(i + 10, 0)){
                        bombas++;
                    }
                    //CHECANDO SE TEM BOMBA ESQUERDA
                    if (this.check(i - 1, 1)){
                        bombas++;
                    }
                    //CHECANDO SE TEM BOMBA DIREITA
                    if (this.check(i + 1, 2)){
                        bombas++;
                    }
                    //CHECANDO SE TEM BOMBA CE
                    if (this.check(i - 11, 1)){
                        bombas++;
                    }
                    //CHECANDO SE TEM BOMBA CD
                    if (this.check(i - 9, 2)){
                        bombas++;
                    }
                    //CHECANDO SE TEM BOMBA BE
                    if (this.check(i + 9, 1)){
                        bombas++;
                    }
                    //CHECANDO SE TEM BOMBA BD
                    if (this.check(i + 11, 2)){
                        bombas++;
                    }

                    //Se houver alguma bomba em volta da casa realmente, colocando o número nela
                    if (bombas > 0) {
                        qdds.get(i).setText("<html><body style='text-align: center'><h1>"+bombas+"</h1></body></html>");
                    }

                }else{
                    // Mensagem caso a casa clicada tenha explodido
                    JOptionPane.showMessageDialog(null, "Boom! Você perdeu :)");
                }
            }
        }
        // Checagem de fim de jogo, acontece todas as rodadas e checa se sobraram apenas casas de bomba
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

    // Métodos obrigatórios, pode Ignorar
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
