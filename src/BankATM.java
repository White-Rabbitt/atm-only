import java.util.InputMismatchException;
import java.util.Scanner;

public class BankATM {

    static double surplus = 1000;

    public static void main(String[] args) {

        auth();
        selectFunction();
    }

    public static boolean auth() {
        boolean flag = true;
        Scanner input = new Scanner(System.in);
        for (int i = 1; i <= 3; i++) {
            System.out.println("Введите номер карты:");
            String inputCard = input.next();
            try {
                Long.parseLong(inputCard);
                System.out.println("Номер карты введен");
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат ввода. Попробуйте снова");
            }

            System.out.println("Введите пароль:");
            String inputPwd = input.next();
            try {
                Integer.parseInt(inputPwd);
                System.out.println("Пароль введен");
            } catch (NumberFormatException e) {
                System.out.println("Неверный формат ввода");
            }
            flag = checkCardAndPassword(inputCard, inputPwd, i);
            if (flag) {
                break;
            }
        }
        return flag;
    }

    public static boolean checkCardAndPassword(String inputCard, String inputPwd, int count) {
        String cardNum = "6228123123";
        int pwd = 888888;

        if (inputCard.equals(cardNum) && inputPwd.equals(inputPwd)) {
            return true;
        } else {
            if (count <= 2) {
                System.out.println("Номер карты или пароль указан неверно. Осталось попыток: " + (3 - count));
            } else {
                System.out.println("Ваша карта заблокирована!");
            }
            return false;
        }
    }

    public static void selectFunction() {
        System.out.println("""
                Выберите функцию:
                1 - Вывод средств
                2 - Пополнение счета
                3 - Проверка баланса
                4 - Платежи и переводы
                5 - Выход""");
        Scanner input = new Scanner(System.in);
        try {
            int func = input.nextInt();
            switch (func) {
                case 1 -> {
                    System.out.println("Операция: Вывод средств");
                    getMoney();
                }
                case 2 -> {
                    System.out.println("Операция: Пополнение счета");
                    depositMoney();
                }
                case 3 -> {
                    System.out.println("Операция: Проверка баланса");
                    System.out.println("На счету: " + surplus);
                    continueAtm();
                }
                case 4 -> {
                    System.out.println("Операция: Платежи и переводы");
                    transfer();
                }
                case 5 -> {
                    System.out.println("Операция: Выход");
                    System.out.println("Спасибо за обращение!");
                    System.exit(0);
                }
                default -> {
                    System.out.println("Неверное значение");
                    continueAtm();
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Неверное значение");
            continueAtm();
        }
    }

    public static void getMoney() {
        Scanner input = new Scanner(System.in);
        System.out.println("Введите сумму для снятия (кратно 100):");
        int money = input.nextInt();
        if (surplus < money) {
            System.out.println("Недостаточно средств");
        } else if (money < 0) {
            System.out.println("Введена некорректная сумма");
        } else {
            multiple(surplus, money);
        }
        surplus -= money;
        continueAtm();
    }

    public static double multiple(double surplus, int money) {
        if (money % 100 == 0) {
            surplus -= money;
            System.out.println("Выдано: " + money);
            System.out.println("Остаток на карте: " + surplus);
            return surplus;
        } else {
            System.out.println("Невозможно выдать запрошенную сумму");
        }
        continueAtm();
        return surplus;
    }

    public static void depositMoney() {
        Scanner input = new Scanner(System.in);
        System.out.println("Поместите банкноты в купюроприемник (сумма не более 10000)");
        double saveMoney = input.nextInt();
        if (saveMoney <= 0 || saveMoney > 10000) {
            System.out.println("Вы пытаетесь внести недопустимую сумму");
        } else if (saveMoney % 100 == 0) {
            surplus += saveMoney;
            System.out.println("Вы внесли: " + saveMoney + ". Баланс: " + surplus);
        } else if (saveMoney % 100 != 0 || saveMoney % 10 != 0) {
            double backMoney = saveMoney % 100;
            surplus = surplus + saveMoney - backMoney;
            System.out.println("Вы внесли: " + (saveMoney - backMoney) + ". Баланс: " + surplus + ". Сдача: " + backMoney);
        } else {
            System.out.println("Купюра является поддельной и будет конфискована");
        }
        continueAtm();
    }

    public static void transfer() {
        Scanner input = new Scanner(System.in);
        System.out.println("Введите сумму перевода:");
        double goMoney = input.nextDouble();
        if (goMoney <= 0 || goMoney > surplus) {
            System.out.println("Невозможно перевести указанную сумму");
        } else {
            surplus -= goMoney;
            System.out.println("Перевод выполнен успешно. Баланс: " + surplus);
        }
        continueAtm();
    }

    public static void continueAtm() {
        System.out.println("Прододжить обслуживание? Да / Нет");
        String continueAtm = new Scanner(System.in).nextLine();
        if (continueAtm.equals("Да")) {
            selectFunction();
        } else if (continueAtm.equals("Нет")) {
            System.out.println("Спасибо за обращение");
            System.exit(0);
        } else {
            System.out.println("Некорректный ввод");
            continueAtm();
        }
    }
}

