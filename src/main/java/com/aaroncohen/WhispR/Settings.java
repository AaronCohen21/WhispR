package com.aaroncohen.WhispR;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/11/2022
 */

public class Settings {

    /**
     * the color scheme of the application
     * default = Dark
     */
    protected enum Theme {
        Dark,
        Light
    }
    protected Theme theme;

    /**
     * if on, what to autofill the name field with when opening the home screen
     * default = blank
     */
    protected boolean rememberName;
    protected String name;

    /**
     * if on, the last used valid room code will be saved and autofilled on the home screen
     * default = off
     */
    protected boolean rememberRoomCodes;
    protected String lastRoomCode;

    /**
     * window config options:
     *
     * Position defaults to (-1,-1) which is the center of the screen,
     * saved each time the screen is moved
     *
     * Size defaults to "new Dimension(574, 435)", saved each time the window size is changed
     */
    protected int posx, posy;
    protected Dimension size;

    public Settings() {
        //All default settings are declared here
        theme = Theme.Dark;
        rememberName = true;
        name = "";

        rememberRoomCodes = true;
        lastRoomCode = "";

        posx = -1;
        posy = -1;

        size = new Dimension(574, 435);
    }

    public void save() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String data = gson.toJson(this);
        PrintWriter pw = new PrintWriter(new FileWriter(System.getProperty("user.home") + "/.WhispR"));
        pw.print(data);
        pw.close();
    }
}
