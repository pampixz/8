import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Аннотация для пометки методов обработки данных.
//Методы, помеченные этой аннотацией, будут автоматически обнаруживаться
//и вызываться во время обработки данных в классе DataManager.
@Retention(RetentionPolicy.RUNTIME) // Указывает, что аннотация будет доступна во время выполнения
@Target(ElementType.METHOD) // Указывает, что аннотация применяется только к методам
public @interface DataProcessor {
}