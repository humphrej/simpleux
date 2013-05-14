package com.specialprojectslab.simpleux.about;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public class NoticePanel extends JPanel {

    public NoticePanel() {
        super(new BorderLayout());

        //notice panel
        URL url = Resources.getResource("META-INF/NOTICE.txt");
        String text = "";
        try {
            text = Resources.toString(url, Charsets.UTF_8);
        } catch (IOException e) {
        }

        JTextArea textArea = new JTextArea(text);
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

    }
}
