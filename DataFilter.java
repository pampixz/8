import java.util.List;
import java.util.stream.Collectors;

//Обработчик данных для фильтрации.
//Убирает элементы, длина которых меньше или равна 5 символам.

public class DataFilter {
    @DataProcessor
    public List<String> filterData(List<String> data) {
        System.out.println("Фильтрация данных...");
        return data.stream() //Преобразует список в поток
                .filter(item -> item.length() > 5) // Оставляем только строки длиной более 5 символов
                .collect(Collectors.toList());
    }
}