package entities.pecas;

import entities.Casa;
import entities.Jogo;
import entities.Peca;
import entities.Tabuleiro;
import enums.PecaCor;

/**
 * Representa a subClasse Bispo.
 * Herda caracteristicas de Peca.
 *
 * @author Gustavo Montenegro;
 */
public class Bispo extends Peca {
    public Bispo(Casa casa, PecaCor cor, Tabuleiro tabuleiro, Jogo jogo) {
        super(casa, cor, tabuleiro, jogo);
    }

    /**
     * Verifica se o Bispo pode se mover
     * @param destino
     * @return true se o Bispo pode mover, false se nao
     */
    @Override
    public boolean podeMover(Casa destino) {
        return super.podeMoverDiagonal(destino);
    }

}
