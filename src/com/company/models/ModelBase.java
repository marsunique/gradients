package com.company.models;

import com.company.util.DBConnector;

import java.io.IOException;
import java.sql.Connection;

public abstract class ModelBase {
    Connection conn = null;
    public ModelBase() throws IOException {
        conn = DBConnector.getConnector().getConn();
    }
}
