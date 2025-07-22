package de.tomasz.smolak.view;

import javax.swing.*;
import java.awt.*;

/**
 * Diese Klasse stellt die grafische Benutzeroberfläche (GUI) für die Kalender-Anwendung bereit.
 * Sie basiert auf {@link JFrame} und verwendet {@link GridBagLayout} für ein flexibles Layout.
 *
 * <p>Der Benutzer kann:</p>
 * <ul>
 *     <li>ein oder zwei Datumsangaben eingeben</li>
 *     <li>ein Zielformat auswählen</li>
 *     <li>verschiedene Operationen über Buttons ausführen</li>
 *     <li>Ergebnisse im Textbereich unten einsehen</li>
 * </ul>
 *
 * @author Tomasz
 * @since 1.0
 */
public class CalendarView extends JFrame {

    /** Erstes Eingabefeld für ein Datum (Pflichtfeld für viele Operationen) */
    public JTextField dateField1;

    /** Zweites Eingabefeld (z. B. zur Differenzberechnung) */
    public JTextField dateField2;

    /** Auswahlbox zur Wahl des Zielformats bei der Umwandlung */
    public JComboBox<String> formatBox;

    /** Button zur Anzeige des aktuellen Datums und der aktuellen Uhrzeit */
    public JButton currentDateButton;

    /** Button zur Prüfung, ob ein Datum korrekt formatiert und gültig ist */
    public JButton validateButton;

    /** Button zur Umwandlung des Datums in ein anderes Format */
    public JButton convertButton;

    /** Button zur Prüfung, ob ein Datum innerhalb eines gültigen Bereichs liegt */
    public JButton checkRangeButton;

    /** Button zur Berechnung der Differenz zwischen zwei Daten */
    public JButton diffButton;

    /** Textbereich für die Ausgabe von Ergebnissen und Rückmeldungen */
    public JTextArea outputArea;

    /**
     * Konstruktor: Initialisiert und arrangiert alle GUI-Komponenten.
     * Das Fenster ist nicht skalierbar und erscheint zentriert.
     *
     * @since 1.0
     */
    public CalendarView() {
        setTitle("Kalender Anwendung");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(850, 400);
        setLocationRelativeTo(null); // zentriert das Fenster
        setResizable(false);         // Fenstergröße ist fix

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Eingabefeld 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Datum 1 (z.B. dd.MM.yyyy):"), gbc);

        dateField1 = new JTextField(20);
        gbc.gridx = 1;
        add(dateField1, gbc);

        // Eingabefeld 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Datum 2 (optional, für Differenz):"), gbc);

        dateField2 = new JTextField(20);
        gbc.gridx = 1;
        add(dateField2, gbc);

        // Format-Auswahl
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Zielformat:"), gbc);

        formatBox = new JComboBox<>(new String[]{
                "Format 1 – ISO (yyyy-MM-dd)",
                "Format 2 – Deutsch (dd.MM.yyyy)",
                "Format 3 – US (MM/dd/yyyy)",
                "Format 4 – Langform (EEEE, dd MMMM yyyy)"
        });

        gbc.gridx = 1;
        add(formatBox, gbc);

        // Buttons: gemeinsames Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        currentDateButton = new JButton("Aktuelles Datum/Uhrzeit");
        validateButton = new JButton("Datum prüfen");
        convertButton = new JButton("Format umwandeln");
        checkRangeButton = new JButton("Gültigkeit prüfen");
        diffButton = new JButton("Differenz berechnen");

        buttonPanel.add(currentDateButton);
        buttonPanel.add(validateButton);
        buttonPanel.add(convertButton);
        buttonPanel.add(checkRangeButton);
        buttonPanel.add(diffButton);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        add(buttonPanel, gbc);

        // Textbereich zur Ausgabe
        outputArea = new JTextArea(8, 50);
        outputArea.setEditable(false); // keine Benutzereingabe
        JScrollPane scrollPane = new JScrollPane(outputArea);

        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.BOTH;
        add(scrollPane, gbc);
    }
}
