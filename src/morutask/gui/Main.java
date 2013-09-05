/*
 * MoruTask
 * Copyright (c) 2013, Mohand Andel
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of MoruTask or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package morutask.gui;

import com.leclercb.commons.api.coder.exc.FactoryCoderException;
import com.leclercb.commons.api.properties.PropertyMap;
import morutask.gui.threads.reminder.ReminderThread;
import morutask.gui.trayIcons.MainIconTray;
import morutask.gui.trayIcons.TimerTrayIcon;
import morutask.models.NoteFactory;
import morutask.models.TaskFactory;

import javax.swing.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author mohand
 */
public class Main {

    private static PropertyMap SETTINGS;
    //private static boolean isModelsChanged = false;
    //private static boolean isSettingChanged = false;

    @SuppressWarnings("null")
    public static void main(String[] args) throws Exception {

        SETTINGS = new PropertyMap();

        loadSettings(SETTINGS);
        // Load models from files
        loadModels();

        initTrayIcons();

        ReminderThread reminderThread = new ReminderThread();
        reminderThread.start();

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {
                    javax.swing.UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    //javax.swing.UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");//info.getClassName());
                } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }

                MainFrame gui = new MainFrame();
                gui.setVisible(true);
            }
        });

    }

    public static void initTrayIcons() {

        MainIconTray mainIconTray = new MainIconTray();
        TimerTrayIcon timerTrayIcon = new TimerTrayIcon();
    }

    public static PropertyMap getSettings() {
        return SETTINGS;
    }

    public static void setSettings(PropertyMap SETTINGS) {
        Main.SETTINGS = SETTINGS;
    }


    public static void loadSettings(PropertyMap setting) {
        try {
            setting.load(new FileInputStream("data" + File.separator + "settings.properties"));
        } catch (Exception e) {
            System.err.println("Cannot load property file");
            throw new RuntimeException();
        }
    }

    public static void saveSettings() {
//        if( ! isSettingChanged)
//            return;

        try {
            System.out.println("Saving Setting !!!");
            SETTINGS.store(new FileOutputStream("data" + File.separator + "settings.properties"), null);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveData() {


    }

    public static void loadModels() throws FactoryCoderException {


        try {
            NoteFactory.getInstance().decodeFromXML(
                    new FileInputStream("data" + File.separator + "notes.xml"));
        } catch (FileNotFoundException e) {
        }

        try {
            TaskFactory.getInstance().decodeFromXML(
                    new FileInputStream("data" + File.separator + "tasks.xml"));
        } catch (FileNotFoundException e) {
        }
    }

    public static void saveModels() throws FileNotFoundException,
            FactoryCoderException {

        System.out.println("Saving Models...");

        NoteFactory.getInstance().cleanFactory();
        TaskFactory.getInstance().cleanFactory();

        NoteFactory.getInstance().encodeToXML(
                new FileOutputStream("data" + File.separator + "notes.xml"));
        TaskFactory.getInstance().encodeToXML(
                new FileOutputStream("data" + File.separator + "tasks.xml"));
    }

}
