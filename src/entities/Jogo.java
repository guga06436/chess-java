package entities;

import entities.pecas.*;
import enums.PecaCor;

import java.util.HashSet;
import java.util.Set;

/**
 * Armazena o tabuleiro e responsavel por posicionar as pecas.
 *
 * @author Gustavo Montenegro
 */
public class Jogo {

    private Tabuleiro tabuleiro;
    private PecaCor turnoCor;
    private boolean gameOver;
    private Casa casaReiBranco;
    private Casa casaReiPreto;
    private Peca ultimaPeca;
    private boolean pecaPromovida;
    private boolean reiBrancoXeque;
    private boolean reiPretoXeque;

    public Jogo() {
        tabuleiro = new Tabuleiro();
        this.turnoCor = PecaCor.BRANCA;
        this.gameOver = false;
        pecaPromovida = false;
        casaReiBranco = null;
        casaReiPreto = null;
        reiBrancoXeque = false;
        reiPretoXeque = false;
        criarPecas();
    }

    /**
     * Posiciona pecas no tabuleiro.
     * Utilizado na inicializacao do jogo.
     */
    private void criarPecas() {
        Casa casa;

        for (int i = 0; i < 8; i++) {
            casa = tabuleiro.getCasa(i, 1);
            new Peao(casa, PecaCor.BRANCA, tabuleiro, this);
        }

        casa = tabuleiro.getCasa(0, 0);
        new Torre(casa, PecaCor.BRANCA, tabuleiro, this);

        casa = tabuleiro.getCasa(1, 0);
        new Cavalo(casa, PecaCor.BRANCA, tabuleiro, this);

        casa = tabuleiro.getCasa(2, 0);
        new Bispo(casa, PecaCor.BRANCA, tabuleiro, this);

        casa = tabuleiro.getCasa(3, 0);
        new Rainha(casa, PecaCor.BRANCA, tabuleiro, this);

        casaReiBranco = tabuleiro.getCasa(4, 0);
        new Rei(casaReiBranco, PecaCor.BRANCA, tabuleiro, this);

        casa = tabuleiro.getCasa(5, 0);
        new Bispo(casa, PecaCor.BRANCA, tabuleiro, this);

        casa = tabuleiro.getCasa(6, 0);
        new Cavalo(casa, PecaCor.BRANCA, tabuleiro, this);

        casa = tabuleiro.getCasa(7, 0);
        new Torre(casa, PecaCor.BRANCA, tabuleiro, this);

        for (int i = 0; i < 8; i++) {
            casa = tabuleiro.getCasa(i, 6);
            new Peao(casa, PecaCor.PRETA, tabuleiro, this);
        }

        casa = tabuleiro.getCasa(0, 7);
        new Torre(casa, PecaCor.PRETA, tabuleiro, this);

        casa = tabuleiro.getCasa(1, 7);
        new Cavalo(casa, PecaCor.PRETA, tabuleiro, this);

        casa = tabuleiro.getCasa(2, 7);
        new Bispo(casa, PecaCor.PRETA, tabuleiro, this);

        casa = tabuleiro.getCasa(3, 7);
        new Rainha(casa, PecaCor.PRETA, tabuleiro, this);

        casaReiPreto = tabuleiro.getCasa(4, 7);
        new Rei(casaReiPreto, PecaCor.PRETA, tabuleiro, this);

        casa = tabuleiro.getCasa(5, 7);
        new Bispo(casa, PecaCor.PRETA, tabuleiro, this);

        casa = tabuleiro.getCasa(6, 7);
        new Cavalo(casa, PecaCor.PRETA, tabuleiro, this);

        casa = tabuleiro.getCasa(7, 7);
        new Torre(casa, PecaCor.PRETA, tabuleiro, this);
    }

    /**
     * Comanda uma Peca na posicao (origemX, origemY) fazer um movimento
     * para (destinoX, destinoY).
     *
     * @param origemX linha da Casa de origem.
     * @param origemY coluna da Casa de origem.
     * @param destinoX linha da Casa de destino.
     * @param destinoY coluna da Casa de destino.
     */
    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Casa origem = tabuleiro.getCasa(origemX, origemY);
        Casa destino = tabuleiro.getCasa(destinoX, destinoY);
        Peca peca = origem.getPeca();

