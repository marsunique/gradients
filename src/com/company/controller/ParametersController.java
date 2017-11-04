package com.company.controller;

import com.company.models.ParameterModel;
import com.company.objects.Parameter;
import com.company.objects.User;

import java.io.IOException;
import java.sql.SQLException;

public class ParametersController extends AbsController{
    private static ParametersController parametersController = null;

    private ParametersController() {
        super();
    }

    public static ParametersController getParametersController() {
        if (parametersController == null) {
            parametersController = new ParametersController();
        }
        return parametersController;
    }

    public int addParameters(int questionID) {
        Parameter parameter = new Parameter();
        parameter.setQues_id(questionID);

        System.out.println("-------------------");
        System.out.println(" Question Creation");
        System.out.println("-------------------");

        System.out.printf("Please Enter Parameters values: ");
        parameter.setParamValues(scanner.nextLine());

        int parameterID = -1;
        try {
            parameterID = ParameterModel.getParameterModel().addParameters(parameter);
        }catch (Exception e) {
            System.out.print("Error: " + e.getMessage());
        }
        return parameterID;
    }


    @Override
    public void landingPage() {

    }

    @Override
    public void setUser(User u) {

    }
}
