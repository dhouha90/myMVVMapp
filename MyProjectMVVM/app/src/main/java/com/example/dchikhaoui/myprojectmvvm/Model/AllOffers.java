package com.example.dchikhaoui.myprojectmvvm.Model;

import java.util.List;

public class AllOffers {

    List<offers> offers ;

    public List<offers> getListOffers() {
        return offers;
    }

    public void setListOffers(List<offers> listOffers) {
        this.offers = listOffers;
    }

    @Override
    public String toString() {
        return "AllOffers{" +
                "listOffers=" + offers.toString() +
                '}';
    }
}