        this.pecaPromovida = false;
        if (peca.getCor() == turnoCor && !gameOver && peca.podeMover(destino)) {
            this.gameOver = destino.possuiPeca() && destino.getPeca() instanceof Rei;
            peca.mover(destino);
            if (peca instanceof Rei) {
                setCasaRei(destino);
            }
            this.turnoCor = (turnoCor == PecaCor.BRANCA) ? PecaCor.PRETA : PecaCor.BRANCA;
            pecaPromovida = (peca instanceof Peao && peca.getCor() == PecaCor.BRANCA && destinoY == 7) ||
                    (peca instanceof Peao && peca.getCor() == PecaCor.PRETA && destinoY == 0);
            this.ultimaPeca = peca;
        }

    }

    /**
     * Promove o peao para uma peca de escolha
     * @param peca (peao) que vai ser promovido
     * @param pecaEscolhida escolha do usurio para a promocao
     * @return true se a escolha foi valida, false caso nao
     */
    public boolean promocaoPeca(Peca peca, String pecaEscolhida) {

        Set<String> names = new HashSet<String>();
        names.add("RAINHA");
        names.add("TORRE");
        names.add("BISPO");
        names.add("CAVALO");

        if(names.contains(pecaEscolhida)) {
            Casa casa_promocao = peca.getCasa();
            PecaCor cor = peca.getCor();
            casa_promocao.removerPeca();
            switch (pecaEscolhida) {
                case "RAINHA":
                    new Rainha(casa_promocao, cor, tabuleiro, this);
                    break;
                case "TORRE":
                    new Torre(casa_promocao, cor, tabuleiro, this);
                    break;
                case "CAVALO":
                    new Cavalo(casa_promocao, cor, tabuleiro, this);
                    break;
                case "BISPO":
                    new Bispo(casa_promocao, cor, tabuleiro, this);
                    break;
                default:
                    break;
            }
            return true;
        }
        return false;
    }

    /**
     * @return o Tabuleiro em jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }

    /**
     * @return ultima Peca jogada.
     */
    public Peca getUltimaPeca() {
        return ultimaPeca;
    }

    /**
     * @return true se a peca esta elegivel para promocao, false caso nao esteja
     */
    public boolean isPecaPromovida() {
        return pecaPromovida;
    }

    /**
     * @return casa na qual o rei branco esta localizado
     */
    public Casa getCasaReiBranco() {
        return casaReiBranco;
    }

    /**
     * @return casa do rei preto
     */
    public Casa getCasaReiPreto() {
        return casaReiPreto;
    }

    /**
     * @return true se o rei branco esta em xeque, false caso nao esteja
     */
    public boolean isReiBrancoXeque() {
        return reiBrancoXeque;
    }

    /**
     * Coloca o rei branco em xeque
     * @param reiBrancoXeque
     */
    public void setReiBrancoXeque(boolean reiBrancoXeque) {
        this.reiBrancoXeque = reiBrancoXeque;
    }

    /**
     * @return true se o rei preto esta em xeque, false caso nao esteja
     */
    public boolean isReiPretoXeque() {
        return reiPretoXeque;
    }

    /**
     * Coloca o rei preto em xeque
     * @param reiPretoXeque
     */
    public void setReiPretoXeque(boolean reiPretoXeque) {
        this.reiPretoXeque = reiPretoXeque;
    }

    /**
     * Demarca a casa do rei branco ou do rei preto
     * @param casaRei
     */
    private void setCasaRei(Casa casaRei) {
        if(casaRei.getPeca().getCor().equals("branca")) {
            casaReiBranco = casaRei;
        }
        else {
            casaReiPreto = casaRei;
        }
    }

    /**
     * @return o turno da cor atual
     */
    public PecaCor getTurnoCor() {
        return turnoCor;
    }

    /**
     * @return true se o o jogo acabou, false caso nao
     */
    public boolean isGameOver() {
        return gameOver;
    }
}
