package data.resources;

import java.util.ListResourceBundle;

import static data.Resources.MAX_Y;

public class data_es extends ListResourceBundle {

    private static final Object[][] content = {
            // Ticket Fields
            {"language", "Español"},
            {"ticket", "Billete"},
            {"name", "Nombre"},
            {"coordinates", "coordenadas"},
            {"creationDate", "Fecha de creación"},
            {"price", "Precio"},
            {"discount", "descuento"},
            {"comment", "Comentario"},
            {"type", "tipo"},
            {"eventId", "ID de evento"},
            {"eventName", "nombre del evento"},
            {"ticketCount", "número de entradas"},
            {"event", "evento"},
            {"minAge", "edad mínima"},
            //Labels and some buttons
            {"back", "espalda"},
            {"table", "mesa"},
            {"exit", "Salida"},
            {"enter", "presentar"},
            {"logInLabel", "Entrada"},
            {"login", "Acceso"},
            {"password", "Contraseña"},
            {"logInButton", "Entrar"},
            {"registration", "Registrarse"},
            {"register", "Registrarse"},
            {"tickets", "Entradas"},
            {"updateTheCell", "Actualizar celda seleccionada"},
            {"sort", "Clasificar"},
            {"filter", "Filtrar"},
            {"showAllTickets", "Mostrar todas las entradas"},
            //Commands
            {"add", "Agregar"},
            {"addIfMax", "Agregar si más"},
            {"addIfMin", "Agregar si es menos"},
            {"maxByComment", "Máximo por comentario"},
            {"printUniquePrices", "Imprimir precios únicos"},
            {"help", "Ayudar"},
            {"removeById", "Eliminar por id"},
            {"clear", "Claro"},
            {"info", "Información"},
            {"sumOfDiscount", "Importe de descuento"},
            {"removeGreater", "Quitar más de"},
            {"executeScript", "Ejecutar secuencia de comandos"},
            {"update", "Actualizar"},
            {"visualize", "Visualización"},
            //server answers
            {"Ticket added", "Ticket agregado"},
            {"Element isn't maximal", "El elemento no es máximo"},
            {"Element isn't minimal", "El elemento no es mínimo"},
            {"Your tickets deleted", "Tus entradas han sido eliminadas"},
            {"Collection is empty", "La coleccion esta vacia"},
            {"User sign up", "El usuario está registrado"},
            {"Ticket with this id not found", "No se encontró ningún boleto con esta identificación"},
            {"It's not your ticket", "Este no es tu boleto"},
            {"Ticket deleted", "Ticket eliminado"},
            {"Incorrect id", "Identificación invalida"},
            {"Ticket updated", "Ticket actualizado"},
            {"You haven't rights to change this ticket", "No estás autorizado a cambiar este ticket"},
            {"You authorization", "Estás conectado"},
            {"User not found", "No se encuentra el usuario"},
            {"Your data is too long", "Los datos ingresados ​​son demasiado grandes"},
            {"User with this login already exists", "El usuario con este inicio de sesión ya existe"},
            {"Database crashed, exit", "La base de datos está rota"},

            {"Script executed", "Script ejecutado"},
            {"Problems with the file which you enter", "Problemas con el archivo que ingresa"},
            {"Error! Recursive in script", "¡Error! Recursivo en guión"},
            {"Incorrect id", "incorrecta id"},
            {"Server is tired. Try to reconnect later", "El servidor está cansado. Intenta volver a conectarte más tarde"},
            {"Price: Price must be more then 0", "Precio: el precio debe ser superior a 0"},
            {"Name: Incorrect name", "Nombre: nombre incorrecto"},
            {"X: Please enter a double argument", "X: ingrese un argumento doble"},
            {"Y: Please enter a float argument", "Y: ingrese un argumento flotante"},
            {"Y: y-coordinate must be less than " + MAX_Y, "Y: la coordenada y debe ser menor que " + MAX_Y},
            {"Discount: Discount can be from 0 to 100", "Descuento: el descuento puede ser de 0 a 100"},
            {"Comment: Incorrect comment", "Comentario: comentario incorrecto"},
            {"Price: Please enter a float argument", "Precio: ingrese un argumento flotante"},
            {"Discount: Please enter a long argument", "Descuento: ingrese un argumento largo"},
            {"Minimal Age: Please enter an integer argument", "Edad mínima: ingrese un argumento entero"},
            {"Tickets Count: Please enter a long argument", "Cantidad de boletos: ingrese un argumento largo"},
            {"Tickets Count: Tickets count must be more than 0", "Descuento: por favor recuento de boletos: el recuento de boletos debe ser más de 0 un argumento largo"},
            {"Discount: Please enter a long argument", "Descuento: ingrese un argumento largo"},
            {"Event Name: Incorrect name", "Nombre del evento: nombre incorrecto"},
            {"Login can't be empty", "El inicio de sesión no puede estar vacío"},
            {"Please select field", "Por favor seleccione el campo"}
    };

    @Override
    protected Object[][] getContents() {
        return content;
    }
}
