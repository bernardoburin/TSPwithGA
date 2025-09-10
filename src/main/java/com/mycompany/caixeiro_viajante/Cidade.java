/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixeiro_viajante;

import java.util.Objects;

/**
 *
 * @author bernardoburin
 */
public final class Cidade {
    private final int id;
    private final String nome;
    private final double lat;
    private final double lon;

    public Cidade(int id, String nome, double lat, double lon) {
        this.id = id;
        this.nome = Objects.requireNonNull(nome);
        this.lat = lat;
        this.lon = lon;
    }

    public int getId() { return id; }
    public String getNome() { return nome; }
    public double getLat() { return lat; }
    public double getLon() { return lon; }

    @Override public String toString() { return nome + "(" + id + ")"; }
}

