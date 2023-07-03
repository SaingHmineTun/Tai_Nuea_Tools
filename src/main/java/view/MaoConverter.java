package view;

import javax.swing.*;

public class MaoConverter extends JFrame {
    private JTextArea edInput;
    private JTextArea edOutput;
    private JButton btClear;
    private JButton btConvert;
    private JButton btCopy;
    private JPanel mainPanel;
    private JRadioButton rbTDD2SHN;
    private JRadioButton rbSHN2TDD;


    public JRadioButton getRbUni2Zg() {
        return rbTDD2SHN;
    }

    public JRadioButton getRbZg2Uni() {
        return rbSHN2TDD;
    }

    public JLabel getLbInput() {
        return lbInput;
    }

    public JButton getBtCopyUnicode() {
        return btCopyTDD;
    }

    public JButton getBtCopyZawgyi() {
        return btCopySHN;
    }

    public JLabel getLbOutput() {
        return lbOutput;
    }

    private JLabel lbInput;
    private JLabel lbOutput;
    private JButton btCopyTDD;
    private JButton btCopySHN;
    private JButton btBreak;
    private JButton btConvertANDBreak;

    public JButton getBtConvertANDBreak() {
        return btConvertANDBreak;
    }

    public JButton getBtBreak() {
        return btBreak;
    }

    private JToolBar toolBar;

    public JToolBar getToolBar() {
        return toolBar;
    }

    public MaoConverter() {
        setSize(900, 600);
        setExtendedState(MAXIMIZED_BOTH);
        setContentPane(mainPanel);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Tai Nuea Converter");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException |
                 UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

    public JTextArea getEdInput() {
        return edInput;
    }

    public JTextArea getEdOutput() {
        return edOutput;
    }

    public JButton getBtClear() {
        return btClear;
    }

    public JButton getBtConvert() {
        return btConvert;
    }

    public JButton getBtCopy() {
        return btCopy;
    }
}
