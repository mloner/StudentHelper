import vk_api
from vk_api.longpoll import VkLongPoll, VkEventType
import json
import keyboards
import API_repository

def write_msg(user_id, answer, keyboard_number):
    vk.method('messages.send', {
                                'user_id': user_id,
                                'message': answer,
                                'random_id': 0,
                                'keyboard': keyboards.keyboards[keyboard_number]
                               }
             )

def write_msg_withot(user_id, answer):
    vk.method('messages.send', {
                                'user_id': user_id,
                                'message': answer,
                                'random_id': 0,
                               }
             )

import tokens
token = tokens.my_token
vk = vk_api.VkApi(token=token)
longpoll = VkLongPoll(vk)

# Основной цикл
for event in longpoll.listen():
    if event.type == VkEventType.MESSAGE_NEW:
        if event.to_me:
            print('\n\nNew message:')
            print('For me by: {}'.format(event.user_id))
            print("Text: " + event.text)

            req_authorization = {}
            req_authorization['client_type'] = 'bot'

            write_msg(event.user_id, 'Пожалуйста аторизуйтесь', 4)
            if event.text == 'Авторизация':
                write_msg(event.user_id, 'Кто Вы?', 0)

                for event_authorization in longpoll.listen():
                    if event_authorization.type == VkEventType.MESSAGE_NEW:
                        if event_authorization.to_me:
                            print('\n\nNew message:')
                            print('For me by: {}'.format(event_authorization.user_id))
                            print("Text: " + event_authorization.text)

                            if event_authorization.text == 'Преподаватель':
                                write_msg_withot(event.user_id, 'Введите свои ФИО в формате "Фамилия И.О."')

                                req_authorization['command'] = 'authorization'
                                req_authorization['role'] = 'prepod'

                                for event_prepod in longpoll.listen():
                                    if event_prepod.type == VkEventType.MESSAGE_NEW:
                                        if event_prepod.to_me:
                                            print('\n\nNew message:')
                                            print('For me by: {}'.format(event_prepod.user_id))
                                            print("Text: " + event_prepod.text)

                                            req_authorization['arg'] = event_prepod.text

                                            if API_repository._authorization(json.dumps(req_authorization)):
                                                write_msg(event.user_id, 'Выберите, какое расписание вы хотите получить', 2)

                                                for event_schedule_p in longpoll.listen():
                                                    if event_schedule_p.type == VkEventType.MESSAGE_NEW:
                                                        if event_schedule_p.to_me:
                                                            print('\n\nNew message:')
                                                            print('For me by: {}'.format(event_schedule_p.user_id))
                                                            print("Text: " + event_schedule_p.text)

                                                            break
                                                break

                                            else:
                                                write_msg(event.user_id, 'Таких не знаем...\nПожалуйста аторизуйтесь', 4)
                                                break

                            elif event_authorization.text == 'Студент':
                                write_msg(event.user_id, 'Выбери группу', 1)

                                req_authorization['command'] = 'authorization'
                                req_authorization['role'] = 'student'

                                for event_student in longpoll.listen():
                                    if event_student.type == VkEventType.MESSAGE_NEW:
                                        if event_student.to_me:
                                            print('\n\nNew message:')
                                            print('For me by: {}'.format(event_student.user_id))
                                            print("Text: " + event_student.text)

                                            req_authorization['arg'] = event_student.text.split('-')[1]

                                            if API_repository._authorization(json.dumps(req_authorization)):
                                                write_msg(event.user_id, 'Выберите, какое расписание вы хотите получить', 2)

                                                for event_schedule_s in longpoll.listen():
                                                    if event_schedule_s.type == VkEventType.MESSAGE_NEW:
                                                        if event_schedule_s.to_me:
                                                            print('\n\nNew message:')
                                                            print('For me by: {}'.format(event_schedule_s.user_id))
                                                            print("Text: " + event_schedule_s.text)

                                                            break
                                                break



                                            else:
                                                write_msg(event.user_id, 'Таких не знаем...\nПожалуйста аторизуйтесь', 4)
                                                break

                            break

