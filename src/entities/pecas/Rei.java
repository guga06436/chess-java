package entities.pecas;

import entities.Casa;
import entities.Jogo;
import entities.Peca;
import entities.Tabuleiro;
import enums.PecaCor;

/**
 * Representa a subClasse Rei.
 * Herda caracteristicas de Peca.
 *
 * @author Gustavo Montenegro;
 */
public class Rei extends Peca {
    public Rei(Casa casa, PecaCor cor, Tabuleiro tabuleiro, Jogo jogo) {
        super(casa, cor, tabuleiro, jogo);
    }

    /**
     * Verifica se o Rei pode se mover
     * @param destino
     * @return true se o Rei pode mover, false se nao
     */
    @Override
    public boolean podeMover(Casa destino) {
        if(ehMovimentoUmaCasa(destino) && (super.podeMoverDiagonal(destino) || super.podeMoverHorizontal(destino)
                || super.podeMoverVertical(destino)) && !xeque(destino)) {
            return true;
        }

        int deltaX = (destino.getX() - super.getCasa().getX());

        Casa casaTorreDireita = super.getTabuleiro().getCasa(7, super.getCasa().getY());
        Casa casaTorreEsquerda = super.getTabuleiro().getCasa(0, super.getCasa().getY());

        if(!xeque(super.getCasa()) && super.getMoveu() == 0 && Math.abs(deltaX) == 2 && (super.ehMovimentoHorizontal(casaTorreDireita)
                || super.ehMovimentoHorizontal(casaTorreEsquerda))) {
            if(deltaX > 0 && casaTorreDireita.possuiPeca() && super.ehMovimentoHorizontal(casaTorreDireita) && casaTorreDireita.getPeca() instanceof Torre
                    && casaTorreDireita.getPeca().getCor() == super.getCor() && casaTorreDireita.getPeca().getMoveu() == 0) { //ROQUE MENOR
                Casa destinoTorre = super.getTabuleiro().getCasa(getCasa().getX() + 1, getCasa().getY());
                casaTorreDireita.getPeca().mover(destinoTorre);
                return true;
            }
            if(deltaX < 0 && casaTorreEsquerda.possuiPeca() && super.ehMovimentoHorizontal(casaTorreEsquerda) && casaTorreEsquerda.getPeca() instanceof Torre
                    && casaTorreEsquerda.getPeca().getCor() == super.getCor() && casaTorreEsquerda.getPeca().getMoveu() == 0) { //ROQUE MAIOR

                Casa destinoTorre = super.getTabuleiro().getCasa(super.getCasa().getX() - 1, super.getCasa().getY());
                casaTorreEsquerda.getPeca().mover(destinoTorre);
                return true;
            }
        }
        return false;
    }

    /**
     * Indica se o movimento requisitado pela peca eh de uma casa.
     * @param destino nova casa que ira conter esta peca.
     * @return true se o movimento eh de uma casa ou false se nao.
     */
    private boolean ehMovimentoUmaCasa(Casa destino) {
        int deltaX = Math.abs(destino.getX() - super.getCasa().getX());
        int deltaY = Math.abs(destino.getY() - super.getCasa().getY());

        return deltaX <= 1 && deltaY <= 1;
    }

    /**
     * Verifica se o Rei está em xeque.
     * @param casaRei casa que o rei se encontra.
     * @return true se o Rei esta em xeque ou false se nao.
     */
    public boolean xeque(Casa casaRei) {
        return xequeVertical(casaRei) || xequeHorizontal(casaRei) || xequeDiagonal(casaRei) || xequePeao(casaRei)
                || xequeEmL(casaRei);
    }

    /**
     * Verifica se o Rei está em xeque verticalmente.
     * @param casaRei
     * @return true se o Rei esta em xeque verticalmente, false se nao
     */
    private boolean xequeVertical(Casa casaRei) {
        return xequeVerticalNegativo(casaRei) || xequeVerticalPositivo(casaRei);
    }

    /**
     * Verifica se o Rei está em xeque verticalmente por baixo.
     * @param casaRei
     * @return true se o Rei esta em xeque verticalmente por baixo, false se nao
     */
    private boolean xequeVerticalNegativo(Casa casaRei) {

        int posicaoRei = casaRei.getY();

        if(posicaoRei == 0) {
            return false;
        }

        Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX(), casaRei.getY());

