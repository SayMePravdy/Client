package data.resources;

import java.util.ListResourceBundle;

import static data.Resources.MAX_Y;

public class data_ru extends ListResourceBundle {

    private static final Object[][] content = {
            {"language", "Русский"},
            {"ticket", "Билет"},
            {"name", "Имя"},
            {"coordinates", "Координаты"},
            {"creationDate", "Дата Создания"},
            {"price", "Цена"},
            {"discount", "Скидка"},
            {"comment", "Комментарий"},
            {"type", "Тип"},
            {"eventId", "id События"},
            {"eventName", "Имя События"},
            {"ticketCount", "Количество Билетов"},
            {"event", "Событие"},
            {"minAge", "Минимальный возраст"},
            //Labels and some buttons
            {"back", "Назад"},
            {"table", "Таблица"},
            {"exit", "Выход"},
            {"enter", "Ввести"},
            {"logInLabel", "Вход"},
            {"login", "Логин"},
            {"password", "Пароль"},
            {"logInButton", "Войти"},
            {"registration", "Регистрация"},
            {"register", "Зарегистрировать"},
            {"tickets", "Билеты"},
            {"updateTheCell", "Обновить выбранную ячейку"},
            {"sort", "Сортировать"},
            {"filter", "Фильтровать"},
            {"showAllTickets", "Показать все билеты"},
            //Commands
            {"add", "Добавить"},
            {"addIfMax", "Добавить если больше"},
            {"addIfMin", "Добавить если меньше"},
            {"maxByComment", "Максимальный по комментарию"},
            {"printUniquePrices", "Вывести уникльные цены"},
            {"help", "Помощь"},
            {"removeById", "Удалить по id"},
            {"clear", "Очистить"},
            {"info", "Информация"},
            {"sumOfDiscount", "Сумма по скидкам"},
            {"removeGreater", "Удалить больше, чем"},
            {"executeScript", "Исполнить скрипт"},
            {"update", "Обновить"},
            {"visualize", "Визуализация"},
            //server answers
            {"Ticket added", "Билет добавлен"},
            {"Element isn't maximal", "Элемент не максимальный"},
            {"Element isn't minimal", "Элемент не минимальный"},
            {"Your tickets deleted", "Ваши билеты удалены"},
            {"Collection is empty", "Коллекция пустая"},
            {"User sign up", "Пользователь зарегистрирован"},
            {"Ticket with this id not found", "Билет с таким id не найден"},
            {"It's not your ticket", "Это не ваш билет"},
            {"Ticket deleted", "Билет удален"},
            {"Incorrect id", "Неверный id"},
            {"Ticket updated", "Билет обновлен"},
            {"You haven't rights to change this ticket", "У вас нет прав, чтобы изменить этот билет"},
            {"You authorization", "Вы авторизованы"},
            {"User not found", "Пользователь не найден"},
            {"Your data is too long", "Введеный данные слишком большие"},
            {"User with this login already exists", "Пользователь с таким логином уже существует"},
            {"Database crashed, exit", "База данных сломалась"},
            //client message
            {"Script executed", "Скрипт исполнен"},
            {"Problems with the file which you enter", "Проблемы с введенным файлом"},
            {"Error! Recursive in script", "Ошибка! Рекурсия в скрипте"},
            {"Incorrect id", "Неверный id"},
            {"Server is tired. Try to reconnect later", "Сервер устал. Попробуйте переподключиться позднее"},
            {"Price: Price must be more then 0", "Цена: Цена должна быть больше 0"},
            {"Name: Incorrect name", "Имя: Некорректное имя"},
            {"X: Please enter a double argument", "X: Введите число"},
            {"Y: Please enter a float argument", "У: Введите число"},
            {"Y: y-coordinate must be less than " + MAX_Y, "Y: y-координата должна быть меньше 266"},
            {"Discount: Discount can be from 0 to 100", "Скидка: Скидка должна быть от 0 до 100"},
            {"Comment: Incorrect comment", "Комментарий: Некорректный комментарий"},
            {"Price: Please enter a float argument", "Цена: Введите число"},
            {"Discount: Please enter a long argument", "Скидка: Введите целое число"},
            {"Minimal Age: Please enter an integer argument", "Минимальный возраст: Введите целое число"},
            {"Tickets Count: Please enter a long argument", "Количество билетов: Введите целое число"},
            {"Tickets Count: Tickets count must be more than 0", "Количество билетов: Количество билетов должно быть больше 0"},
            {"Discount: Please enter a long argument", "Скидка: Введите число"},
            {"Event Name: Incorrect name", "Имя События: Некорректное имя"},
            {"Login can't be empty", "Логин не может быть пустым"},
            {"Please select field", "Выберите поле"}
    };

    @Override
    protected Object[][] getContents() {
        return content;
    }
}
