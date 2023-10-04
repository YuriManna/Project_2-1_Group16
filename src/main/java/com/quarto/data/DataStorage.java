package com.quarto.data;

import com.quarto.setup.Pieces;

import java.io.Serializable;
import java.util.ArrayList;

public class DataStorage implements Serializable {
    ArrayList<Pieces> piece = new ArrayList<Pieces>();
ArrayList<Integer> col= new ArrayList<Integer>();
    ArrayList<Integer> row= new ArrayList<Integer>();
}
