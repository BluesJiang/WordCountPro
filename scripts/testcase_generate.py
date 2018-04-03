from functools import reduce
import numpy as np
from numpy.random import randint

config = {
    'word_size': [1, 10],
    'sep_size': [1,3],
    'word_repeat': [1, 300]
}
elements = {
    "words": "abcdefghijklmnopqrstuvwxyz-",
    "symbol": "!@#$%^&*()~`_+=|\\:;\"'<>?/ \t\r\n1234567890-"
}

def generate_usecase(num_of_type):
    global config
    global elements
    word_dict = {}
    for i in range(num_of_type):
        word_len = randint(*config['word_size'])
        word_elements = randint(0, len(elements['words']), word_len)
        word = np.array(list(elements['words']))[word_elements]
        word = word[1:] if word[0] == '-' else word
        word = word[:-1] if word[-1] == '-' else word

        word = ''.join(word)
        word_dict[word] = 0
    total_count = 0
    for key in word_dict.keys():
        word_dict[key] = randint(*config['word_repeat'])
        total_count += word_dict[key]
    word_dict_tmp = word_dict.copy()
    final_string = ''
    for i in range(total_count):
        key, val = None, 0
        while (val == 0):
            key_tmp = list(word_dict_tmp.keys())[randint(len(word_dict))]
            val = word_dict_tmp[key_tmp]
            if val != 0:
                key = key_tmp
                word_dict_tmp[key_tmp] = val-1
        word_upper_case = randint(0, 2, len(key))
        key = ''.join([s.upper() if word_upper_case[i] > 0 else s for i, s in enumerate(list(key))])
        final_string += key
        sep = ''
        for _ in range(randint(*config['sep_size'])):
            sep += elements['symbol'][randint(0, len(elements['symbol']))]
        if sep == '-':
            while sep == '-':
                sep = elements['symbol'][randint(0, len(elements['symbol']))]
        final_string += sep


    print (final_string)
    with open('usecase.txt', 'w') as f:
        f.write(final_string)
            
    sorted_key = sorted(word_dict.items(), key=lambda x:x[1])
    result = ''
    for key, val in sorted_key:
        result += key + ': ' + str(val) + '\n'
    print(result)
    with open('result.txt', 'w') as f:
        f.write(result)

def main():
    generate_usecase(100)

if __name__ == '__main__':
    main()