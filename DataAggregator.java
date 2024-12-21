import java.util.Collections; //для работы с коллекциями
import java.util.List;

//Обработчик данных для агрегации.
//Подсчитывает общее количество элементов в данных.

public class DataAggregator {
    @DataProcessor
    public List<String> aggregateData(List<String> data) {
        System.out.println("Агрегация данных...");
        return Collections.singletonList("Total items: " + data.size()); // Создаем одну строку с результатом
               //неизменяемый список со строкой с результатом
    }
}