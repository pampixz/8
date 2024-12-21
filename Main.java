public class Main {
    public static void main(String[] args) throws InterruptedException {
        DataManager dataManager = new DataManager(); //управление регистрацией обработчиков

        // Регистрация обработчиков
        System.out.println("Регистрация обработчиков...");
        dataManager.registerDataProcessor(new DataFilter()); //для фильтрации
        dataManager.registerDataProcessor(new DataTransformer()); //для преобразования
        dataManager.registerDataProcessor(new DataAggregator()); //для агрегации

        // Загрузка данных
        System.out.println("\nЗагрузка данных...");
        dataManager.loadData("input.txt");

        // Обработка данных
        System.out.println("\nОбработка данных...");
        dataManager.processData(); //ищет методы с аннотацией

        // Сохранение данных
        System.out.println("\nСохранение данных...");
        dataManager.saveData("output.txt");
    }
}