        while(intermediaria.getY() > 0) {
            intermediaria = super.getTabuleiro().getCasa(intermediaria.getX(), intermediaria.getY() - 1);
            if(intermediaria.possuiPeca()) {
                boolean mesmaCor = super.getCor() == intermediaria.getPeca().getCor();
                if(mesmaCor) {
                    return false;
                }
                else {
                    return intermediaria.getPeca() instanceof Rainha || intermediaria.getPeca() instanceof Torre;
                }
            }
        }
        return false;
    }

    /**
     * Verifica se o Rei está em xeque verticalmente por cima.
     * @param casaRei
     * @return true se o Rei esta em xeque verticalmente por cima, false se nao
     */
    private boolean xequeVerticalPositivo(Casa casaRei) {

        int posicaoRei = casaRei.getY();

        if(posicaoRei == 7) {
            return false;
        }

        Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX(), casaRei.getY());

        while(intermediaria.getY() < 7) {
            intermediaria = super.getTabuleiro().getCasa(intermediaria.getX(), intermediaria.getY() + 1);
            if(intermediaria.possuiPeca()) {
                boolean mesmaCor = super.getCor() == intermediaria.getPeca().getCor();
                if(mesmaCor) {
                    return false;
                }
                else {
                    return intermediaria.getPeca() instanceof Rainha || intermediaria.getPeca() instanceof Torre;
                }
            }
        }
        return false;
    }

    /**
     * Verifica se o Rei está em xeque horizontalmente.
     * @param casaRei
     * @return true se o Rei esta em xeque horizontalmente, false se nao
     */
    private boolean xequeHorizontal(Casa casaRei) {
        return xequeHorizontalNegativo(casaRei) || xequeHorizontalPositivo(casaRei);
    }

    /**
     * Verifica se o Rei está em xeque horizontalmente por baixo.
     * @param casaRei
     * @return true se o Rei esta em xeque horizontalmente por baixo, false se nao
     */
    private boolean xequeHorizontalNegativo(Casa casaRei) {

        int posicaoRei = casaRei.getX();

        if(posicaoRei == 0) {
            return false;
        }

        Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX(), casaRei.getY());

        while(intermediaria.getX() > 0) {
            intermediaria = super.getTabuleiro().getCasa(intermediaria.getX() - 1, intermediaria.getY());
            if(intermediaria.possuiPeca()) {
                boolean mesmaCor = super.getCor() == intermediaria.getPeca().getCor();
                if(mesmaCor) {
                    return false;
                }
                else {
                    return intermediaria.getPeca() instanceof Rainha || intermediaria.getPeca() instanceof Torre;
                }
            }
        }
        return false;
    }

    /**
     * Verifica se o Rei está em xeque horizontalmente por cima.
     * @param casaRei
     * @return true se o Rei esta em xeque horizontalmente por cima, false se nao
     */
    private boolean xequeHorizontalPositivo(Casa casaRei) {

        int posicaoRei = casaRei.getX();

        if(posicaoRei == 7) {
            return false;
        }

        Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX(), casaRei.getY());

        while(intermediaria.getX() < 7) {
            intermediaria = super.getTabuleiro().getCasa(intermediaria.getX() + 1, intermediaria.getY());
            if(intermediaria.possuiPeca()) {
                boolean mesmaCor = super.getCor() == intermediaria.getPeca().getCor();
                if(mesmaCor) {
                    return false;
                }
                else {
                    return intermediaria.getPeca() instanceof Rainha || intermediaria.getPeca() instanceof Torre;
                }
            }
        }
        return false;
    }

    /**
     * Verifica se o Rei está em xeque diagonalmente.
     * @param casaRei
     * @return true se o Rei esta em xeque diagonalmente, false se nao
     */
    private boolean xequeDiagonal(Casa casaRei) {

        boolean DiagonalDireitaSuperior = false;
        boolean DiagonalDireitaInferior = false;
        boolean DiagonalEsquerdaSuperior = false;
        boolean DiagonalEsquerdaInferior = false;

        if(casaRei.getX() != 7 && casaRei.getY() != 7) {
            Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX(), casaRei.getY());
            while(intermediaria.getX() < 7 && intermediaria.getY() < 7) {
                intermediaria = super.getTabuleiro().getCasa(intermediaria.getX() + 1, intermediaria.getY() + 1);
                if(intermediaria.possuiPeca()) {
                    boolean mesmaCor = super.getCor() == intermediaria.getPeca().getCor();
                    if(mesmaCor) {
                        break;
                    }
                    else {
                        DiagonalDireitaSuperior = intermediaria.getPeca() instanceof Rainha || intermediaria.getPeca() instanceof Bispo;
                        break;
                    }
                }
            }
        }

        if(casaRei.getX() != 7 && casaRei.getY() != 0) {
            Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX(), casaRei.getY());
            while(intermediaria.getX() < 7 && intermediaria.getY() > 0) {
                intermediaria = super.getTabuleiro().getCasa(intermediaria.getX() + 1, intermediaria.getY() - 1);
                if(intermediaria.possuiPeca()) {
                    boolean mesmaCor = super.getCor() == intermediaria.getPeca().getCor();
                    if(mesmaCor) {
                        break;
                    }
                    else {
                        DiagonalDireitaInferior = intermediaria.getPeca() instanceof Rainha || intermediaria.getPeca() instanceof Bispo;
                        break;
                    }
                }
            }
        }

        if(casaRei.getX() != 0 && casaRei.getY() != 7) {
            Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX(), casaRei.getY());
            while(intermediaria.getX() > 0 && intermediaria.getY() < 7) {
                intermediaria = super.getTabuleiro().getCasa(intermediaria.getX() - 1, intermediaria.getY() + 1);
                if(intermediaria.possuiPeca()) {
                    boolean mesmaCor = super.getCor() == intermediaria.getPeca().getCor();
                    if(mesmaCor) {
                        break;
                    }
                    else {
                        DiagonalEsquerdaSuperior = intermediaria.getPeca() instanceof Rainha || intermediaria.getPeca() instanceof Bispo;
                        break;
                    }
                }
            }
        }

        if(casaRei.getX() != 0 && casaRei.getY() != 0) {
            Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX(), casaRei.getY());
            while(intermediaria.getX() > 0 && intermediaria.getY() > 0) {
                intermediaria = super.getTabuleiro().getCasa(intermediaria.getX() - 1, intermediaria.getY() - 1);
                if(intermediaria.possuiPeca()) {
                    boolean mesmaCor = super.getCor() == intermediaria.getPeca().getCor();
                    if(mesmaCor) {
                        break;
                    }
                    else {
                        DiagonalEsquerdaInferior = intermediaria.getPeca() instanceof Rainha || intermediaria.getPeca() instanceof Bispo;
                        break;
                    }
                }
            }
        }
        return DiagonalDireitaSuperior || DiagonalDireitaInferior || DiagonalEsquerdaSuperior || DiagonalEsquerdaInferior;
    }

    /**
     * Verifica se o Rei está em xeque por um peao.
     * @param casaRei
     * @return true se o Rei esta em xeque por um peao, false se nao
     */
    private boolean xequePeao(Casa casaRei) {

        int deltaY;

        if(super.getCor() == PecaCor.BRANCA) {
            deltaY = 1;
        }
        else {
            deltaY = -1;
        }

        Casa intermediariaEsquerda = super.getTabuleiro().getCasa(casaRei.getX() - 1, casaRei.getY() + deltaY);
        Casa intermediariaDireita = super.getTabuleiro().getCasa(casaRei.getX() + 1, casaRei.getY() + deltaY);

        if(intermediariaEsquerda.possuiPeca() && intermediariaEsquerda.getPeca().getCor() != super.getCor()
                && intermediariaEsquerda.getPeca() instanceof Peao) {
            return true;
        }
        if(intermediariaDireita.possuiPeca() && intermediariaDireita.getPeca().getCor() != super.getCor()
                && intermediariaDireita.getPeca() instanceof Peao) {
            return true;
        }
        return false;
    }

    /**
     * Verifica se o Rei está em xeque em L.
     * @param casaRei
     * @return true se o Rei esta em xeque em L, false se nao
     */
    private boolean xequeEmL(Casa casaRei) {

        int posicaoReiX = casaRei.getX();
        int posicaoReiY = casaRei.getY();

        if(posicaoReiX <= 5 && posicaoReiY != 7) {
            Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX() + 2, casaRei.getY() + 1);
            if(intermediaria.possuiPeca() && intermediaria.getPeca().getCor() != super.getCor()
                    && intermediaria.getPeca() instanceof Cavalo) {
                return true;
            }
        }

        if(posicaoReiX <= 5 && posicaoReiY != 0) {
            Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX() + 2, casaRei.getY() - 1);
            if(intermediaria.possuiPeca() && intermediaria.getPeca().getCor() != super.getCor()
                    && intermediaria.getPeca() instanceof Cavalo) {
                return true;
            }
        }

        if(posicaoReiX >= 3 && posicaoReiY != 7) {
            Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX() - 2, casaRei.getY() + 1);
            if(intermediaria.possuiPeca() && intermediaria.getPeca().getCor() != super.getCor()
                    && intermediaria.getPeca() instanceof Cavalo) {
                return true;
            }
        }

        if(posicaoReiX >= 3 && posicaoReiY != 0) {
            Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX() - 2, casaRei.getY() - 1);
            if(intermediaria.possuiPeca() && intermediaria.getPeca().getCor() != super.getCor()
                    && intermediaria.getPeca() instanceof Cavalo) {
                return true;
            }
        }

        if(posicaoReiX != 7 && posicaoReiY <= 5) {
            Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX() + 1, casaRei.getY() + 2);
            if(intermediaria.possuiPeca() && intermediaria.getPeca().getCor() != super.getCor()
                    && intermediaria.getPeca() instanceof Cavalo) {
                return true;
            }
        }

        if(posicaoReiX != 0 && posicaoReiY <= 5) {
            Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX() - 1, casaRei.getY() + 2);
            if(intermediaria.possuiPeca() && intermediaria.getPeca().getCor() != super.getCor()
                    && intermediaria.getPeca() instanceof Cavalo) {
                return true;
            }
        }

        if(posicaoReiX != 7 && posicaoReiY >= 3) {
            Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX() + 1, casaRei.getY() - 2);
            if(intermediaria.possuiPeca() && intermediaria.getPeca().getCor() != casaRei.getPeca().getCor()
                    && intermediaria.getPeca() instanceof Cavalo) {
                return true;
            }
        }

        if(posicaoReiX != 0 && posicaoReiY >= 3) {
            Casa intermediaria = super.getTabuleiro().getCasa(casaRei.getX() - 1, casaRei.getY() - 2);
            if(intermediaria.possuiPeca() && intermediaria.getPeca().getCor() != super.getCor()
                    && intermediaria.getPeca() instanceof Cavalo) {
                return true;
            }
        }
        return false;
    }

}