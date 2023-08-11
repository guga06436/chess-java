package entities;

/**
 * Representa uma Casa do tabuleiro.
 * Possui uma posicao (i,j) e pode conter uma Peca.
 *
 * @author Alan Moraes alan@ci.ufpb.br;
 * @author Leonardo Villeth lvilleth@cc.ci.ufpb.br;
 */
public class Casa {
    private int x;
    private int y;
    private Peca peca;

    public Casa(int x, int y) {
        this.x = x;
        this.y = y;
        this.peca = null;
    }

    /**
     * @param peca a Peca a ser posicionada nesta Casa.
     */
    public void colocarPeca(Peca peca) {
        this.peca = peca;
    }

    /**
     * Remove a peca posicionada nesta casa, se houver.
     */
    public void removerPeca() {
        peca = null;
    }

    /**
     * @return a Peca posicionada nesta Casa, ou Null se a casa estiver livre.
     */
    public Peca getPeca() {
        return peca;
    }

    /**
     * @return true se existe uma peca nesta casa, caso contrario false.
     */
    public boolean possuiPeca() {
        return peca != null;
    }

    /**
     * @return posicao X da casa.
     */
    public int getX() {
        return x;
    }

    /**
     * @return posicao Y da casa.
     */
    public int getY() {
        return y;
    }
}
