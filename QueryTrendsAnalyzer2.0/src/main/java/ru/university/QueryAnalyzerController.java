package ru.university;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class QueryAnalyzerController {

    @FXML private TextField filePathField;
    @FXML private TextField topNField;
    @FXML private BarChart<String, Number> chart;
    @FXML private Label statusLabel;

    private final QueryAnalyzer analyzer = new QueryAnalyzer();

    @FXML
    private void handleBrowse() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл с запросами");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Текстовые файлы", "*.txt"),
                new FileChooser.ExtensionFilter("Все файлы", "*.*")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            filePathField.setText(file.getAbsolutePath());
            statusLabel.setText("Файл выбран: " + file.getName());
        }
    }

    @FXML
    private void handleAnalyze() {
        try {
            // Очистка предыдущих данных
            chart.getData().clear();

            // Чтение файла
            String filePath = filePathField.getText();
            int topN = Integer.parseInt(topNField.getText());

            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    analyzer.processQuery(line);
                }
            }

            // Получение топ запросов
            var topQueries = analyzer.getTopQueries(topN);

            // Создание диаграммы
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            for (var entry : topQueries) {
                series.getData().add(new XYChart.Data<>(entry.getKey(), entry.getValue()));
            }

            chart.getData().add(series);
            statusLabel.setText("Проанализировано запросов: " + analyzer.getTotalQueries());

        } catch (Exception e) {
            statusLabel.setText("Ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }
}