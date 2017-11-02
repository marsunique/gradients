package com.company.controller;

import java.util.Scanner;

public abstract class AbsController implements Controller {
    Scanner scanner = null;
    public AbsController() {
        scanner = new Scanner(System.in);
    }
}
