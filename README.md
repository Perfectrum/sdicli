1. Оля
2. Даниил
3. Реализация ранера и команд.

Ранер получает на вход список команд. Все команды реализуют одинаковый интерфейс. Все команды имеют в качестве поля список аргументов и имеют
метод run. Метод run принимает абстрактный ридер (далее - входящий ридер) и возвращает абстрактный ридер (далее - исходящий ридер), 
чтобы можно было строить цепочки. Также этот метод декларирует то что он выбрасывает исключение.

Использование абстрактных ридеров в качестве параметров позволяет менять возможные реализации. 
Мы можем читать или писать данные по сети, из файлов, из объектов. Для нашей программы это будет неважно.

Если в качестве входящего ридера передается null значит на стандартный вход этой команды ничего не было подано.
Скорее всего эта команда была первой в цепочке. Но возможны и другие случаи. Проверять на null входящий параметр - задача
каждой команды.

Все команды реализуют логику работы в методе run. Команды могут использовать данные полученные через аргументы, а могут 
не использовать. Команды могут использовать информацию из входящего ридера, а могут не использовать. Эти детали 
зависят от реализации конкретной команды. Главное, что все команды возвращают исходящий ридер. Из этого ридера можно будет 
считывать результаты работы команды.

Ранер проверяет результативность выполнения команды. Если в ходе работы команды было выброшено исключение, то оно 
перехватывается и об этом сообщается пользователю. Последующие команды не выполняются.

После выполнения всех команд ранер считывает информацию из последнего исходящего ридера, и отправляет ее на консоль.

### Список всех доступных команд:

#### EchoCommand

Echo игнорирует все что поступило во входящем ридере. Выводит в исходящий ридер все свои аргументы через один пробел.

#### CatCommand

Cat выводит содержимое файлов, которые указаны в аргументах. 
Если файлы не указаны, тогда команда перенаправляет содержимое входящего ридера в исходящий ридер.

#### WcCommand

Wc выводит количество строк, слов и байт в файлах, которые указаны в аргументах. 
Если файлы не указаны, тогда команда выводит количество строк, слов и байт в данных входящего ридера.

#### PwdCommand

Pwd выводит содержимое текущей директории. Игнорирует данные во входящем ридере и игнорирует аргументы.

#### ExecuteCommand

Execute выполняет произвольную команду. На вход команде передаются аргументы. Стандартный потоки входа читает данные из
входящего ридера, а стандартный поток вывода записывает данные в исходящий ридер.

#### ExitCommand

Команда завершает работу интерпретатора.
