// Neue Version der CalendarService.java – rein mit java.util.Calendar und SimpleDateFormat
package de.tomasz.smolak.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Diese Klasse bietet Methoden zur Verarbeitung von Datum und Uhrzeit
 * basierend auf der java.util.Calendar-Klasse.
 */
public class CalendarService {

    /**
     * Gibt das aktuelle Datum und die aktuelle Uhrzeit im Format dd.MM.yyyy HH:mm:ss zurück.
     * <p>
     * Die Methode verwendet Calendar.getInstance(), um das aktuelle Datum/Zeit zu ermitteln,
     * und SimpleDateFormat zur Formatierung gemäß deutschem Datumsformat.
     *
     * @return Ein String mit dem formatierten Datum und der Uhrzeit.
     * @author Tomasz Smolak
     * @since 1.0
     */
    public String getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        return sdf.format(calendar.getTime());
    }

    /**
     * Prüft, ob ein Datum im Format dd.MM.yyyy syntaktisch korrekt und real existierend ist.
     * <p>
     * Die Methode verwendet SimpleDateFormat mit deaktivierter Toleranzprüfung (setLenient(false)),
     * sodass nur exakte und gültige Kalendertage akzeptiert werden (z.B. kein 31.02.2024).
     *
     * @param dateStr Der zu prüfende Datums-String im Format dd.MM.yyyy
     * @return true, wenn das Datum gültig und existierend ist; sonst false
     * @author Tomasz Smolak
     * @since 1.0
     */
    public boolean isValidDate(String dateStr) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Wandelt ein Datum im Format dd.MM.yyyy in ein frei definierbares Ziel-Format um.
     *<p>
     * Die Methode nutzt SimpleDateFormat zur Umwandlung eines Eingabe-Strings in ein Date-Objekt
     * und formatiert dieses anschließend im gewünschten Zielmuster (z. B. "yyyy-MM-dd" oder "MMMM yyyy").
     * Bei fehlerhafter Eingabe wird eine standardisierte Fehlermeldung zurückgegeben.
     *
     * @param input         Das Eingabedatum als String im Format dd.MM.yyyy
     * @param targetPattern Das gewünschte Zielformat (Muster gemäß SimpleDateFormat)
     * @return Der formatierte Datum-String oder eine Fehlermeldung bei ungültiger Eingabe
     */
    public String convertToFormat(String input, String targetPattern) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date parsed = inputFormat.parse(input);
            SimpleDateFormat outputFormat = new SimpleDateFormat(targetPattern, Locale.GERMANY);
            return outputFormat.format(parsed);
        } catch (ParseException e) {
            return "❗ Ungültiges Datumsformat! Bitte im Format dd.MM.yyyy eingeben.";
        }
    }

    /**
     * Prüft, ob ein Datum innerhalb eines angegebenen Datumsbereichs liegt.
     *<p>
     * Die Methode parst das Eingabedatum sowie die Grenzen des Bereichs (Start und Ende) im Format dd.MM.yyyy.
     * Anschließend wird überprüft, ob das Datum innerhalb dieses Bereichs liegt (einschließlich der Grenzen).
     * Bei ungültiger Eingabe erfolgt eine Fehlermeldung.
     *
     * @param input Das zu prüfende Datum als String im Format dd.MM.yyyy
     * @param start Der Beginn des gültigen Bereichs im selben Format
     * @param end   Das Ende des gültigen Bereichs im selben Format
     * @return Statusmeldung zur Gültigkeit des Datums im Bereich oder ein Formatfehlerhinweis
     * @author Tomasz Smolak
     * @since 1.0
     */
    public String validateDateInRange(String input, String start, String end) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date d = sdf.parse(input);
            Date s = sdf.parse(start);
            Date e = sdf.parse(end);

            if (d.before(s) || d.after(e)) {
                return "❌ Datum liegt außerhalb des gültigen Bereichs (" + start + " – " + end + ").";
            }
            return "✅ Datum liegt im gültigen Bereich.";
        } catch (ParseException e) {
            return "❗ Ungültiges Datumsformat! Bitte im Format dd.MM.yyyy eingeben.";
        }
    }

    /**
     * Berechnet die Differenz zwischen zwei Datumswerten in Jahren, Monaten und Tagen.
     *
     * Die Methode verwendet java.util.Calendar zur Bestimmung der Jahres-, Monats- und Tagesdifferenz.
     * Zusätzlich wird die Gesamtanzahl der Tage über die Zeitdifferenz in Millisekunden berechnet.
     *
     * @param d1 Erstes Datum im Format dd.MM.yyyy
     * @param d2 Zweites Datum im Format dd.MM.yyyy
     * @return Ein formatierter Text mit der Datumsdifferenz oder eine Fehlermeldung bei ungültigem Eingabeformat
     */
    public String calculateDateDifference(String d1, String d2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            Date date1 = sdf.parse(d1);
            Date date2 = sdf.parse(d2);

            if (date1.after(date2)) {
                Date temp = date1;
                date1 = date2;
                date2 = temp;
            }

            long diffMillis = date2.getTime() - date1.getTime();
            long totalDays = diffMillis / (1000 * 60 * 60 * 24);

            Calendar cal1 = Calendar.getInstance();
            cal1.setTime(date1);
            Calendar cal2 = Calendar.getInstance();
            cal2.setTime(date2);

            int years = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
            int months = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
            int days = cal2.get(Calendar.DAY_OF_MONTH) - cal1.get(Calendar.DAY_OF_MONTH);

            if (days < 0) {
                months--;
                days += cal1.getActualMaximum(Calendar.DAY_OF_MONTH);
            }
            if (months < 0) {
                years--;
                months += 12;
            }

            return String.format("Differenz:\n%d Jahre, %d Monate, %d Tage\n(insgesamt: %d Tage)",
                    years, months, days, totalDays);

        } catch (ParseException e) {
            return "❗ Ungültiges Eingabeformat! Bitte beide Daten im Format dd.MM.yyyy eingeben.";
        }
    }
}
