package view;

import converter.TaiNueaConverter;
import detector.TaiNueaDetector;
import filepicker.JFilePicker;
import toast.Toast;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FileNameConverter extends JDialog {

    private final JCheckBox cbAutoDetect;
    //    private JFileChooser input, output;
    private File folder;

    public FileNameConverter(JFrame parent) {
        super(parent, "File Name Converter", true);
        setLayout(new FlowLayout());

        // set up a file picker component
        JFilePicker inputFolder = new JFilePicker("Choose input folder", "Browse...");
        inputFolder.setMode(JFilePicker.MODE_SAVE);
        inputFolder.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        input = inputFolder.getFileChooser();

        JPanel panel = new JPanel();
        FlowLayout layout1 = new FlowLayout();
        layout1.setHgap(25);
        panel.setLayout(layout1);

        cbAutoDetect = new JCheckBox("Auto Detect");
        cbAutoDetect.setToolTipText("If enabled, converter will only convert Zawgyi file name!");

        // Create a convert Button
        JButton btConvert = new JButton("Convert to Unicode");
        btConvert.setToolTipText("Will convert all file name to Unicode!");
        btConvert.addActionListener(e -> {
            if (inputFolder.isFolderSelected()) {
                folder = inputFolder.getFile();
                convertFileNameToUnicode();
            } else {
                Toast toast = new Toast("No folder is selected yet");
                toast.showToast(getX(), getY() + getHeight());
            }
        });

        panel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        panel.add(cbAutoDetect);
        panel.add(btConvert);

        // add the component to the frame
        add(inputFolder);
        add(panel);

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(500, 150);
        setLocationRelativeTo(null);    // center on screen
        setVisible(true);
    }

    private void convertFileNameToUnicode() {

        Toast toast = new Toast("All files have been converted to Unicode!");
        File[] inputFolder = folder.listFiles();
        boolean autoDetect = cbAutoDetect.isSelected();
        if (inputFolder != null) {
            for (File file : inputFolder) {
                String convertedName;
                if (autoDetect) {
                    if (TaiNueaDetector.isTaiNuea(file.getName())) {
                        convertedName = TaiNueaConverter.tdd2shn(file.getName());
                        file.renameTo(new File(folder, convertedName));
                    }
                } else {
                    convertedName = TaiNueaConverter.shn2tdd(file.getName());
                    file.renameTo(new File(folder, convertedName));
                }
            }
        }
        toast.showToast(getX(), getY() + getHeight());
    }
}
