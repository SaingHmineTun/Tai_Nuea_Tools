package controller;

import breaker.ShanSyllableBreaker;
import breaker.TaiNueaSyllableBreaker;
import converter.TaiNueaConverter;
import detector.TaiNueaDetector;
import listener.ClipboardTextListener;
import listener.MenuItemListener;
import toast.Toast;
import utils.Utils;
import view.MaoConverter;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class MaoConverterController implements ActionListener, ChangeListener {

    public MaoConverter maoConverter;
    private JTextArea edInput, edOutput;
    private JButton btConvert, btBreak, btConvertANDBreak, btClear, btCopy, btCopySHN, btCopyTDD;
    private Font tddFont, shnFont;
    private JRadioButton rbTDD2SHN, rbSHN2TDD;
    private JLabel lbInput, lbOutput;
    private JMenuItem open, save, exit;
    private JCheckBoxMenuItem enablePopup;
    private ClipboardTextListener clipboardListener;
    private JMenuItem fileNameConverter;

    public MaoConverterController() throws InvocationTargetException, InterruptedException {
        initializeComponents();
        initializeMenubar();
        initializeAppIcons();
        initializeListeners();
        initializeMenuListeners();

        /*
        Select Shan to Tai Nuea at the startup
         */
        rbSHN2TDD.setSelected(true);
    }

    private void initializeMenuListeners() {
        MenuItemListener listener = new MenuItemListener(this);
        open.addActionListener(listener);
        save.addActionListener(listener);
        exit.addActionListener(listener);
        fileNameConverter.addActionListener(listener);
        enablePopup.addItemListener(e -> {
            if (e.getStateChange() == 1) {
                clipboardListener = new ClipboardTextListener(maoConverter);
                new Thread(clipboardListener).start();
            } else if (e.getStateChange() == 2) {
                clipboardListener.terminate();
            }
        });
    }

    private void initializeMenubar() {

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();

        // File Menu
        JMenu file = new JMenu("File");
        open = new JMenuItem("Open");
        open.setToolTipText("Open text file and import to input text");
        save = new JMenuItem("Save");
        save.setToolTipText("Save output text as text file");
        exit = new JMenuItem("Exit");
        exit.setToolTipText("Exit");
        file.add(open);
        file.add(save);
        file.add(exit);

        // Tool Menu
        JMenu tool = new JMenu("Tool");
        enablePopup = new JCheckBoxMenuItem("Enable Popup Converter");
        enablePopup.setToolTipText("Enable Dialog Convert when copy text!");
        fileNameConverter = new JMenuItem("File Name Converter");
        fileNameConverter.setToolTipText("Convert file name written in Zawgyi to Unicode");

        tool.add(fileNameConverter);
        tool.add(enablePopup);

        // Add Menu to MenuBar
        menuBar.add(file);
        menuBar.add(tool);
        maoConverter.setJMenuBar(menuBar);
    }


    private void initializeAppIcons() {

//        ArrayList<Image> icons = new ArrayList<>();
//        icons.add(new ImageIcon(getClass().getResource("/resources/icons/tmkfontconverter2forpc_16.png")).getImage());
//        icons.add(new ImageIcon(getClass().getResource("/resources/icons/tmkfontconverter2forpc_24.png")).getImage());
//        icons.add(new ImageIcon(getClass().getResource("/resources/icons/tmkfontconverter2forpc_32.png")).getImage());
//        icons.add(new ImageIcon(getClass().getResource("/resources/icons/tmkfontconverter2forpc_48.png")).getImage());
//        icons.add(new ImageIcon(getClass().getResource("/resources/icons/tmkfontconverter2forpc_128.png")).getImage());
//        icons.add(new ImageIcon(getClass().getResource("/resources/icons/tmkfontconverter2forpc_256.png")).getImage());
//        icons.add(new ImageIcon(getClass().getResource("/resources/icons/tmkfontconverter2forpc_512.png")).getImage());
//        maoConverter.setIconImages(icons);

    }

    private void initializeListeners() {

        btBreak.addActionListener(this);
        btConvert.addActionListener(this);
        btConvertANDBreak.addActionListener(this);
        btClear.addActionListener(this);
        btCopy.addActionListener(this);
        rbSHN2TDD.addChangeListener(this);
        rbTDD2SHN.addChangeListener(this);
        btCopyTDD.addActionListener(this);
        btCopySHN.addActionListener(this);
    }

    private void initializeComponents() {
        maoConverter = new MaoConverter();
        try {
            tddFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/fonts/tdd_microsoft.ttf")).deriveFont(14f);
            shnFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/fonts/shn_pyidaungsu.ttf")).deriveFont(14f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(tddFont);
            ge.registerFont(shnFont);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }

        edInput = maoConverter.getEdInput();
        edInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent documentEvent) {

                if (TaiNueaDetector.isTaiNuea(edInput.getText())) {
                    rbTDD2SHN.setSelected(true);
                } else {
                    rbSHN2TDD.setSelected(true);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent documentEvent) {

            }

            @Override
            public void changedUpdate(DocumentEvent documentEvent) {

            }
        });
        edInput.setFont(tddFont);
        edOutput = maoConverter.getEdOutput();
        edOutput.setFont(tddFont);
        btConvert = maoConverter.getBtConvert();
        btBreak = maoConverter.getBtBreak();
        btConvertANDBreak = maoConverter.getBtConvertANDBreak();
        btClear = maoConverter.getBtClear();
        btCopy = maoConverter.getBtCopy();
        btCopySHN = maoConverter.getBtCopyZawgyi();
        btCopyTDD = maoConverter.getBtCopyUnicode();
        rbTDD2SHN = maoConverter.getRbUni2Zg();
        rbSHN2TDD = maoConverter.getRbZg2Uni();
        lbInput = maoConverter.getLbInput();
        lbOutput = maoConverter.getLbOutput();


    }

    public void showWindows() {
        maoConverter.setVisible(true);
    }

    private final static String TDD = "Tai Nuea";
    private final static String SHN = "Shan";

    @Override
    public void actionPerformed(ActionEvent e) {
        /*
        CONVERT
         */
        if (e.getActionCommand().equalsIgnoreCase("convert")) {

            String convertedString;
            if (rbSHN2TDD.isSelected()) {
                convertedString = TaiNueaConverter.shn2tdd(edInput.getText());
            } else {
                convertedString = TaiNueaConverter.tdd2shn(edInput.getText());
            }
            edOutput.setText(convertedString);
        }
        /*
        CLEAR
         */
        else if (e.getActionCommand().equalsIgnoreCase("clear")) {
            edInput.setText("");
            edOutput.setText("");
        }
        /*
        Copy BOTH
         */
        else if (e.getActionCommand().equalsIgnoreCase("copy both")) {
            String myString;
            if (rbSHN2TDD.isSelected()) {

                myString = "#Tai Nuea#\n" + edOutput.getText() + "\n#Shan#\n" + edInput.getText() + "\n";
            } else {

                myString = "#Tai Nuea#\n" + edInput.getText() + "\n#Shan#\n" + edOutput.getText() + "\n";
            }
            Utils.setShowCopyDialog(false);
            Utils.copyToClipboard(myString);
            showToast("Both Tai Nuea and Shan copied successfully!");
        }
        /*
        Copy Tai Nuea
         */
        else if (e.getActionCommand().equalsIgnoreCase("copy tai nuea")) {
            Utils.setShowCopyDialog(false);
            String myString;
            if (rbSHN2TDD.isSelected()) {
                myString = edOutput.getText();
            } else {
                myString = edInput.getText();
            }
            Utils.copyToClipboard(myString);
            showToast(TDD.concat(" Copied Successfully!"));
        }
        /*
        Copy SHAN
         */
        else if (e.getActionCommand().equalsIgnoreCase("copy shan")) {

            Utils.setShowCopyDialog(false);
            String myString;
            if (rbSHN2TDD.isSelected()) {
                myString = edInput.getText();
            } else {
                myString = edOutput.getText();
            }
            Utils.copyToClipboard(myString);
            showToast(SHN.concat(" copied successfully!"));
        }
        /*
        Syllable Break
         */
        else if (e.getActionCommand().equalsIgnoreCase("Syllable Break")) {

            String syllableBreakString;
            if (rbSHN2TDD.isSelected()) {
                syllableBreakString = ShanSyllableBreaker.syllable_break(edInput.getText());
            } else {
                syllableBreakString = TaiNueaSyllableBreaker.syllable_break(edInput.getText());
            }
            edOutput.setText(syllableBreakString);
        }
        /*
        CONVERT & Syllable Break
         */
        else if (e.getActionCommand().equalsIgnoreCase("Convert AND Break")) {

            String convertedANDbreakString;
            if (rbSHN2TDD.isSelected()) {
                convertedANDbreakString = TaiNueaConverter.shn2tdd(edInput.getText());
                convertedANDbreakString = TaiNueaSyllableBreaker.syllable_break(convertedANDbreakString);
            } else {
                convertedANDbreakString = TaiNueaConverter.tdd2shn(edInput.getText());
                convertedANDbreakString = ShanSyllableBreaker.syllable_break(convertedANDbreakString);
            }
            edOutput.setText(convertedANDbreakString);
        }
    }

    private void showToast(String s) {
        Toast toast = new Toast(s);
        toast.showToast(maoConverter.getX() + maoConverter.getWidth() / 2, maoConverter.getY() + maoConverter.getHeight() / 2 + maoConverter.getHeight() / 3);

    }

    private boolean isSHN2TDD = true;

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        /*
        To prevent multiple change event from firing,
        I just re-assure with boolean value
         */
        if (rbSHN2TDD.isSelected() && isSHN2TDD) {

            lbOutput.setText(TDD);
            lbInput.setText(SHN);
            edInput.setFont(shnFont);
            edOutput.setFont(tddFont);
            isSHN2TDD = false;
        } else if (rbTDD2SHN.isSelected() && !isSHN2TDD) {
            lbOutput.setText(SHN);
            lbInput.setText(TDD);
            edInput.setFont(tddFont);
            edOutput.setFont(shnFont);
            isSHN2TDD = true;
        }
    }
}
