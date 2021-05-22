package data.resources;

import java.util.ListResourceBundle;

import static data.Resources.MAX_Y;

public class data_ua extends ListResourceBundle {
    private static final Object[][] content = {
            // Ticket Fields
            {"language", "Український"},
            {"ticket", "Квиток"},
            {"coordinates", "Координати"},
            {"name", "Ім'я"},
            {"creationDate", "Дата створення"},
            {"price", "Ціна"},
            {"discount", "Знижка"},
            {"comment", "Коментар"},
            {"type", "Тип"},
            {"eventId", "id події"},
            {"eventName", "Ім'я Події"},
            {"ticketCount", "Кількість Квитків"},
            {"event", "Подія"},
            {"minAge", "Мінімальний вік"},
            //Labels and some buttons
            {"back", "Назад"},
            {"table", "Таблиця"},
            {"exit", "Вихід"},
            {"enter", "Ввівши"},
            {"logInLabel", "Входу"},
            {"login", "Логін"},
            {"password", "Пароль"},
            {"logInButton", "Увійти"},
            {"registration", "Реєстрація"},
            {"register", "Зареєструвавши"},
            {"tickets", "Квиток"},
            {"updateTheCell", "Оновити вибрану комірку"},
            {"sort", "Сортувати"},
            {"filter", "Фільтрувати"},
            {"showAllTickets", "Показати всі квитки"},
            //Commands
            {"add", "Додавши"},
            {"addIfMax", "Додати якщо більше"},
            {"addIfMin", "Додати якщо менше"},
            {"maxByComment", "Максимальний за коментарем"},
            {"printUniquePrices", "Вивести унікальні ціни"},
            {"help", "Допомога"},
            {"removeById", "Видалити по id"},
            {"clear", "Очистивши"},
            {"info", "Інформація"},
            {"sumOfDiscount", "Сума по знижках"},
            {"removeGreater", "Видалити більше, ніж"},
            {"executeScript", "Виконати скрипт"},
            {"update", "Оновити"},
            {"visualize", "Візуалізація"},
            //server answers
            {"Ticket added", "Квиток доданий"},
            {"Element isn't maximal", "Елемент не максимальний"},
            {"Element isn't minimal", "Елемент не мінімальний"},
            {"Your tickets deleted", "Ваші квитки видалені"},
            {"Collection is empty", "Колекція порожня"},
            {"User sign up", "Користувач зареєстрований"},
            {"Ticket with this id not found", "Квиток з таким id не знайдено"},
            {"It's not your ticket", "Це не ваш квиток"},
            {"Ticket deleted", "Квиток видалено"},
            {"Incorrect id", "Невірний id"},
            {"Ticket updated", "Квиток оновлено"},
            {"You haven't rights to change this ticket", "У вас немає прав, щоб змінити цей квиток"},
            {"You authorization", "Ви авторизовані"},
            {"User not found", "Користувач не знайдений"},
            {"Your data is too long", "Введений дані занадто великі"},
            {"User with this login already exists", "Користувач з таким логіном вже існує"},
            {"Database crashed, exit", "База даних зламалася"},

            {"Script executed", "Сценарій виконаний"},
            {"Problems with the file which you enter", "Проблеми з файлом, який ви вводите"},
            {"Error! Recursive in script", "Помилка Рекурсивний сценарій"},
            {"Incorrect id", "Неправильний id"},
            {"Server is tired. Try to reconnect later", "Сервер втомився. Спробуйте пізніше підключитися"},
            {"Price: Price must be more then 0", "Ціна: Ціна повинна бути більше 0"},
            {"Name: Incorrect name", "Ім'я: Неправильна назва"},
            {"X: Please enter a double argument", "X: Будь ласка, введіть подвійний аргумент"},
            {"Y: Please enter a float argument", "Y: Будь ласка, введіть аргумент з плаваючою точкою"},
            {"Y: y-coordinate must be less than " + MAX_Y, "Y: координата y повинна бути менше " + MAX_Y},
            {"Discount: Discount can be from 0 to 100", "Знижка: Знижка може бути від 0 до 100"},
            {"Comment: Incorrect comment", "Коментар: Неправильний коментар"},
            {"Price: Please enter a float argument", "Ціна: Будь ласка, введіть аргумент з плаваючою позицією"},
            {"Discount: Please enter a long argument", "Знижка: Будь ласка, введіть довгий аргумент"},
            {"Minimal Age: Please enter an integer argument", "Мінімальний вік: Будь ласка, введіть цілочисельний аргумент"},
            {"Tickets Count: Please enter a long argument", "Кількість квитків: Будь ласка, введіть довгий аргумент"},
            {"Tickets Count: Tickets count must be more than 0", "Кількість квитків: Кількість квитків повинна бути більше 0"},
            {"Discount: Please enter a long argument", "Знижка: Будь ласка, введіть довгий аргумент"},
            {"Event Name: Incorrect name", "Назва події: неправильна назва"},
            {"Login can't be empty", "Вхід не може бути порожнім"},
            {"Please select field", "Виберіть поле"}
    };

    @Override
    protected Object[][] getContents() {
        return content;
    }
}
