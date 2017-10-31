package com.company.models;

import com.company.objects.Parameter;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParameterModel extends ModelBase{
    private ParameterModel parameterModel = null;
    private ParameterModel() throws IOException {
    }

    public ParameterModel getParameterModel() throws IOException {
        if (parameterModel == null) {
            parameterModel = new ParameterModel();
        }
        return parameterModel;
    }

    public void addParameters(Parameter parameter) throws SQLException {
        String query = "INSERT INTO Parameters(ques_id, param_vals) VALUES (?,?)";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setInt(1, parameter.getQues_id());
        preparedStatement.setString(2, parameter.getParamID());
    }

    public void deleteParameters(int questionID, int paramsID) throws SQLException {
        String query = "DELETE FROM Parameters WHERE ques_id = ? AND param_id = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);

        preparedStatement.setInt(1, questionID);
        preparedStatement.setInt(2, paramsID);

        preparedStatement.execute();
    }
}
