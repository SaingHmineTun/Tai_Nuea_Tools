package view;

import converter.TaiNueaConverter;
import detector.TaiNueaDetector;
import utils.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MaoPopupConverter extends JDialog implements ActionListener {
    private JTextArea taResult;
    private JPanel contentPane;
    private JButton btConvert;
    private JButton btCopy;
    private static MaoPopupConverter maoPopupConverter;
    private Font shnFont, tddFont;

    public static MaoPopupConverter getInstance() {
        if (maoPopupConverter == null) {
            maoPopupConverter = new MaoPopupConverter();
        }
        return maoPopupConverter;
    }

    public MaoPopupConverter() {
        initializeUI();
        initializeListeners();
    }

    private void initializeListeners() {
        btConvert.addActionListener(this);
        btCopy.addActionListener(this);
    }

    private void initializeUI() {
        setContentPane(contentPane);
        setSize(400, 275);
        setAlwaysOnTop(true);
        setTitle("Tai Nuea Tools");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setResizable(false);
        try {
            shnFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/fonts/shn_pyidaungsu.ttf")).deriveFont(14f);
            tddFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/resources/fonts/tdd_microsoft.ttf")).deriveFont(14f);

        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        taResult.setFont(shnFont);
    }

    public void show(String str) {
        String convertedString;
        if (TaiNueaDetector.isTaiNuea(str)) {
            convertedString = TaiNueaConverter.tdd2shn(str);
            taResult.setFont(shnFont);
        } else {
            convertedString = TaiNueaConverter.shn2tdd(str);
            taResult.setFont(tddFont);
        }
        taResult.setText(convertedString);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equalsIgnoreCase("convert")) {
            String convertedText;
            /*
            Detect if the text is Tai Nuea,
            if it was then convert Tai Nuea to Shan
             */
            if (TaiNueaDetector.isTaiNuea(taResult.getText())) {
                convertedText = TaiNueaConverter.tdd2shn(taResult.getText());
                taResult.setFont(shnFont);
            } else {
                convertedText = TaiNueaConverter.shn2tdd(taResult.getText());
                taResult.setFont(tddFont);
            }
            taResult.setText(convertedText);
        } else if (e.getActionCommand().equalsIgnoreCase("copy")) {
            Utils.setShowCopyDialog(false);
            Utils.copyToClipboard(taResult.getText());
        }
    }
}
