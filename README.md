# CocktailBar
OTUS - final project work. Приложение с рецептами коктелей.

Приложение получает данные с сервера, сохраняет в SQLite и выводит данные на экран в RecyclerView. Приложение состоит из 5 экранов:

1) Экран главного меню - кнопки для выбора типа поиска коктеля
2) Экран выбора ингредиента (выбор напитка на основании которого происходит поиск)
3) Экран со списком найденных коктелей
4) Экран с подробным описанием коктеля
5) Экран “О приложении”

Приложение написано на Kotlin. Retrofit для запросов к серверу. Room для кэширования. Picasso для работы с изображениями. Dagger2 для DI. 
Kotlin coroutines.
