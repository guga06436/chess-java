package entities.pecas;

import entities.Casa;
import entities.Jogo;
import entities.Peca;
import entities.Tabuleiro;
import enums.PecaCor;

/**
 * Representa a subClasse Torre.
 * Herda caracteristicas de Peca.
 *
 * @author Gustavo Montenegro;
 */
public class Torre extends Peca {
    public Torre(Casa casa, PecaCor cor, Tabuleiro tabuleiro, Jogo jogo) {
        super(casa, cor, tabuleiro, jogo);
    }

    /**
     * Verifica se a Torre pode se mover
     * @param destino
     * @return true se a Torre pode mover, false se nao
     */
    @Override
    public boolean podeMover(Casa destino) {
        return super.podeMoverHorizontal(destino) || super.podeMoverVertical(destino);
    }
}
