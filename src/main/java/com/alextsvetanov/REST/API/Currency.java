package com.alextsvetanov.REST.API;

public class Currency implements ICurrency{
    private String name;
    private String abbr;

    public Currency(String name, String abbr) {
        this.setName(name);
        this.setAbbr(abbr);
    }

    private void setName (String name) {
        this.name = name;
    }

    private void setAbbr (String abbr) {
        this.abbr = abbr;
    }

    @Override
    public String getName () {
        return this.name;
    }

    @Override
    public String getAbbr () {
        return this.abbr;
    }
}
