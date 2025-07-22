package de.tomasz.smolak.controller;

import de.tomasz.smolak.model.CalendarService;
import de.tomasz.smolak.view.CalendarView;

import java.util.Map;

/**
 * Der {@code CalendarController} verknüpft die Benutzeroberfläche (View)
 * mit der Geschäftslogik (Service).
 * <p>
 * Diese Klasse verarbeitet Benutzeraktionen und delegiert sie an den {@link CalendarService}.
 * Ergebnisse werden im Ausgabebereich der {@link CalendarView} angezeigt.
 * </p>
 *
 * @author Tomasz Smolak
 * @version 1.1
 * @since 1.0
 */
public class CalendarController {

    private final CalendarView view;
    private final CalendarService service;

    /**
     * Konstruktor zum Initialisieren des Controllers mit der View und dem Service.
     * Registriert alle Aktionen für die Bedienelemente.
     *
     * @param view    die Benutzeroberfläche
     * @param service die Logik zur Verarbeitung von Datum und Uhrzeit
     */
    public CalendarController(CalendarView view, CalendarService service) {
        this.view = view;
        this.service = service;
        initController();
    }

    /**
     * Registriert ActionListener für alle Buttons der View.
     */
    private void initController() {
        view.currentDateButton.addActionListener(_ -> showCurrentDateTime());
        view.validateButton.addActionListener(_ -> validateDate());
        view.convertButton.addActionListener(_ -> convertDateFormat());
        view.checkRangeButton.addActionListener(_ -> checkDateRange());
        view.diffButton.addActionListener(_ -> calculateDifference());
    }

    /**
     * Zeigt das aktuelle Datum und die aktuelle Uhrzeit an.
     * Ruft {@link CalendarService#getCurrentDateTime()} auf und zeigt das Ergebnis im Ausgabefeld.
     */
    private void showCurrentDateTime() {
        String result = service.getCurrentDateTime();
        view.outputArea.setText("Aktuelles Datum & Uhrzeit:\n" + result);
    }

    /**
     * Validiert das eingegebene Datum im Format {@code dd.MM.yyyy}.
     * Gibt eine Erfolgsmeldung oder eine Fehlermeldung im Ausgabefeld aus.
     */
    private void validateDate() {
        String input = view.dateField1.getText().trim();
        if (input.isEmpty()) {
            view.outputArea.setText("Bitte ein Datum eingeben.");
            return;
        }

        boolean isValid = service.isValidDate(input);
        if (isValid) {
            view.outputArea.setText("Das eingegebene Datum ist gültig.");
        } else {
            view.outputArea.setText("Ungültiges Datum oder falsches Format (dd.MM.yyyy).");
        }
    }

    /**
     * Wandelt ein Datum in das vom Benutzer gewählte Zielformat um.
     * Unterstützt vier Formate (ISO, Deutsch, US, Langform).
     * Erwartetes Eingabeformat: {@code dd.MM.yyyy}.
     */
    private void convertDateFormat() {
        String input = view.dateField1.getText().trim();
        String auswahl = (String) view.formatBox.getSelectedItem();

        if (input.isEmpty()) {
            view.outputArea.setText("Bitte ein Datum eingeben.");
            return;
        }

        Map<String, String> formatMap = Map.of(
                "Format 1 – ISO (yyyy-MM-dd)", "yyyy-MM-dd",
                "Format 2 – Deutsch (dd.MM.yyyy)", "dd.MM.yyyy",
                "Format 3 – US (MM/dd/yyyy)", "MM/dd/yyyy",
                "Format 4 – Langform (EEEE, dd MMMM yyyy)", "EEEE, dd MMMM yyyy"
        );

        String pattern = formatMap.getOrDefault(auswahl, "yyyy-MM-dd");
        String result = service.convertToFormat(input, pattern);
        view.outputArea.setText("Umgewandeltes Datum:\n" + result);
    }

    /**
     * Prüft, ob das eingegebene Datum innerhalb eines gültigen Bereichs liegt.
     * Der Bereich ist festgelegt auf {@code 01.01.1900} bis {@code 31.12.2100}.
     */
    private void checkDateRange() {
        String input = view.dateField1.getText().trim();
        String start = "01.01.1900";
        String end = "31.12.2100";

        if (input.isEmpty()) {
            view.outputArea.setText("Bitte ein Datum eingeben.");
            return;
        }

        String resultMessage = service.validateDateInRange(input, start, end);
        view.outputArea.setText(resultMessage);
    }

    /**
     * Berechnet die Differenz zwischen zwei eingegebenen Daten.
     * Gibt Jahre, Monate, Tage sowie die Gesamtdauer in Tagen aus.
     */
    private void calculateDifference() {
        String input1 = view.dateField1.getText().trim();
        String input2 = view.dateField2.getText().trim();

        if (input1.isEmpty() || input2.isEmpty()) {
            view.outputArea.setText("Bitte beide Datumsfelder ausfüllen.");
            return;
        }

        String result = service.calculateDateDifference(input1, input2);
        view.outputArea.setText(result);
    }
}
