package com.company.models;

import com.company.objects.Parameter;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ParameterModel extends ModelBase{
    private static ParameterModel parameterModel = null;
    private ParameterModel() throws IOException {
    }

    public static ParameterModel getParameterModel() throws IOException {
        if (parameterModel == null) {
            parameterModel = new ParameterModel();
        }
        return parameterModel;
    }

    public int addParameters(Parameter parameter) throws SQLException {
        String query = "INSERT INTO Parameters(ques_id, param_vals) VALUES (?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

        preparedStatement.setInt(1, parameter.getQues_id());
        preparedStatement.setString(2, parameter.getParamValues());

        preparedStatement.execute();

        ResultSet rs = preparedStatement.getGeneratedKeys();
        rs.next();
        return rs.getInt(1); // return parameter id

    }

    public void deleteParameters(int questionID, int paramsID) throws SQLException {
        String query = "DELETE FROM Parameters WHERE ques_id = ? AND param_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setInt(1, questionID);
        preparedStatement.setInt(2, paramsID);

        preparedStatement.execute();
    }
}
