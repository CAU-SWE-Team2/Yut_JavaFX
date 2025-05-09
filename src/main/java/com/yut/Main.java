package com.yut;

import javax.swing.SwingUtilities;

import com.yut.ui.swing.MainFrame;
import com.yut.controller.TitleController;
import com.yut.controller.model_interfaces.GameModelInterface;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            MainFrame mainFrame = new MainFrame();

            TitleController titleController = new TitleController(mainFrame);

            titleController.start();
        });

    }
}