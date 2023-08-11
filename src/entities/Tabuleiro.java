package entities;

/**
 * Constroi o tabuleiro.
 * Responsovel por armazenar as 64 casas.
 *
 * @author Gustavo Montenegro
 */
public class Tabuleiro {

    private Casa[][] casas;

    public Tabuleiro() {
        casas = new Casa[8][8];
        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Casa casa = new Casa(x, y);
                casas[x][y] = casa;
            }
        }
    }
    /**
     * @param x linha
     * @param y coluna
     * @return Casa na posicao (x,y)
     */
    public Casa getCasa(int x, int y) {
        return casas[x][y];
    }
}
