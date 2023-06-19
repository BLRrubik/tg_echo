# Эхо-бот для сбора статистики по сообщениям от пользователей

### Задача
Реализовать эхо телеграм-бота с веб интерфейсом. 
Веб интерфейс представляет из себя простое api для управления настройками 
и получения статистики из работающего телеграм бота.

### Проблемы
В ходе выполнения данного задания возникли несколько спорных 
моментов. 

Изначально я начал писать с использованием Postgres и Redis, 
однако я увидел, что предпочтительнее in-memory хранилище. 
Однако при перезапуске приложения все данные стираются и 
статистика теряется. Поэтому мною было принято решение, без
испоользования сторонних баз данных, 
реализовать state приложения. При завершении 
приложения я сохраняю информацию по пользователю и задержке
в json, а при старте считываю. Пример [json](state/state.json).


Другая проблема была с пониманием задания. Возник вопрос: 
"Если пользователь с этим ботом существует еще в одном чате?".
Данный вопрос я решил так, раз бот пишется для сбора статистики, 
то пусть бот считает все сообщения отправленные конкретным
пользователем вне зависимости от того, в каком чате он находится.


Также я видел два варианта архитектурного решения. Либо это будет
монолит, т.е. и бот и веб в одном проекте, либо это будет 
несколько модулей. Я решил, что для данного проекта подходит
монолитная структура, чтобы не делать множества запросов.


### Запуск

Я решил выбрать [docker-compose](docker-compose.yml). 

Все переменные уже прописаны в этом файле и подгружается в 
application.yml.

Можно изменить:
* токен бота
* имя бота 
* порт сервера
* задержку по дефолту

Запуск производиться следующим образом:
```
    docker-compsoe up
```


### Работа
При старте приложения бот будет ожидать от пользователя 
ввод команды /start. В любом другом случае пользователю будет
отправляться сообщение с тем, что для начала нужно отправить
стратовую команду.

Для удобства я реализовал reply на сообщение пользователя, 
чтобы было видно кому и что отправил бот. 

Данного бота я тестировал как в личных сообщениях, так и 
добавлял его в групповые чаты.

Считаю, что задание было выполнено успешно. 

### API 

API из себя представляет два метода: 
* POST: /delay/update

Тело запроса: 
```
    {
	“delay” : Long (время задержки в мс)
    }
```

* GET: /message/last/{userId}

Переменная пути: 
- userId - id пользователя (я использовал UUID)

Тело ответа:
```
    {
	“username”: String (Тег пользователя из Телеграма)
	"text" : String (Текст сообщения)
    }
```


#### ВАЖНО 

Для просмотра api я подключил swagger. Для доступа к нему 
необходимо перейти по ссылке: http://<ip>:<port>/swagger-ui/index.html

