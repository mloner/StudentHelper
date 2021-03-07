import json
import vk_api

def get_but(text, color):
    return {
        "action": {
            "type": "text",
            "payload": "{\"button\": \"" + "1" + "\"}",
            "label": f"{text}"
        },
        "color": f"{color}"
    }

def answer_but(count):
    tmp_count = count
    buttons = []
    if tmp_count < 6:
        for i in range(tmp_count):
            buttons.append(get_but(str(i+1),'primary'))
    return buttons

def answer_keyboard(count):
    keyboard_answer = {
        "one_time": False,
        "inline": True,
        "buttons": [answer_but(count)]
        }
    keyboard_answer = json.dumps(keyboard_answer, ensure_ascii=False).encode('utf-8')
    keyboard_answer = str(keyboard_answer.decode('utf-8'))
    return keyboard_answer

# def answer_but(answerVariants:[]):
#     buttons = []
#     for i in answerVariants:
#         buttons.append(get_but(i,'primary'))
#     return buttons
#
# def answer_keyboard(answerVariants:[]):
#     keyboard_answer = {
#         "one_time": False,
#         "inline": True,
#         "buttons": [answer_but(answerVariants)]
#         }
#     keyboard_answer = json.dumps(keyboard_answer, ensure_ascii=False).encode('utf-8')
#     keyboard_answer = str(keyboard_answer.decode('utf-8'))
#     return keyboard_answer

# Стартовая Авторизации
keyboard0 = {
    "one_time": True,
    "buttons": [
        [get_but('Преподаватель', 'primary'), get_but('Студент', 'primary')]
    ]
}
keyboard0 = json.dumps(keyboard0, ensure_ascii=False).encode('utf-8')
keyboard0 = str(keyboard0.decode('utf-8'))

# Клава Студент
keyboard1 = {
    "one_time": True,
    "buttons": [
        [get_but('ИВТ-363', 'primary'), get_but('ИВТ-365', 'primary')]
    ]
}
keyboard1 = json.dumps(keyboard1, ensure_ascii=False).encode('utf-8')
keyboard1 = str(keyboard1.decode('utf-8'))

# Клава выбор расписания препода
keyboard2 = {
    "one_time": True,
    "buttons": [
        [get_but('На сегодня', 'primary'), get_but('На завтра', 'primary'), get_but('По дате', 'primary')],
        [get_but('Разлогиниться', 'negative'), get_but('Пройти опрос', 'primary')]
    ]
}
keyboard2 = json.dumps(keyboard2, ensure_ascii=False).encode('utf-8')
keyboard2 = str(keyboard2.decode('utf-8'))

# Выбора подгруппы
keyboard3 = {
    "one_time": True,
    "buttons": [
        [get_but('1 подгруппа', 'primary'), get_but('2 подгруппа', 'primary')],
        [get_but('Обе подгруппы', 'primary')]
    ]
}
keyboard3 = json.dumps(keyboard3, ensure_ascii=False).encode('utf-8')
keyboard3 = str(keyboard3.decode('utf-8'))


# Клава Начало
keyboard4 = {
    "one_time": True,
    "buttons": [[get_but('Авторизация', 'secondary')]]
}
keyboard4 = json.dumps(keyboard4, ensure_ascii=False).encode('utf-8')
keyboard4 = str(keyboard4.decode('utf-8'))

keyboard5 = {
    "one_time": True,
    "buttons": [
        [get_but('На сегодня', 'primary'), get_but('На завтра', 'primary'), get_but('По дате', 'primary')],
        [get_but('Пройти опрос', 'primary'), get_but('Сменить подгруппу', 'primary')],
        [get_but('Разлогиниться', 'negative')]
    ]
}
keyboard5 = json.dumps(keyboard5, ensure_ascii=False).encode('utf-8')
keyboard5 = str(keyboard5.decode('utf-8'))

keyboards = [keyboard0, keyboard1, keyboard2, keyboard3, keyboard4, keyboard5]
