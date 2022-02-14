package com.aaroncohen.WhispR;

import javax.swing.*;

import static com.aaroncohen.WhispR.WhispR.settings;

/**
 * Code written by Aaron Cohen
 * File Created On: 02/11/2022
 */

public class SettingsPanel {
    private JPanel contentPanel;
    private JLabel titleLabel;
    private JComboBox themeBox;
    private JCheckBox rememberRoomCodesBox;
    private JButton saveButton;
    private JButton cancelButton;
    private JCheckBox rememberNameBox;
    private JCheckBox notificationsBox;

    private JPanel parent;

    public SettingsPanel(JPanel parent) {
        this.parent = parent;

        //set up components
        titleLabel.putClientProperty("FlatLaf.styleClass", "h1");
        //theme box
        themeBox.addItem("Dark");
        themeBox.addItem("Light");
        themeBox.setSelectedIndex(settings.theme == Settings.Theme.Dark ? 0 : 1);
        //remember name
        rememberNameBox.setSelected(settings.rememberName);
        //remember room codes
        rememberRoomCodesBox.setSelected(settings.rememberRoomCodes);
        //notifications
        notificationsBox.setSelected(settings.notificationSounds);
        //save and cancel actions
        saveButton.addActionListener((e -> {
            try {
                //save settings here
                settings.theme = (themeBox.getSelectedIndex() == 0) ? Settings.Theme.Dark : Settings.Theme.Light;
                settings.rememberName = rememberNameBox.isSelected();
                settings.rememberRoomCodes = rememberRoomCodesBox.isSelected();
                settings.notificationSounds = notificationsBox.isSelected();
                settings.save();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error: Could not save changes", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
            parent.add(new DefaultPanel(parent).getPanel());
            parent.remove(this.getPanel());
        }));
        cancelButton.addActionListener((e -> {
            parent.add(new DefaultPanel(parent).getPanel());
            parent.remove(this.getPanel());
        }));
    }

    public JPanel getPanel() {
        return contentPanel;
    }
}
