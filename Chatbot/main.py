import vk_api
from vk_api.longpoll import VkLongPoll, VkEventType
import json
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

        # authorization = API_repository._getAuthData(event.user_id)
        # if authorization['status'] == 'FAIL':
        #     sender(event.user_id, 'Пожалуйтса авторизуйтесь', 4)
        # else:
        #     sender(event.user_id, 'Выберите расписание', 2)

        if event.text == 'Авторизация' and user_state['state'] != 'Finish':
            API_repository._setUserState(event.user_id, 'RegisterUser')
            sender(event.user_id, 'Кто Вы?', 0)

        elif event.text == 'Студент' and user_state['state'] != 'Finish':
            API_repository._setUserState(event.user_id, 'RegisterUser_S')
            sender_without_keyboard(event.user_id, 'Введите группу')

        elif event.text == 'Преподаватель' and user_state['state'] != 'Finish':
            API_repository._setUserState(event.user_id, 'RegisterUser_P')
            sender_without_keyboard(event.user_id, 'Введите, пожалуйста, ваше ФИО в формате "Фамилия И.О."')

        elif event.text == 'Разлогиниться':
            API_repository._setUserState(event.user_id, 'Start')
            sender(event.user_id, 'Пожалуйтса авторизуйтесь', 4)


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
                if API_repository._checkFIO(event.text)['status'] == 'OK':
                    API_repository._registerUser(event.user_id, 'prepod', event.text)
                    sender(event.user_id, 'Вы успешно авторизовались!\n\nВыберите расписание', 2)
                    API_repository._setUserState(event.user_id, 'Finish')
                else:
                    API_repository._setUserState(event.user_id, 'Start')
                    sender(event.user_id, 'Пожалуйтса авторизуйтесь', 4)