import vk_api
from vk_api.longpoll import VkLongPoll, VkEventType
import keyboards
import API_repository
import datetime
import time

import tokens
token = tokens.my_token
vk_session = vk_api.VkApi(token=token)
vk = vk_session.get_api()

class MyVkLongPoll(VkLongPoll):
    def listen(self):
        while True:
            try:
                for event in self.check():
                    yield event
            except Exception as e:
                print( str(time.time()) + 'Error:', e)

longpoll = MyVkLongPoll(vk_session)

def sender(user_id, answer, keyboard_number):
    vk.messages.send(user_id=user_id, message=answer, keyboard = keyboards.keyboards[keyboard_number], random_id=0)

def sender_without_keyboard(user_id, answer):
    vk.messages.send(user_id = user_id, message = answer, random_id = 0)

def sender_question(user_id, answer, keyboard):
    vk.messages.send(user_id=user_id, message=answer, keyboard = keyboard, random_id=0)

def sender_XOXOL(user_id):
    vk.messages.send(user_id=user_id, message='Для Миииииши', attachment = 'doc163807367_588463638', random_id=0)

def log(event):
    print('\n\nNew message:')
    print('For me by: {}'.format(event.user_id))
    print("Text: " + event.text)


def scheduel_text_S(i, schedule_text):
    schedule_text = schedule_text + '📖 ' + i['subjectName'] + '\n'
    schedule_text = schedule_text + '📄 ' + i['lessonType'] + '\n'
    schedule_text = schedule_text + '⏰ ' + i['lessonStart'] + ' - ' + i['lessonEnd'] + '\n'
    if i['subGroup'] == 0:
        schedule_text = schedule_text + '🗿 ' + 'Обе подргуппы' + '\n'
    elif i['subGroup'] == 1:
        schedule_text = schedule_text + '🗿 ' + '1 подргуппа' + '\n'
    elif i['subGroup'] == 2:
        schedule_text = schedule_text + '🗿 ' + '2 подргуппа' + '\n'
    if i['description'] != None:
        if i['description'].find('/') != -1:
            schedule_text = schedule_text + '1 пг.: ' + i['description'].split('/')[0] + '\n' + '2 пг.: ' + i['description'].split('/')[1] + '\n'
        else:
            schedule_text = schedule_text + 'Обе пг.: ' + i['description']
    schedule_text = schedule_text + '🚪 ' + i['className'] + '\n'
    schedule_text = schedule_text + '🤡 ' + i['prepodName'] + '\n\n'


    return schedule_text

def scheduel_text_P(i, schedule_text):
    schedule_text = schedule_text + '📖 ' + i['subjectName'] + '\n'
    schedule_text = schedule_text + '📄 ' + i['lessonType'] + '\n'
    schedule_text = schedule_text + '⏰ ' + i['lessonStart'] + ' - ' + i['lessonEnd'] + '\n'
    schedule_text = schedule_text + '🤡 ' + i['groupName'] + '\n'
    if i['subGroup'] == 0:
        schedule_text = schedule_text + '🗿 ' + 'Обе подргуппы' + '\n'
    elif i['subGroup'] == 1:
        schedule_text = schedule_text + '🗿 ' + '1 подргуппа' + '\n'
    elif i['subGroup'] == 2:
        schedule_text = schedule_text + '🗿 ' + '2 подргуппа' + '\n'
    if i['description'] != None:
        if i['description'].find('/') != -1:
            schedule_text = schedule_text + '1 пг.: ' + i['description'].split('/')[0] + '\n' + '2 пг.: ' + i['description'].split('/')[1] + '\n'
        else:
            schedule_text = schedule_text + 'Обе пг.: ' + i['description']
    schedule_text = schedule_text + '🚪 ' + i['className'] + '\n'
    return schedule_text

