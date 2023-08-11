package entities.pecas;

import entities.Casa;
import entities.Jogo;
import entities.Peca;
import entities.Tabuleiro;
import enums.PecaCor;

/**
 * Representa a subClasse Peao.
 * Herda caracteristicas de Peca.
 *
 * @author Gustavo Montenegro;
 */
public class Peao extends Peca {
    public Peao(Casa casa, PecaCor cor, Tabuleiro tabuleiro, Jogo jogo) {
        super(casa, cor, tabuleiro, jogo);
    }

    @Override
    public boolean podeMover(Casa destino) {

        int deltaX = destino.getX() - super.getCasa().getX();
        int deltaY = (super.getCor() == PecaCor.BRANCA) ? destino.getY() - super.getCasa().getY() : super.getCasa().getY() - destino.getY();

        if (super.getMoveu() == 0 && deltaX == 0 && deltaY == 2) { // PRIMEIRO MOVIMENTO PEAO
            return caminhoLivrePeao(destino);
        }

        if (deltaX == 0 && deltaY == 1) { // MOVIMENTO NORMAL PEAO
            return caminhoLivrePeao(destino);
        }

        if(Math.abs(deltaX) == 1 && deltaY == 1 && destino.possuiPeca()) { //CAPTURA DO PEAO
            return super.getCor() != destino.getPeca().getCor();
        }

        if(ehMovimentoEnPassant(Math.abs(deltaX), deltaY) && !destino.possuiPeca()) {

            Casa vizinhaDireita = super.getTabuleiro().getCasa(destino.getX(), super.getCasa().getY());
            Casa vizinhaEsquerda = super.getTabuleiro().getCasa(destino.getX(), super.getCasa().getY());

            if(vizinhaDireita.possuiPeca() && vizinhaDireita.getPeca() == super.getJogo().getUltimaPeca() &&
                    vizinhaDireita.getPeca().getMoveu() == 1 && vizinhaDireita.getPeca().getCor() != super.getCor()
                    && destino.getX() == (super.getCasa().getX() + 1) && vizinhaDireita.getPeca() instanceof Peao) {
                vizinhaDireita.removerPeca();
                return true;
            }

            if(vizinhaEsquerda.possuiPeca() && vizinhaEsquerda.getPeca() == super.getJogo().getUltimaPeca() &&
                    vizinhaEsquerda.getPeca().getMoveu() == 1 && vizinhaEsquerda.getPeca().getCor() != super.getCor()
                    && destino.getX() == (super.getCasa().getX() - 1) && vizinhaEsquerda.getPeca() instanceof Peao) {
                vizinhaEsquerda.removerPeca();
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se tem peca no caminho do peao
     * @param destino que a peca vai se mover
     * @return true se nao tiver, false se tiver
     */
    private boolean caminhoLivrePeao(Casa destino) {

        boolean caminho;

        if(super.getCor() == PecaCor.BRANCA) {
            for(int verificaY = super.getCasa().getY() + 1; verificaY <= destino.getY(); verificaY++) {
                caminho = verificaCasa(verificaY);
                if(!caminho) {
                    return true;
                }
            }
        }
        else if(super.getCor() == PecaCor.PRETA) {
            for(int verificaY = super.getCasa().getY() - 1; verificaY >= destino.getY(); verificaY--) {
                caminho = verificaCasa(verificaY);
                if(!caminho) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica se tem peca na casa
     * @param verificaY coordenada Y que deseja verifiicar se tem peca
     * @return true se tiver, false se nao
     */
    private boolean verificaCasa(int verificaY) {
        Casa verifica = super.getTabuleiro().getCasa(super.getCasa().getX(), verificaY);
        return verifica.possuiPeca();
    }

    /**
     * Verifica se o movimmento do Peao eh EnPassant
     * @param deltaX
     * @param deltaY
     * @return true se o movimento eh EnPassant, false se nao
     */
    private boolean ehMovimentoEnPassant(int deltaX, int deltaY) {
        return ((super.getCasa().getY() == 4 && super.getCor() == PecaCor.BRANCA && deltaY == 1 && deltaX == 1) ||
                (super.getCasa().getY() == 3 && super.getCor() == PecaCor.PRETA && deltaY == 1 && deltaX == 1));
    }
}
