import java.lang.reflect.Method; //для получения методов объектов обработчиков
import java.util.ArrayList; //для хранения данных
import java.util.List; //для того чтобы обработчики были в виде коллекции
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit; //для работы с временными интервалами
import java.nio.file.Files; //для работы с файловой системой
import java.nio.file.Paths;
import java.io.IOException;

//DataManager отвечает за загрузку данных, многопоточную обработку
//с использованием методов, помеченных @DataProcessor, и сохранение результатов.
public class DataManager {
    private final List<Object> processors = new ArrayList<>(); // Список обработчиков данных
    private final List<String> data = new ArrayList<>(); // Загруженные данные
    private final List<String> processedData = new ArrayList<>(); // Обработанные данны
    // Регистрирует объект-обработчик с методами, помеченными @DataProcessor.

    // processor Объект, содержащий методы обработки данных

    public void registerDataProcessor(Object processor) { //регистрирует обработчик в системе
        processors.add(processor);
        System.out.println("Обработчик зарегистрирован: " + processor.getClass().getSimpleName());
    }

    //Загружает данные из файла.
    // source Путь к входному файлу
    public void loadData(String source) {
        try {
            // Чтение всех строк из файла
            data.clear();
            data.addAll(Files.readAllLines(Paths.get(source)));
            System.out.println("Данные загружены из " + source + ": " + data);
        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + source);
            e.printStackTrace();
        }
    }

    //Обрабатывает данные, используя методы, помеченные @DataProcessor.
    public void processData() throws InterruptedException {
        System.out.println("Начата обработка данных...");

        // Используем ExecutorService для многопоточной обработки
        ExecutorService executor = Executors.newFixedThreadPool(5);

        for (Object processor : processors) {
            for (Method method : processor.getClass().getDeclaredMethods()) {
                // Проверяем, помечен ли метод аннотацией @DataProcessor
                if (method.isAnnotationPresent(DataProcessor.class)) {
                    System.out.println("Найден метод для обработки: " + method.getName());

                    // Запускаем метод в отдельном потоке
                    executor.submit(() -> {
                        try {
                            System.out.println("Вызов метода " + method.getName() + " из " + processor.getClass().getSimpleName());
                            @SuppressWarnings("unchecked")
                            List<String> result = (List<String>) method.invoke(processor, data); // Вызываем метод
                            synchronized (processedData) {
                                processedData.addAll(result); // Добавляем результат в общий список
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        }

        // Завершаем выполнение потоков
        executor.shutdown();
        executor.awaitTermination(10, TimeUnit.SECONDS); //ожидание завершения

        System.out.println("Обработка завершена. Результаты: " + processedData);
    }

    //Сохраняет обработанные данные в файл.
    //destination Путь к выходному файлу
    public void saveData(String destination) {
        try {
            // Запись обработанных данных в файл
            Files.write(Paths.get(destination), processedData);
            System.out.println("Данные сохранены в " + destination);
        } catch (IOException e) {
            System.out.println("Ошибка при записи файла: " + destination);
            e.printStackTrace();
        }
    }
}