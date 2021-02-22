# StudentHelper
## Команда
1. Алейников Андрей
2. Сатилин Владислав
3. Ромашов Никита

## Предметная область
Студенческий процесс

## Точка зрения
Точка зрения - студенты и преподаватели.

## Описание предметной области
Программа для просмотра расписания занятий для студентов и преподавателей, проставление оценок преподавателями студентам за занятия.

## Функциональные требования
- Преподаватели
  - Чат-бот
    - Авторизация по фио
    - Просмотр расписание на текущий день / на 2 недели
    - Напоминание о предстоящих занятиях в текущий день
	- Социологические опросы
  - Мобильное приложение
    - Авторизация в качестве преподавателя
    - Отмечать присутствие  студентов на занятии
    - Давать студентам баллы за занятие
    - Просматривать итоговый балл за предмет для студента, генерация ведомости в xls
    - Просматривать расписание на текущий день / на 2 недели
    - Напоминание о предстоящих занятиях в текущий день

- Студенты
  - Чат-бот
    - Авторизация (группа)
    - Просматривать расписание на текущий день / на 2 недели
    - Напоминание о предстоящих занятиях в текущий день
    - Помощь в чат боте по различным вопросам (как вступить в профсоюз, как оформить социальную стипендию)
	- Социологические опросы
  - Мобильное приложение
    - Авторизация студента
    - Просматривать оценки за предметы / занятия
    - Просматривать расписание на текущий день / на 2 недели
    - Напоминание о предстоящих занятиях в текущий день
## Описание хранимых данных в БД

- Основная БД
  - СУБД - PostgreSQL
  - Предназначение - хранение данных о преподавателях, студентах, факультетах, кафедрах, группах и расписание занятий.
  
	![DB1Schema](https://github.com/mloner/StudentHelper/blob/main/images/DB1Schema.png)
	
- Вторая БД
  - СУБД - MS SQL
  - Предназначение - хранение данных чат-бота (данные о пользователях, опросах, ответах на опросы).
  
	![DB2Schema](https://github.com/mloner/StudentHelper/blob/main/images/DB2Schema.png)
	
- Третья БД
  - СУБД - MongoDB
  <!---!- Предназначение - 
	[DB2Schema](https://github.com/mloner/StudentHelper/blob/main/images/DB2Schema.png) --->
	
## Перечень основных операций для баз данных

- Основная БД
  
	
- Основная БД

- Основная БД
  

	