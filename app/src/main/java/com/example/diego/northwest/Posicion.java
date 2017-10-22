package com.example.diego.northwest;

/**
 * Created by Diego on 16/10/2017.
 */

public class Posicion {
    public int valor=0;
    public boolean isCrossedPos=false;
    public boolean isCrossedNeg=false;
    public boolean usado=false;

    public boolean isTrash() {
        return trash;
    }

    public void setTrash(boolean trash) {
        this.trash = trash;
    }

    public boolean trash=false;

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }

    public Posicion(int valor, boolean isCrossedPos, boolean isCrossedNeg, boolean usado) {
        this.valor = valor;
        this.isCrossedPos = isCrossedPos;
        this.isCrossedNeg = isCrossedNeg;
        this.usado=usado;

    }

    public int getValor() {

        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean isCrossedPos() {
        return isCrossedPos;
    }

    public void setCrossedPos(boolean crossedPos) {
        isCrossedPos = crossedPos;
    }

    public boolean isCrossedNeg() {
        return isCrossedNeg;
    }

    public void setCrossedNeg(boolean crossedNeg) {
        isCrossedNeg = crossedNeg;
    }
}
