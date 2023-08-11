package entities;

import enums.PecaCor;

/**
 * Representa uma Peca do jogo.
 * Possui uma casa e uma cor associada.
 *
 * @author Gustavo Montenegro;
 */
public abstract class Peca {

    private Casa casa;
    private PecaCor cor;
    private Tabuleiro tabuleiro;
    private Jogo jogo;
    private int moveu;

    public Peca(Casa casa, PecaCor cor, Tabuleiro tabuleiro, Jogo jogo) {
        this.casa = casa;
        this.cor = cor;
        this.tabuleiro = tabuleiro;
        this.jogo = jogo;
        this.moveu = 0;
        casa.colocarPeca(this);
    }

    /**
     * Movimenta a peca para uma nova casa.
     * @param destino nova casa que ira conter esta peca.
     */
    public void mover(Casa destino) {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
        moveu += 1;
    }

    /**
     * Indica se o movimento requisitado pela peca eh valido.
     * @param destino nova casa que ira conter esta peca.
     * @return true se o movimento eh valido ou false se nao.
     */
    public abstract boolean podeMover(Casa destino);

    /**
     * Indica se o movimento requisitado pela peca eh diagonal.
     * @param destino nova casa que ira conter esta peca.
     * @return true se o movimento eh diagonal ou false se nao.
     */
    protected boolean ehMovimentoDiagonal(Casa destino) {
        int deltaX = Math.abs(destino.getX() - casa.getX());
        int deltaY = Math.abs(destino.getY() - casa.getY());

        return deltaX == deltaY;
    }

    /**
     * Indica se o movimento requisitado pela peca eh horizontal.
     * @param destino nova casa que ira conter esta peca.
     * @return true se o movimento eh horizontal ou false se nao.
     */
    protected boolean ehMovimentoHorizontal(Casa destino) {
        int deltaX = Math.abs(destino.getX() - casa.getX());
        int deltaY = Math.abs(destino.getY() - casa.getY());

        return deltaX != 0 && deltaY == 0;
    }

    /**
     * Indica se o movimento requisitado pela peca eh vertical.
     * @param destino nova casa que ira conter esta peca.
     * @return true se o movimento eh vertical ou false se nao.
     */
    protected boolean ehMovimentoVertical(Casa destino) {
        int deltaX = Math.abs(destino.getX() - casa.getX());
        int deltaY = Math.abs(destino.getY() - casa.getY());

        return deltaX == 0 && deltaY != 0;
    }

    /**
     * Verifica se a peca pode se mover na diagonal
     * @param destino
     * @return true se puder, false se nao puder
     */
    protected boolean podeMoverDiagonal(Casa destino) {
        if (!ehMovimentoDiagonal(destino)) {
            return false;
        }

        if (destino.possuiPeca() && destino.getPeca().getCor() == this.cor) {
            return false;
        }

        // 1 PARA DIREITA E -1 PARA ESQUERDA
        int deslocamentoX = (casa.getX() < destino.getX()) ? 1 : -1;
        // 1 PARA CIMA E -1 PARA BAIXO
        int deslocamentoY = (casa.getY() < destino.getY()) ? 1 : -1;

        Casa intermediaria = tabuleiro.getCasa(casa.getX() + deslocamentoX, casa.getY() + deslocamentoY);

        while(intermediaria != destino) {
            if(intermediaria.possuiPeca()) {
                return false;
            }
            intermediaria = tabuleiro.getCasa(intermediaria.getX() + deslocamentoX, intermediaria.getY() + deslocamentoY);
        }

        return true;
    }

    /**
     * Verifica se a peca pode se mover na horizontal
     * @param destino
     * @return true se puder, false se nao puder
     */
    protected boolean podeMoverHorizontal(Casa destino) {
        if (!ehMovimentoHorizontal(destino)) {
            return false;
        }

        if (destino.possuiPeca() && destino.getPeca().getCor() == this.cor) {
            return false;
        }

        // 1 PARA DIREITA E -1 PARA ESQUERDA
        int deslocamentoX = (casa.getX() < destino.getX()) ? 1 : -1;

        Casa intermediaria = tabuleiro.getCasa(casa.getX() + deslocamentoX, casa.getY());

        while(intermediaria != destino) {
            if(intermediaria.possuiPeca()) {
                return false;
            }
            intermediaria = tabuleiro.getCasa(intermediaria.getX() + deslocamentoX, intermediaria.getY());
        }

        return true;
    }

    /**
     * Verifica se a peca pode se mover na vertical
     * @param destino
     * @return true se puder, false se nao puder
     */
    protected boolean podeMoverVertical(Casa destino) {
        if (!ehMovimentoVertical(destino)) {
            return false;
        }

        if (destino.possuiPeca() && destino.getPeca().getCor() == this.cor) {
            return false;
        }

        // 1 PARA CIMA E -1 PARA BAIXO
        int deslocamentoY = (casa.getY() < destino.getY()) ? 1 : -1;

        Casa intermediaria = tabuleiro.getCasa(casa.getX(), casa.getY() + deslocamentoY);

        while(intermediaria != destino) {
            if(intermediaria.possuiPeca()) {
                return false;
            }
            intermediaria = tabuleiro.getCasa(intermediaria.getX(), intermediaria.getY() + deslocamentoY);
        }

        return true;
    }

    /**
     * @return a cor da peca.
     */
    public PecaCor getCor() {
        return cor;
    }

    /**
     * @return a casa da peca.
     */
    public Casa getCasa() {
        return casa;
    }

    /**
     * @return o tabuleiro da peca.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /**
     * @return quantas vezes a peca moveu.
     */
    public int getMoveu() {
        return moveu;
    }

    /**
     * @return o jogo da peca.
     */
    public Jogo getJogo() {
        return jogo;
    }
}
