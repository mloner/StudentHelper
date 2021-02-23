import vk_api
from vk_api.longpoll import VkLongPoll, VkEventType
import json
import keyboards

def write_msg(user_id, answer, keyboard_number):
    vk.method('messages.send', {
                                'user_id': user_id,
                                'message': answer,
                                'random_id': 0,
                                'keyboard': keyboards.keyboards[keyboard_number]
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

            if event.text == 'Преподаватель':
                write_msg(event.user_id, 'Введите свои ФИО в формате "Фамилия И.О."', 3)

            elif event.text == 'Студент':
                write_msg(event.user_id, 'Выбери группу', 1)

            elif event.text == 'В начало':
                write_msg(event.user_id, 'Кто Вы?', 0)

            elif event.text == 'ИВТ-363':
                write_msg(event.user_id, 'Выберите, какое расписание вы хотите получить', 2)

