package com.pruebacc.redis;

import lombok.Getter;

/*
 *Enum that represents the different states that a Operation can have.
 */
public enum OperationState {
        FAILED("Operación Fallida"),
        SUCCESSFUL ("Operación realizada satisfactoriamente"),
        ROULETTE_NON_EXISTENT("La ruleta con el id dado no existe"),
        BET_NOT_ALLOWED("La apuesta debe ser solamente de un sólo color o sobre un número"),
        BET_QUANTITY_ERROR("La cantidad de dinero a apostar no está dentro del rango de 1 a 10000"),
        BET_NUMBER_ERROR("El número al que se ha apostado no está dentro del rango de 0 a 36"),
        ROULETTE_CLOSED("La ruleta se encuentra cerrada, no se pueden recibir apuestas en estos momentos");
    /*
     *viewName represents the String value of a particular element inside the enum
     */
    @Getter
    private final String viewName;
    /*
     *Constructor that creates a new enum element
     */
    OperationState(String displayName) {
        this.viewName = displayName;
    }
}
