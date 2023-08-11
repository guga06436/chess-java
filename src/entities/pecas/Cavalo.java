package entities.pecas;

import entities.Casa;
import entities.Jogo;
import entities.Peca;
import entities.Tabuleiro;
import enums.PecaCor;

/**
 * Representa a subClasse Cavalo.
 * Herda caracteristicas de Peca.
 *
 * @author Gustavo Montenegro;
 */
public class Cavalo extends Peca {
    public Cavalo(Casa casa, PecaCor cor, Tabuleiro tabuleiro, Jogo jogo) {
        super(casa, cor, tabuleiro, jogo);
    }

    /**
     * Verifica se o Cavalo pode se mover em L
     * @param destino
     * @return true se o Cavalo pode mover em L, false se nao
     */
    @Override
    public boolean podeMover(Casa destino) {

        if(!ehMovimentoEmL(destino)) {
            return false;
        }

        if(destino.possuiPeca()) {
            if(super.getCor() == destino.getPeca().getCor()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Indica se o movimento requisitado pela peca eh de L.
     * @param destino
     * @return true se o movimento eh em L, false se nao
     */
    private boolean ehMovimentoEmL(Casa destino) {
        int deltaX = Math.abs(destino.getX() - super.getCasa().getX());
        int deltaY = Math.abs(destino.getY() - super.getCasa().getY());

        return (deltaX == 2 && deltaY == 1)||(deltaX == 1 && deltaY == 2);
    }
}
