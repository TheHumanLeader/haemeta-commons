package com.haemeta.common.exception.response.base;

public abstract class ResMissingException extends Exception {

    protected String missingParameter;

    public String getMissingParameter() {
        return missingParameter;
    }

    public void setMissingParameter(String missingParameter) {
        this.missingParameter = missingParameter;
    }
}
