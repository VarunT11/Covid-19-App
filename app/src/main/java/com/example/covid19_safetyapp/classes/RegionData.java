package com.example.covid19_safetyapp.classes;

public class RegionData {

    private String stateName;
    private int totalCases;
    private int newTotalCases;
    private int activeCases;
    private int newActiveCases;
    private int recoveredCases;
    private int newRecoveredCases;
    private int deceasedCases;
    private int newDeceasedCases;

    public RegionData(String stateName, int totalCases, int activeCases, int newActiveCases, int recoveredCases, int newRecoveredCases, int deceasedCases, int newDeceasedCases) {
        this.stateName = stateName;
        this.totalCases = totalCases;
        this.activeCases = activeCases;
        this.newActiveCases = newActiveCases;
        this.recoveredCases = recoveredCases;
        this.newRecoveredCases = newRecoveredCases;
        this.deceasedCases = deceasedCases;
        this.newDeceasedCases = newDeceasedCases;

        this.newTotalCases = newActiveCases + newRecoveredCases + newDeceasedCases;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public int getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(int totalCases) {
        this.totalCases = totalCases;
    }

    public int getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(int activeCases) {
        this.activeCases = activeCases;
    }

    public int getNewActiveCases() {
        return newActiveCases;
    }

    public void setNewActiveCases(int newActiveCases) {
        this.newActiveCases = newActiveCases;
    }

    public int getRecoveredCases() {
        return recoveredCases;
    }

    public void setRecoveredCases(int recoveredCases) {
        this.recoveredCases = recoveredCases;
    }

    public int getNewRecoveredCases() {
        return newRecoveredCases;
    }

    public void setNewRecoveredCases(int newRecoveredCases) {
        this.newRecoveredCases = newRecoveredCases;
    }

    public int getDeceasedCases() {
        return deceasedCases;
    }

    public void setDeceasedCases(int deceasedCases) {
        this.deceasedCases = deceasedCases;
    }

    public int getNewDeceasedCases() {
        return newDeceasedCases;
    }

    public void setNewDeceasedCases(int newDeceasedCases) {
        this.newDeceasedCases = newDeceasedCases;
    }

    public int getNewTotalCases() {
        return newTotalCases;
    }

    public void setNewTotalCases(int newTotalCases) {
        this.newTotalCases = newTotalCases;
    }
}
