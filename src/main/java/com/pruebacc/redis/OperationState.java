package com.pruebacc.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;


public enum OperationState {
        FAILED("Operación Fallida"),
        SUCCESSFUL ("Operación realizada satisfactoriamente");

    @Getter
    private final String viewName;
    OperationState(String displayName) {
        this.viewName = displayName;
    }
}
