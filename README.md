# SEG_TOOL - README

## Einleitung

Das SEG_TOOL wurde entwickelt, um die Frage zu beantworten: **"Kann eine akustische Sequenz auf Phonebene segmentiert werden?"** Die Arbeit von Machelett (1996) legt nahe, dass Segmentgrenzen im Spektrogramm visuell erkennbar sind. Unser Tool zielt darauf ab, diese Segmentgrenzen ohne visuellen Input automatisiert zu erkennen, indem es Formantwerte als Datenbasis nutzt. Diese README bietet eine detaillierte Anleitung zur Nutzung des Tools und beschreibt die technischen Details seiner Implementierung.

## Übersicht

1. [Einleitung](#einleitung)
2. [Installation](#installation)
3. [Nutzung](#nutzung)
4. [Programmierung des SEG_TOOLs](#programmierung-des-seg_tools)
   - [Klassenstruktur](#klassenstruktur)
   - [Analysestruktur](#analysestruktur)
5. [Datenverarbeitung](#datenverarbeitung)
   - [Einlesen & Aufbereiten der Daten](#einlesen--aufbereiten-der-daten)
   - [Kernanalyse – Verarbeitung der Daten](#kernanalyse--verarbeitung-der-daten)
   - [Treffer Eingrenzen](#treffer-eingrenzen)
6. [Ausgabe der Daten](#ausgabe-der-daten)
7. [Beispiele](#beispiele)
8. [Fazit](#fazit)

## Installation

1. **Voraussetzungen:**
   - Java Development Kit (JDK)
   - Eclipse IDE oder eine andere Java-Entwicklungsumgebung

2. **Schritte:**
   - Klonen oder Herunterladen des Projekts von [GitHub](URL).
   - Importieren des Projekts in Ihre Java-IDE.
   - Sicherstellen, dass alle Abhängigkeiten korrekt installiert sind.

## Nutzung

1. Starten Sie das SEG_TOOL in Ihrer IDE.
2. Wählen Sie die Datei `Formantenanalyse.txt` über das Auswahlfenster aus.
3. Das Tool analysiert die Datei und erstellt automatisch die Segmentierungsgrenzen.
4. Überprüfen Sie die Ausgabedateien (`TextGrid` und `CSV`) für die Ergebnisse.

## Programmierung des SEG_TOOLs

### Klassenstruktur

- **Datensatz**: Repräsentiert das gesamte Dokument `Formantenanalyse.txt`. Enthält Attribute wie `xmin`, `xmax`, `nx`, `dx`, `x1` und eine Liste von `Frame`-Objekten.
- **Frame**: Jedes `Frame`-Objekt enthält einen Intensitätswert und eine Liste von `Formant`-Objekten.
- **Formant**: Jedes `Formant`-Objekt enthält Frequenz- und Bandbreitenwerte.
- **Loesungssatz**: Enthält eine Liste von `Trennstelle`-Objekten.
- **Trennstelle**: Enthält die Position und Kennwerte einer Segmentierungsgrenze.

### Analysestruktur

- **Analyzer**: Die Hauptklasse zur Datenverarbeitung.
  - **Einlesen & Aufbereiten der Daten**: Datei wird eingelesen und Inhalte werden in eine ArrayList geschrieben und aufbereitet.
  - **Kernanalyse**: Besteht aus drei Schritten:
    1. **Kennwerte definieren**: Berechnung der Kennwerte für jeden Frame.
    2. **Kennwerte über Frames analysieren**: Berechnung der Differenzen zwischen den Frames und Identifikation vorläufiger Trennstellen.
    3. **Treffer eingrenzen**: Analyse der Streuung und Identifikation der endgültigen Trennstellen.

## Datenverarbeitung

### Einlesen & Aufbereiten der Daten

1. **Einlesen**: Die Datei `Formantenanalyse.txt` wird zeilenweise eingelesen und in eine ArrayList geschrieben.
2. **Aufbereiten**: Inhalte werden in Präfixe und Suffixe aufgeteilt und in ein `Datensatz`-Objekt geschrieben.

### Kernanalyse – Verarbeitung der Daten

1. **Kennwerte definieren**:
   - `freqband`: Gemittelter Wert der Formanten in Hinblick auf Frequenz und Bandbreite.
   - `Intensity`: Maximale Lautstärke eines Frames.
   - Einzelne Formantenkennwerte (`F1F`, `F2F`, `F1B`, `F2B`).
   
2. **Kennwerte über Frames analysieren**:
   - Berechnung der Differenzen (`werte_diff`).
   - Verrechnung der Differenzen mit den maximalen Werten (`daten_max`).
   - Anwendung von Konstanten und Bedingungen zur Identifikation vorläufiger Trennstellen.

3. **Treffer eingrenzen**:
   - Analyse der Streuung von vorläufigen Trennstellen.
   - Identifikation der endgültigen Trennstellen basierend auf definierten Bedingungen (z.B. kleinster `freqband`-Wert innerhalb eines Intervalls).

## Ausgabe der Daten

- **TextGrid-Datei**: Enthält die Trennstellen mit Zeitpunkten, um sie visuell in Tools wie PRAAT zu überprüfen.
- **CSV-Datei**: Enthält die Kennwerte und die Positionen der Trennstellen zur weiteren Analyse in Tools wie Excel.

## Beispiele

1. **Anwendungsbeispiel**: Analyse einer Beispiel-Datei `Formantenanalyse.txt` und Überprüfung der Ergebnisse in PRAAT.
2. **Ergebnisverbesserung**: Anpassung der Konstanten und Bedingungen, um die Segmentierungsergebnisse zu optimieren.

## Fazit

Das SEG_TOOL bietet eine automatisierte Möglichkeit, akustische Sequenzen auf Phonebene zu segmentieren, basierend auf Formantwerten. Es ist flexibel und erweiterbar, sodass verschiedene Analyseparameter und Bedingungen angepasst werden können, um optimale Ergebnisse zu erzielen. Die Ausgabeformate ermöglichen eine einfache Überprüfung und Weiterverarbeitung der Ergebnisse.

---

Mit dieser README sind Sie bestens gerüstet, um das SEG_TOOL effektiv zu nutzen und weiterzuentwickeln. Für weitere Informationen und technische Details konsultieren Sie bitte die vollständige Dokumentation im Anhang Ihrer Projektdateien.
