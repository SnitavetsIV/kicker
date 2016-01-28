package com.snit.kicker.entity;

/**
 * @author Ilya Snitavets
 */
public class User {

    public static final String TABLE = "user";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DELETED = "deleted";

    private int id = -1;
    private String name;
    private boolean deleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return name;
    }
}
