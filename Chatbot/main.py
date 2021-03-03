import vk_api
from vk_api.longpoll import VkLongPoll, VkEventType
import keyboards
import API_repository



import tokens
token = tokens.my_token
vk = vk_api.VkApi(token=token)
longpoll = VkLongPoll(vk)

def sender(user_id, answer, keyboard_number):
    vk.method('messages.send', {
                                'user_id': user_id,
                                'message': answer,
                                'random_id': 0,
                                'keyboard': keyboards.keyboards[keyboard_number]
                               }
             )

def sender_without_keyboard(user_id, answer):
    vk.method('messages.send', {
                                'user_id': user_id,
                                'message': answer,
                                'random_id': 0,
                               }
             )

def log(event):
    print('\n\nNew message:')
    print('For me by: {}'.format(event.user_id))
    print("Text: " + event.text)


for event in longpoll.listen():
    if event.type == VkEventType.MESSAGE_NEW and event.to_me:
        log(event)

        user_state = API_repository._getUserState(event.user_id)['response']

        if event.text == 'Авторизация' and user_state['state'] != 'Finish':
            API_repository._setUserState(event.user_id, 'RegisterUser')
            sender(event.user_id, 'Кто Вы?', 0)

        elif event.text == 'Студент' and user_state['state'] != 'Finish':
            API_repository._setUserState(event.user_id, 'RegisterUser_S')
            sender(event.user_id, 'Выберите группу', 1)

        elif event.text == 'Преподаватель' and user_state['state'] != 'Finish':
            API_repository._setUserState(event.user_id, 'RegisterUser_P')
            sender_without_keyboard(event.user_id, 'Введите, пожалуйста, ваше ФИО в формате "Фамилия И.О."')

        elif event.text == 'Разлогиниться':
            API_repository._setUserState(event.user_id, 'Start')
            sender(event.user_id, 'Пожалуйтса авторизуйтесь', 4)

        elif event.text == 'На сегодня' and user_state['state'] == 'Finish':
            if user_state['role'] == 'student':
                schedule_today = API_repository._getScheduleStudent(user_state['role'], user_state['arg'], 'today')['response']

                schedule_text = '👥 ' + user_state['arg'] + '\n\n'
                for i in schedule_today:
                    schedule_text = schedule_text + '📖 ' + i['subjectName'] + '\n'
                    schedule_text = schedule_text + '📄 ' + i['lessonType'] + '\n'
                    schedule_text = schedule_text + '⏰ ' + i['lessonStart'] + ' - ' + i['lessonEnd'] + '\n'
                    if i['subGroup'] == 0:
                        schedule_text = schedule_text + '🗿 ' + 'Обе подргуппы' + '\n'
                    elif i['subGroup'] == 1:
                        schedule_text = schedule_text + '🗿 ' + '1 подргуппа' + '\n'
                    elif i['subGroup'] == 2:
                        schedule_text = schedule_text + '🗿 ' + '2 подргуппа' + '\n'
                    schedule_text = schedule_text + '🚪 ' + i['className'] + '\n'
                    schedule_text = schedule_text + '🤡 ' + i['prepodName'] + '\n\n'

                sender(event.user_id, schedule_text, 2)


            elif user_state['role'] == 'prepod':
                schedule_today = API_repository._getSchedulePrepod(user_state['role'], user_state['arg'], 'today')

        elif event.text == 'На завтра' and user_state['state'] == 'Finish':
            if user_state['role'] == 'student':
                schedule_tomorrow = API_repository._getScheduleStudent(user_state['role'], user_state['arg'], 'tomorrow')['response']

                schedule_text = '👥 ' + user_state['arg'] + '\n\n'
                for i in schedule_tomorrow:
                    schedule_text = schedule_text + '📖 ' + i['subjectName'] + '\n'
                    schedule_text = schedule_text + '📄 ' + i['lessonType'] + '\n'
                    schedule_text = schedule_text + '⏰ ' + i['lessonStart'] + ' - ' + i['lessonEnd'] + '\n'
                    if i['subGroup'] == 0:
                        schedule_text = schedule_text + '🗿 ' + 'Обе подргуппы' + '\n'
                    elif i['subGroup'] == 1:
                        schedule_text = schedule_text + '🗿 ' + '1 подргуппа' + '\n'
                    elif i['subGroup'] == 2:
                        schedule_text = schedule_text + '🗿 ' + '2 подргуппа' + '\n'
                    schedule_text = schedule_text + '🚪 ' + i['className'] + '\n'
                    schedule_text = schedule_text + '🤡 ' + i['prepodName'] + '\n\n'

                sender(event.user_id, schedule_text, 2)
                # schedule_text = '👥 ' + user_state['arg'] + '\n\n'
                # for i in schedule_tomorrow:
                #     schedule_text = schedule_text + '📖 ' + i['lessonType'] + ' '
                #     schedule_text = schedule_text + i['subjectName'] + '\n'
                #     schedule_text = schedule_text + '⏰ ' + i['lessonStart'] + ' - ' + i['lessonEnd'] + ' '
                #     schedule_text = schedule_text + '🚪 ' + i['className'] + '\n'
                #     if i['subGroup'] == 0:
                #         schedule_text = schedule_text + '🗿 ' + 'Обе подргуппы' + ' '
                #     elif i['subGroup'] == 1:
                #         schedule_text = schedule_text + '🗿 ' + '1 подргуппа' + ' '
                #     elif i['subGroup'] == 2:
                #         schedule_text = schedule_text + '🗿 ' + '2 подргуппа' + ' '
                #     schedule_text = schedule_text + '🤡 ' + i['prepodName'] + '\n\n'





            elif user_state['role'] == 'prepod':
                schedule_tomorrow = API_repository._getSchedulePrepod(user_state['role'], user_state['arg'], 'tomorrow')

        elif event.text == 'На 2 недели' and user_state['state'] == 'Finish':
            if user_state['role'] == 'student':
                schedule_two_weeks = API_repository._getScheduleStudent(user_state['role'], user_state['arg'], 'two_weeks')

            elif user_state['role'] == 'prepod':
                schedule_two_weeks = API_repository._getSchedulePrepod(user_state['role'], user_state['arg'], 'two_weeks')



        #Ввел что-то не из перечня
        else:
            if user_state['state'] == 'None':
                API_repository._setUserState(event.user_id, 'Start')
                sender(event.user_id, 'Пожалуйтса авторизуйтесь', 4)

            elif user_state['state'] == 'Start':
                sender(event.user_id, 'НУ ТЫ ДУРАК БЛЯТЬ!? КНОПКА ЖЕ ЕСТЬ', 4)

            elif user_state['state'] == 'RegisterUser':
                sender(event.user_id, 'НУ ТЫ ДУРАК БЛЯТЬ!? КНОПКА ЖЕ ЕСТЬ', 0)

            elif user_state['state'] == 'Finish':
                if user_state['role'] == 'student':
                    sender(event.user_id, 'Вы уже авторизованы как студент группы ' + user_state['arg'], 2)
                else:
                    sender(event.user_id, 'Вы уже авторизованы как преподаватель ' + user_state['arg'], 2)

            elif user_state['state'] == 'RegisterUser_S':
                if API_repository._checkGroup(event.text.upper())['status'] == 'OK':
                    API_repository._registerUser(event.user_id, 'student', event.text.upper())
                    sender(event.user_id, 'Вы успешно авторизовались!\n\nВыберите расписание', 2)
                    API_repository._setUserState(event.user_id, 'Finish')
                else:
                    API_repository._setUserState(event.user_id, 'Start')
                    sender(event.user_id, 'Пожалуйтса авторизуйтесь', 4)

            elif user_state['state'] == 'RegisterUser_P':
                if API_repository._checkFio(event.text)['status'] == 'OK':
                    API_repository._registerUser(event.user_id, 'prepod', event.text)
                    sender(event.user_id, 'Вы успешно авторизовались!\n\nВыберите расписание', 2)
                    API_repository._setUserState(event.user_id, 'Finish')
                else:
                    API_repository._setUserState(event.user_id, 'Start')
                    sender(event.user_id, 'Пожалуйтса авторизуйтесь', 4)