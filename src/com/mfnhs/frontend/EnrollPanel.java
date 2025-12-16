package com.mfnhs.frontend;

import com.mfnhs.Main;
import com.mfnhs.backend.data.Address;
import com.mfnhs.backend.data.Name;
import com.mfnhs.backend.data.Student;
import com.mfnhs.backend.manager.StudentManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EnrollPanel extends JPanel {

    private static final Color BG = Theme.CARD_BG;
    private static final Color ACCENT = Theme.ACCENT;

    private JTextField schoolYearField;
    private JTextField gradeLevelField;
    private JTextField strandField;
    private JTextField trackField;

    private JTextField firstNameField;
    private JTextField lastNameField;
    private JTextField middleNameField;
    private JTextField extensionNameField;

    private JTextField birthdateField;
    private JTextField sexField;
    private JTextField ageField;
    private JTextField motherTongueField;
    private JTextField lrnField;
    private JTextField birthCertificateField;
    private JTextField birthplaceField;
    private JTextField indigenousPeoplesField;
    private JTextField fourPsComboBox;
    private JTextField fourPsField;
    private JTextField disabilityComboBox;
    private JTextField disabilityField;

    private JTextField currentHouseField;
    private JTextField currentBarangayField;
    private JTextField currentCityField;
    private JTextField currentCountryField;
    private JTextField currentStreetField;
    private JTextField currentZipCodeField;
    private JTextField currentProvinceField;

    private JTextField permanentHouseField;
    private JTextField permanentBarangayField;
    private JTextField permanentCityField;
    private JTextField permanentCountryField;
    private JTextField permanentStreetField;
    private JTextField permanentZipCodeField;
    private JTextField permanentProvinceField;

    private JTextField fatherFirstNameField;
    private JTextField fatherLastNameField;
    private JTextField fatherMiddleNameField;
    private JTextField fatherExtensionNameField;
    private JTextField fatherContactField;
    private JTextField motherFirstNameField;
    private JTextField motherLastNameField;
    private JTextField motherMiddleNameField;
    private JTextField motherExtensionNameField;
    private JTextField motherContactField;

    public EnrollPanel(Consumer<String> navigator) {
        setLayout(new BorderLayout());
        setBackground(Theme.OUTER_BG);

        JPanel container = new JPanel(new BorderLayout());
        container.setBorder(new EmptyBorder(12,12,12,12));
        container.setBackground(BG);
        // fixed container for static layout
        container.setPreferredSize(new Dimension(960, 560));

        JLabel header = new JLabel("STUDENT DATA FORM", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(ACCENT);
        header.setForeground(Color.WHITE);
        header.setFont(header.getFont().deriveFont(Font.BOLD, 28f));
        header.setBorder(new EmptyBorder(12,12,12,12));
        container.add(header, BorderLayout.NORTH);

        // form content in scroll pane
        JPanel content = new JPanel();
        content.setBackground(BG);
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(12,12,12,12));

        // top fields row: school year, strand, grade level, track
        RowPanel systr = rowPanel(new String[]{"School year","Strand"}, new int[]{2,2});
        content.add(systr);
        schoolYearField = systr.fieldList.get(0);
        strandField = systr.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,8)));

        RowPanel gltr = rowPanel(new String[]{"Grade level to Enroll","Track"}, new int[]{2,2});
        content.add(gltr);
        gradeLevelField = gltr.fieldList.get(0);
        trackField = gltr.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,14)));

        content.add(sectionTitle("LEARNER'S INFORMATION"));
        content.add(Box.createRigidArea(new Dimension(0,8)));

        // three-column grid for learner info
        RowPanel lnbppsa = threeColumnRow(new String[]{"Last name","Birthdate (mm/dd/yy)","PSA Birth Certificate No."});
        content.add(lnbppsa);
        lastNameField = lnbppsa.fieldList.get(0);
        birthdateField = lnbppsa.fieldList.get(1);
        birthCertificateField = lnbppsa.fieldList.get(2);
        content.add(Box.createRigidArea(new Dimension(0,8)));

        RowPanel fnsxlrn = threeColumnRow(new String[]{"First name","Sex","Learners Reference No."});
        content.add(fnsxlrn);
        firstNameField = fnsxlrn.fieldList.get(0);
        sexField = fnsxlrn.fieldList.get(1);
        lrnField = fnsxlrn.fieldList.get(2);
        content.add(Box.createRigidArea(new Dimension(0,8)));

        RowPanel mnagbp =threeColumnRow(new String[]{"Middle name","Age","Place of Birth (Municipality/City)"});
        content.add(mnagbp);
        middleNameField = mnagbp.fieldList.get(0);
        ageField = mnagbp.fieldList.get(1);
        birthplaceField = mnagbp.fieldList.get(2);
        content.add(Box.createRigidArea(new Dimension(0,8)));

        RowPanel enmt = threeColumnRow(new String[]{"Extension name","Mother tongue",""});
        content.add(enmt);
        extensionNameField = enmt.fieldList.get(0);
        motherTongueField = enmt.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,14)));

        // long text field example (indigenous etc)
        LabeledWideField newLabeledWideField = new LabeledWideField("Belonging to any Indigenous People(IP) Community/ Indigenous cultural community?");
        indigenousPeoplesField = newLabeledWideField.myField;
        content.add(newLabeledWideField);
        content.add(Box.createRigidArea(new Dimension(0,8)));

        RowPanel fourps =twoColumnRow(new String[]{"Is your family a beneficiary of 4ps","If yes, write the household ID number"});
        content.add(fourps);
        fourPsComboBox = fourps.fieldList.get(0);
        fourPsField = fourps.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,8)));

        RowPanel disab =twoColumnRow(new String[]{"Is the child a Learner with Disability","If yes, please specify"});
        content.add(disab);
        disabilityComboBox = disab.fieldList.get(0);
        disabilityField = disab.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,18)));

        RowPanel hnb = twoColumnRow(new String[]{"House no.","Barangay"});
        RowPanel mncc = twoColumnRow(new String[]{"Municipality / City","Country"});
        RowPanel stzp = twoColumnRow(new String[]{"Sitio / Street name","Zip code"});
        LabeledField prv = new LabeledWideField("Province");
        content.add(sectionTitle("CURRENT ADDRESS"));
        content.add(Box.createRigidArea(new Dimension(0,8)));
        content.add(hnb);
        currentHouseField = hnb.fieldList.get(0);
        currentBarangayField = hnb.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,8)));
        content.add(mncc);
        currentCityField = mncc.fieldList.get(0);
        currentCountryField = mncc.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,8)));
        content.add(stzp);
        currentStreetField = stzp.fieldList.get(0);
        currentZipCodeField = stzp.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,8)));
        content.add(prv);
        currentProvinceField = prv.myField;
        content.add(Box.createRigidArea(new Dimension(0,18)));

        RowPanel phnb = twoColumnRow(new String[]{"House no.","Barangay"});
        RowPanel pmncc = twoColumnRow(new String[]{"Municipality / City","Country"});
        RowPanel pstzp = twoColumnRow(new String[]{"Sitio / Street name","Zip code"});
        LabeledField pprv = new LabeledWideField("Province");
        content.add(sectionTitle("PERMANENT ADDRESS"));
        content.add(Box.createRigidArea(new Dimension(0,8)));
        content.add(phnb);
        permanentHouseField = phnb.fieldList.get(0);
        permanentBarangayField = phnb.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,8)));
        content.add(pmncc);
        permanentCityField = pmncc.fieldList.get(0);
        permanentCountryField = pmncc.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,8)));
        content.add(pstzp);
        permanentStreetField = pstzp.fieldList.get(0);
        permanentZipCodeField = pstzp.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,8)));
        content.add(pprv);
        permanentProvinceField = pprv.myField;
        content.add(Box.createRigidArea(new Dimension(0,18)));

        RowPanel fmln = twoColumnRow(new String[]{"Father's Last name","Mother's Last name"});
        RowPanel fmfn = twoColumnRow(new String[]{"First name","First name"});
        RowPanel fmmn = twoColumnRow(new String[]{"Middle name","Middle name"});
        RowPanel fmcn = twoColumnRow(new String[]{"Contact no.","Contact no."});
        content.add(sectionTitle("PARENTS / GUARDIAN'S INFORMATION"));
        content.add(Box.createRigidArea(new Dimension(0,8)));
        content.add(fmln);
        fatherLastNameField = fmln.fieldList.get(0);
        motherLastNameField = fmln.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,8)));
        content.add(fmfn);
        fatherFirstNameField = fmfn.fieldList.get(0);
        motherFirstNameField = fmfn.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,8)));
        content.add(fmmn);
        fatherMiddleNameField = fmmn.fieldList.get(0);
        motherMiddleNameField = fmmn.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,8)));
        content.add(fmcn);
        fatherContactField = fmcn.fieldList.get(0);
        motherContactField = fmcn.fieldList.get(1);
        content.add(Box.createRigidArea(new Dimension(0,18)));

        // actions
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actions.setOpaque(false);
        JButton back = new JButton("Back");
        back.addActionListener(e -> navigator.accept(App.CARD_DASHBOARD));
        JButton submit = new JButton("Submit");
        submit.addActionListener(e -> showConfirmDialog(navigator));
        actions.add(back);
        actions.add(submit);
        content.add(actions);

        JScrollPane sc = new JScrollPane(content, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        sc.getVerticalScrollBar().setUnitIncrement(16);
        sc.setBorder(new LineBorder(ACCENT,2,true));
        sc.setPreferredSize(new Dimension(920, 480));
        container.add(sc, BorderLayout.CENTER);

        add(container, BorderLayout.CENTER);
    }

    private Student studentifyInput() {
        return Student.Builder.newInstance()
                .setName(new Name(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        middleNameField.getText(),
                        extensionNameField.getText()
                ))
                .setBirthdate(null)
                .setSex(!sexField.getText().toLowerCase().startsWith("m"))
                .setMotherTongue(motherTongueField.getText())
                .setBirthCertificateNumber(birthCertificateField.getText())
                .setLRN(lrnField.getText())
                .setIP(indigenousPeoplesField.getText())
                .set4Ps(fourPsField.getText())
                .setDisability(disabilityField.getText())
                .setFatherName(new Name(
                        fatherFirstNameField.getText(),
                        fatherLastNameField.getText(),
                        fatherMiddleNameField.getText(),
                        ""
                ))
                .setMotherName(new Name(
                        motherFirstNameField.getText(),
                        motherLastNameField.getText(),
                        motherMiddleNameField.getText(),
                        ""
                ))
                .setGuardianName(new Name ("","","",""))
                .setCurrentAddress(new Address(
                        "",
                        "",
                        currentProvinceField.getText(),
                        currentCityField.getText(),
                        currentBarangayField.getText(),
                        currentStreetField.getText() + currentHouseField.getText(),
                        currentZipCodeField.getText()
                ))
                .setPermanentAddress(new Address(
                        "",
                        "",
                        permanentProvinceField.getText(),
                        permanentCityField.getText(),
                        permanentBarangayField.getText(),
                        permanentStreetField.getText() + permanentHouseField.getText(),
                        permanentZipCodeField.getText()
                ))
                .setBirthplace(new Address(
                        "",
                        "",
                        "",
                        birthplaceField.getText(),
                        "",
                        "",
                        ""
                ))
                .build();
    }

    private void showConfirmDialog(Consumer<String> navigator) {
        Window parent = SwingUtilities.getWindowAncestor(this);
        final JDialog dialog = new JDialog(parent, "Confirm Enrollment", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new LineBorder(ACCENT, 2, true));
        panel.setBackground(Color.WHITE);

        JLabel h = new JLabel("CONFIRM", SwingConstants.CENTER);
        h.setOpaque(true);
        h.setBackground(ACCENT);
        h.setForeground(Color.WHITE);
        h.setFont(h.getFont().deriveFont(Font.BOLD, 18f));
        h.setBorder(new EmptyBorder(10,10,10,10));
        panel.add(h);

        JLabel msg = new JLabel("CONFIRM ENROLLMENT", SwingConstants.CENTER);
        msg.setBorder(new EmptyBorder(12,14,12,14));
        msg.setFont(msg.getFont().deriveFont(Font.BOLD, 16f));
        msg.setForeground(ACCENT.darker());
        panel.add(msg);

        JPanel btnRow = new JPanel(new GridLayout(1,2));
        btnRow.setBorder(new EmptyBorder(6,6,6,6));
        JButton no = new JButton("No");
        JButton yes = new JButton("Yes");
        no.addActionListener(ev -> dialog.dispose());
        yes.addActionListener(ev -> {
            dialog.dispose();
            // simple confirmation action: navigate back to dashboard
            try {
                Main.createStudent(studentifyInput());
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                JOptionPane.showMessageDialog(parent, "Enrollment failed.", "Failure", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            navigator.accept(App.CARD_DASHBOARD);
            JOptionPane.showMessageDialog(parent, "Enrollment confirmed.", "Success", JOptionPane.INFORMATION_MESSAGE);
            App.updateMasterlist();
            App.updateDashboardCounts();
        });
        btnRow.add(no);
        btnRow.add(yes);
        panel.add(btnRow);

        dialog.getContentPane().add(panel);
        dialog.pack();
        dialog.setLocationRelativeTo(parent);
        dialog.setVisible(true);
    }

    private JPanel sectionTitle(String text) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(BG);
        JLabel l = new JLabel(text, SwingConstants.CENTER);
        l.setOpaque(true);
        l.setBackground(ACCENT);
        l.setForeground(Color.WHITE);
        l.setBorder(new EmptyBorder(6,6,6,6));
        p.add(l, BorderLayout.CENTER);
        return p;
    }

    private RowPanel rowPanel(String[] labels, int[] weights) {
        return new RowPanel(labels, weights);
    }

    static class RowPanel extends JPanel {
        public List<JTextField> fieldList = new ArrayList<>();

        RowPanel(String[] labels, int[] weights) {
            super();
            this.setLayout(new GridBagLayout());
            this.setBackground(BG);
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(6,6,6,6);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            int cols = labels.length;
            for (int i=0;i<cols;i++){
                gbc.gridx = i;
                gbc.weightx = 1.0 / cols;
                LabeledField field = new LabeledField(labels[i]);
                fieldList.add(field.myField);
                this.add(field, gbc);
            }
        }
    }

    private RowPanel twoColumnRow(String[] labels) {
        return rowPanel(labels, new int[]{1,1});
    }

    private RowPanel threeColumnRow(String[] labels) {
        return rowPanel(labels, new int[]{1,1,1});
    }

    static class LabeledField extends JPanel {
        public JTextField myField = new JTextField();

        LabeledField(String label) {
            super();
            this.setLayout(new BorderLayout());
            this.setBackground(BG);
            JLabel l = new JLabel(label);
            l.setBorder(new EmptyBorder(2,4,2,4));
            myField.setPreferredSize(new Dimension(200,26));
            myField.setBorder(new LineBorder(new Color(200,200,200),1,true));
            this.add(l, BorderLayout.NORTH);
            this.add(myField, BorderLayout.CENTER);
        }
    }

    static class LabeledWideField extends LabeledField {
        LabeledWideField(String label) {
            super(label);
            myField.setPreferredSize(new Dimension(400,28));
        }
    }

}
