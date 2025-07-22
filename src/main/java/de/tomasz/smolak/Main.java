package de.tomasz.smolak;

import de.tomasz.smolak.view.CalendarView;
import de.tomasz.smolak.model.CalendarService;
import de.tomasz.smolak.controller.CalendarController;

import javax.swing.SwingUtilities;

/**
 * Einstiegspunkt der Kalenderanwendung.
 * <p>
 * Diese Klasse startet die Benutzeroberfläche und verbindet die MVC-Komponenten:
 * View (GUI), Controller (Logikbindung) und Service (Fachlogik).
 * </p>
 *
 * @author Tomasz Smolak
 * @version 1.0
 */
public class Main {

    /**
     * Die Hauptmethode der Anwendung. Startet die GUI im Event Dispatch Thread (EDT),
     * um Thread-Sicherheit für Swing-Komponenten zu gewährleisten.
     *
     * @param args Kommandozeilenargumente (werden nicht verwendet)
     * @see javax.swing.SwingUtilities#invokeLater(Runnable)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CalendarView view = new CalendarView();               // GUI
            CalendarService service = new CalendarService();     // Geschäftslogik
            new CalendarController(view, service);               // Steuerung

            view.setVisible(true);                               // Anzeige starten
        });
    }
}
