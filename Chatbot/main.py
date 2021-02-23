import vk_api
from vk_api.longpoll import VkLongPoll, VkEventType
import time



def write_msg(user_id, answer):
    message = answer[0]
    vk.method('messages.send', {
                                'user_id': user_id,
                                'message': answer,
                                'random_id' : time.time(),
                               }
             )

# API-токен
import tokens
token = tokens.my_token
# Авторизуемся как сообщество
vk = vk_api.VkApi(token=token)

# Работа с сообщениями
longpoll = VkLongPoll(vk)

# Основной цикл
for event in longpoll.listen():
    if event.type == VkEventType.MESSAGE_NEW:
        if event.to_me:

            print('\n\nNew message:')
            print('For me by: {}'.format(event.user_id))
            print("Text: " + event.text)
            write_msg(event.user_id, 'Здарова')
            