for event in longpoll.listen():
    if event.type == VkEventType.MESSAGE_NEW and event.to_me:
        log(event)

        user_state = API_repository._getUserState(event.user_id)['response']

        if (event.text == 'хохол' or event.text == 'Хохол'):
            sender_XOXOL(event.user_id)

        if event.text == 'Авторизация' and user_state['state'] != 'Finish':
            API_repository._setUserField(event.user_id, 'state', 'RegisterUser')
            sender(event.user_id, 'Кто Вы?', 0)

        elif event.text == 'Студент' and user_state['state'] != 'Finish':
            API_repository._setUserField(event.user_id, 'state', 'RegisterUser_S')
            sender(event.user_id, 'Выберите группу', 1)

        elif event.text == 'Преподаватель' and user_state['state'] != 'Finish':
            API_repository._setUserField(event.user_id, 'state', 'RegisterUser_P')
            sender_without_keyboard(event.user_id, 'Введите, пожалуйста, ваше ФИО в формате "Фамилия И.О."')

        elif event.text == 'Разлогиниться':
            API_repository._setUserField(event.user_id, 'state', 'Start')
            sender(event.user_id, 'Пожалуйста авторизуйтесь', 4)

        elif event.text == 'Сменить подгруппу' and user_state['role'] == 'student' and user_state['state'] == 'Finish':
            API_repository._setUserField(event.user_id, 'state', 'RegisterUser_S_S')
            sender(event.user_id, 'Выберите подгруппу', 3)

        elif event.text == 'Пройти опрос':
            question = API_repository._getQuestion(event.user_id)['response']
            if isinstance(question, dict):
                sender_question(event.user_id, question['question'], keyboards.answer_keyboard(question['answerVariants']))
                API_repository._setUserField(event.user_id, 'state', 'Finish_Q')
            else:
                if user_state['role'] == 'student':
                    sender(event.user_id, 'Для вас нет новых опросов 🙃', 5)
                else:
                    sender(event.user_id, 'Для вас нет новых опросов 🙃', 2)


        elif event.text == 'На сегодня' and user_state['state'] == 'Finish':
            if user_state['role'] == 'student':
                schedule_today = API_repository._getScheduleStudent(user_state['role'], user_state['arg'], 'today', '')['response']
                schedule_text = ''
                if user_state['subGroup'] == 0:
                    schedule_text = schedule_text + '👥 ' + user_state['arg'] + ' Обе подргуппы' + '\n'
                    if schedule_today != []:
                        schedule_text = schedule_text + '📅 ' + schedule_today[0]['weekDayName'].title() + '\n\n'
                        for i in schedule_today:
                            schedule_text = scheduel_text_S(i, schedule_text)
                    else:
                        schedule_text = schedule_text + 'КА-НИ-КУ-ЛЫ, СЕГОДНЯ В ШКОЛУ НЕ ПОЙДУ'

                elif user_state['subGroup'] == 1:
                    schedule_text = schedule_text + '👥 ' + user_state['arg'] + ' 1 подргуппа' + '\n'
                    if schedule_today != []:
                        schedule_text = schedule_text + '📅 ' + schedule_today[0]['weekDayName'].title() + '\n\n'
                        for i in schedule_today:
                            if i['subGroup'] == 1 or i['subGroup'] == 0:
                                schedule_text = scheduel_text_S(i, schedule_text)
                    else:
                        schedule_text = schedule_text + 'КА-НИ-КУ-ЛЫ, СЕГОДНЯ В ШКОЛУ НЕ ПОЙДУ'

                elif user_state['subGroup'] == 2:
                    schedule_text = schedule_text + '👥 ' + user_state['arg'] + ' 2 подргуппа' + '\n'
                    if schedule_today != []:
                        schedule_text = schedule_text + '📅 ' + schedule_today[0]['weekDayName'].title() + '\n\n'
                        for i in schedule_today:
                            if i['subGroup'] == 2 or i['subGroup'] == 0:
                                schedule_text = scheduel_text_S(i, schedule_text)
                    else:
                        schedule_text = schedule_text + 'КА-НИ-КУ-ЛЫ, СЕГОДНЯ В ШКОЛУ НЕ ПОЙДУ'
                sender(event.user_id, schedule_text, 5)

            elif user_state['role'] == 'prepod':
                schedule_today = API_repository._getSchedulePrepod(user_state['role'], user_state['arg'], 'today', '')['response']
                schedule_text = ''
                schedule_text = schedule_text + '👥 ' + user_state['arg'] + '\n'
                if schedule_today != []:
                    schedule_text = schedule_text + '📅 ' + schedule_today[0]['weekDayName'].title() + '\n\n'
                    for i in schedule_today:
                        schedule_text = scheduel_text_P(i, schedule_text)
                else:
                    schedule_text = schedule_text + 'ОТДОХНИ, ТЫ ЗАСЛУЖИЛ'
                sender(event.user_id, schedule_text, 2)

        elif event.text == 'На завтра' and user_state['state'] == 'Finish':
            if user_state['role'] == 'student':
                schedule_tomorrow = API_repository._getScheduleStudent(user_state['role'], user_state['arg'], 'tomorrow', '')['response']
                schedule_text = ''
                if user_state['subGroup'] == 0:
                    schedule_text = schedule_text + '👥 ' + user_state['arg'] + ' Обе подргуппы' + '\n'
                    if schedule_tomorrow != []:
                        schedule_text = schedule_text + '📅 ' + schedule_tomorrow[0]['weekDayName'].title() + '\n\n'
                        for i in schedule_tomorrow:
                            schedule_text = scheduel_text_S(i, schedule_text)
                    else:
                        schedule_text = schedule_text + 'КА-НИ-КУ-ЛЫ, ЗАВТРА В ШКОЛУ НЕ ПОЙДУ'

                elif user_state['subGroup'] == 1:
                    schedule_text = schedule_text + '👥 ' + user_state['arg'] + ' 1 подргуппа' + '\n'
                    if schedule_tomorrow != []:
                        schedule_text = schedule_text + '📅 ' + schedule_tomorrow[0]['weekDayName'].title() + '\n\n'
                        for i in schedule_tomorrow:
                            if i['subGroup'] == 1 or i['subGroup'] == 0:
                                schedule_text = scheduel_text_S(i, schedule_text)
                    else:
                        schedule_text = schedule_text + 'КА-НИ-КУ-ЛЫ, ЗАВТРА В ШКОЛУ НЕ ПОЙДУ'

                elif user_state['subGroup'] == 2:
                    schedule_text = schedule_text + '👥 ' + user_state['arg'] + ' 2 подргуппа' + '\n'
                    if schedule_tomorrow != []:
                        schedule_text = schedule_text + '📅 ' + schedule_tomorrow[0]['weekDayName'].title() + '\n\n'
                        for i in schedule_tomorrow:
                            if i['subGroup'] == 2 or i['subGroup'] == 0:
                                schedule_text = scheduel_text_S(i, schedule_text)
                    else:
                        schedule_text = schedule_text + 'КА-НИ-КУ-ЛЫ, ЗАВТРА В ШКОЛУ НЕ ПОЙДУ'
                sender(event.user_id, schedule_text, 5)

            elif user_state['role'] == 'prepod':
                schedule_tomorrow = API_repository._getSchedulePrepod(user_state['role'], user_state['arg'], 'tomorrow', '')['response']
                schedule_text = ''
                schedule_text = schedule_text + '👥 ' + user_state['arg'] + '\n'
                if schedule_tomorrow != []:
                    schedule_text = schedule_text + '📅 ' + schedule_tomorrow[0]['weekDayName'].title() + '\n\n'
                    for i in schedule_tomorrow:
                        schedule_text = scheduel_text_P(i, schedule_text)
                else:
                    schedule_text = schedule_text + 'ОТДОХНИ, ТЫ ЗАСЛУЖИЛ'
                sender(event.user_id, schedule_text, 2)

        elif event.text == 'По дате' and user_state['state'] == 'Finish':
            if user_state['role'] == 'student':
                API_repository._setUserField(event.user_id, 'state', 'Finish_date_S')
                sender_without_keyboard(event.user_id, 'Введите дату в формате "дд.мм.гггг"')

            elif user_state['role'] == 'prepod':
                API_repository._setUserField(event.user_id, 'state', 'Finish_date_P')
                sender_without_keyboard(event.user_id, 'Введите дату в формате "дд.мм.гггг"')


        #Ввел что-то не из перечня
        else:
            if user_state['state'] == 'None':
                API_repository._setUserField(event.user_id, 'state', 'Start')
                sender(event.user_id, 'Пожалуйтса авторизуйтесь', 4)

            elif user_state['state'] == 'Start':
                sender(event.user_id, 'КНОПОЧКА ЖЕ ЕСТЬ', 4)

            elif user_state['state'] == 'RegisterUser':
                sender(event.user_id, 'КНОПОЧКА ЖЕ ЕСТЬ', 0)

            elif user_state['state'] == 'Finish':
                if user_state['role'] == 'student':
                    if user_state['subGroup'] == 0:
                        sender(event.user_id, 'Вы уже авторизованы как студент группы ' + user_state['arg'] + ' Обе подгруппы\n\nВыберите расписание', 5)
                    elif user_state['subGroup'] == 1:
                        sender(event.user_id, 'Вы уже авторизованы как студент группы ' + user_state['arg'] + ' 1 подгруппа\n\nВыберите расписание', 5)
                    elif user_state['subGroup'] == 2:
                        sender(event.user_id, 'Вы уже авторизованы как студент группы ' + user_state['arg'] + ' 2 подгруппа\n\nВыберите расписание', 5)
                else:
                    sender(event.user_id, 'Вы уже авторизованы как преподаватель ' + user_state['arg'], 2)

            elif user_state['state'] == 'RegisterUser_S':
                if API_repository._checkGroup(event.text.upper())['status'] == 'OK':
                    API_repository._setUserField(event.user_id, 'arg', event.text)
                    API_repository._setUserField(event.user_id, 'state', 'RegisterUser_S_S')
                    sender(event.user_id, 'Выберите подгруппу', 3)
                else:
                    API_repository._setUserField(event.user_id, 'state', 'Start')
                    sender(event.user_id, 'Пожалуйтса авторизуйтесь', 4)

            elif user_state['state'] == 'RegisterUser_S_S':
                if event.text == '1 подгруппа':
                    API_repository._registerUser(event.user_id, 'student', None, 1)
                    sender(event.user_id, 'Вы успешно авторизовались!\n\nВыберите расписание', 5)
                    API_repository._setUserField(event.user_id, 'state', 'Finish')
                elif event.text == '2 подгруппа':
                    API_repository._registerUser(event.user_id, 'student', None, 2)
                    sender(event.user_id, 'Вы успешно авторизовались!\n\nВыберите расписание', 5)
                    API_repository._setUserField(event.user_id, 'state', 'Finish')
                elif event.text == 'Обе подгруппы':
                    API_repository._registerUser(event.user_id, 'student', None, 0)
                    sender(event.user_id, 'Вы успешно авторизовались!\n\nВыберите расписание', 5)
                    API_repository._setUserField(event.user_id, 'state', 'Finish')
                else:
                    sender(event.user_id, 'КНОПОЧКА ЖЕ ЕСТЬ', 3)

            elif user_state['state'] == 'RegisterUser_P':
                if API_repository._checkFio(event.text)['status'] == 'OK':
                    API_repository._registerUser(event.user_id, 'prepod', event.text, None)
                    sender(event.user_id, 'Вы успешно авторизовались!\n\nВыберите расписание', 2)
                    API_repository._setUserField(event.user_id, 'state', 'Finish')
                else:
                    API_repository._setUserField(event.user_id, 'state', 'Start')
                    sender(event.user_id, 'Пожалуйтса авторизуйтесь', 4)

            elif user_state['state'] == 'Finish_date_S':
                try:
                    datetime.datetime.strptime(event.text, '%d.%m.%Y')
                    schedule_custom = API_repository._getScheduleStudent(user_state['role'], user_state['arg'], 'custom', event.text)['response']
                    schedule_text = ''
                    if user_state['subGroup'] == 0:
                        schedule_text = schedule_text + '👥 ' + user_state['arg'] + ' Обе подргуппы' + '\n'
                        if schedule_custom != []:
                            schedule_text = schedule_text + '📅 ' + schedule_custom[0]['weekDayName'].title() + '\n\n'
                            for i in schedule_custom:
                                schedule_text = scheduel_text_S(i, schedule_text)
                        else:
                            schedule_text = schedule_text + 'КА-НИ-КУ-ЛЫ, А Я В ШКОЛУ НЕ ПОЙДУ'

                    elif user_state['subGroup'] == 1:
                        schedule_text = schedule_text + '👥 ' + user_state['arg'] + ' 1 подргуппа' + '\n'
                        if schedule_custom != []:
                            schedule_text = schedule_text + '📅 ' + schedule_custom[0]['weekDayName'].title() + '\n\n'
                            for i in schedule_custom:
                                if i['subGroup'] == 1 or i['subGroup'] == 0:
                                    schedule_text = scheduel_text_S(i, schedule_text)
                        else:
                            schedule_text = schedule_text + 'КА-НИ-КУ-ЛЫ, А Я В ШКОЛУ НЕ ПОЙДУ'

                    elif user_state['subGroup'] == 2:
                        schedule_text = schedule_text + '👥 ' + user_state['arg'] + ' 2 подргуппа' + '\n'
                        if schedule_custom != []:
                            schedule_text = schedule_text + '📅 ' + schedule_custom[0]['weekDayName'].title() + '\n\n'
                            for i in schedule_custom:
                                if i['subGroup'] == 2 or i['subGroup'] == 0:
                                    schedule_text = scheduel_text_S(i, schedule_text)
                        else:
                            schedule_text = schedule_text + 'КА-НИ-КУ-ЛЫ, А Я В ШКОЛУ НЕ ПОЙДУ'
                    sender(event.user_id, schedule_text, 5)
                    API_repository._setUserField(event.user_id, 'state', 'Finish')

                except ValueError:
                    sender(event.user_id, 'Неверный формат', 5)
                    API_repository._setUserField(event.user_id, 'state', 'Finish')

            elif user_state['state'] == 'Finish_date_P':
                try:
                    datetime.datetime.strptime(event.text, '%d.%m.%Y')
                    schedule_custom = API_repository._getScheduleStudent(user_state['role'], user_state['arg'], 'custom', event.text)['response']
                    schedule_text = ''
                    schedule_text = schedule_text + '👥 ' + user_state['arg'] + '\n'
                    if schedule_custom != []:
                        schedule_text = schedule_text + '📅 ' + schedule_custom[0]['weekDayName'].title() + '\n\n'
                        for i in schedule_custom:
                            schedule_text = scheduel_text_P(i, schedule_text)
                    else:
                        schedule_text = schedule_text + 'ОТДОХНИ, ТЫ ЗАСЛУЖИЛ'
                    sender(event.user_id, schedule_text, 2)
                    API_repository._setUserField(event.user_id, 'state', 'Finish')

                except ValueError:
                    sender(event.user_id, 'Неверный формат', 2)
                    API_repository._setUserField(event.user_id, 'state', 'Finish')

            elif user_state['state'] == 'Finish_Q':
                if API_repository._answerQuestion(event.user_id, event.text)['status'] != 'FAIL':
                    if user_state['role'] == 'student':
                        sender(event.user_id, 'Спасибо за ответ 🙂', 5)
                    else:
                        sender(event.user_id, 'Спасибо за ответ 🙂', 2)
                else:
                    sender_without_keyboard(event.user_id, 'КНОПОЧКИ ЖЕ ЕСТЬ')
                API_repository._setUserField(event.user_id, 'state', 'Finish')