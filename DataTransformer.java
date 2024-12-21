import java.util.List; //для хранения данных
import java.util.stream.Collectors;//для сбора элементов потока в различные коллекции

//Обработчик данных для преобразования.
//Преобразует все строки в верхний регистр.

public class DataTransformer {
    @DataProcessor
    public List<String> transformData(List<String> data) {
        System.out.println("Трансформация данных...");
        return data.stream()
                .map(String::toUpperCase) // Преобразуем строки в верхний регистр
                .collect(Collectors.toList()); // Преобразуем поток данных в список
    }
}