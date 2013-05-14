package com.specialprojectslab.simpleux.about;


import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.specialprojectslab.simpleux.SimpleUXUtilities;
import org.flexdock.util.ResourceManager;
import org.flexdock.util.SwingUtility;
import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.painter.ImagePainter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class AboutDialog extends JDialog {

    private final JXPanel mainPanel;
    private final NoticePanel noticePanel;
    private final BufferedImage myImage;
    private final SystemPropertiesPanel propertiesPanel ;

    public AboutDialog(JFrame parent, String imageResource) {
        super(parent, ModalityType.APPLICATION_MODAL);

        setUndecorated(true);
        // splash panel
        mainPanel = new JXPanel();
        myImage = createImage(imageResource);
       // mainPanel.setImage(myImage);

        ImagePainter painter=new ImagePainter(myImage);
        painter.setFillHorizontal(true);
        painter.setFillVertical(true);
        mainPanel.setBackgroundPainter(painter);
        mainPanel.setPreferredSize(new Dimension(500,400));


        noticePanel = new NoticePanel();
        propertiesPanel = new SystemPropertiesPanel();

        //set up together
        getContentPane().setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);
        tabbedPane.addTab("Splash", mainPanel);
        tabbedPane.addTab("Notice", noticePanel);
        tabbedPane.addTab("Properties", propertiesPanel);

        getContentPane().add(tabbedPane, BorderLayout.CENTER);
        JButton button = new JButton("OK");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AboutDialog.this.setVisible(false);
            }
        });
        getContentPane().add(button, BorderLayout.SOUTH);
        pack();

        SwingUtility.centerOnScreen(this);
    }

    public void showDialog() {
        setVisible(true);
    }

    private static BufferedImage createImage(String url) {
        try {
            URL location = ResourceManager.getResource(url);
            return SimpleUXUtilities.getScaledImage(ImageIO.read(location), 500, 400);
        } catch (IOException e) {
            return null;
        }
    }
}
