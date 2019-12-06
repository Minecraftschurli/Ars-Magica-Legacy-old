import os
import re


def snake_case(name):
    s1 = re.sub('(.)([A-Z][a-z]+)', r'\1_\2', name)
    return re.sub('([a-z0-9])([A-Z])', r'\1_\2', s1).lower()


def main():
    path = os.getcwd()
    filenames = os.listdir(path)

    for filename in filenames:
        os.rename(filename, snake_case(filename))


if __name__ == '__main__':
    main()
