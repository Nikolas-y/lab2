package lab2.var4;

// Импортируются необходимые библиотеки для работы с GUI, событиями, изображениями и файлами
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

// Подавление предупреждений о несовместимости версий сериализации
@SuppressWarnings("serial")

// Главный класс приложения, наследующий JFrame (окно приложения)
public class MainFrame extends JFrame {

    // Размеры окна приложения в виде констант
    private static final int WIDTH = 640; // Ширина окна
    private static final int HEIGHT = 480; // Высота окна

    private int columnCount = 15; // Количество символов в текстовых полях

    // Поля для ввода значений X, Y, Z
    private JTextField textFieldX;
    private JTextField textFieldY;
    private JTextField textFieldZ;

    // Поля для отображения значений переменных памяти
    private JTextField textVar1;
    private JTextField textVar2;
    private JTextField textVar3;

    // Массив для хранения значений в "памяти" (3 переменные)
    private double[] memVariables = {0, 0, 0};
    private double result = 0; // Переменная для хранения результата вычислений

    // Поле для отображения результата
    private JTextField textFieldResult;

    // Группы радио-кнопок для выбора формулы и работы с памятью
    private ButtonGroup radioButtons = new ButtonGroup();
    private ButtonGroup memoryButtons = new ButtonGroup();

    // Контейнеры для размещения радио-кнопок
    private Box hboxFormulaType = Box.createHorizontalBox();
    private Box vboxMemoryVariables = Box.createVerticalBox();

    private JLabel formulaLabel; // Метка для отображения изображения формулы

    private int formulaId = 1; // ID текущей формулы (по умолчанию 1)
    private int memId = 1; // ID текущей переменной памяти (по умолчанию 1)

    // Формула №1 для расчета
    public double calculate1(double x, double y, double z) {
        // Вычисляет значение по заданной формуле
        return Math.sin(Math.log(y) + Math.sin(Math.PI * y * y)) *
                Math.pow(x * x + Math.sin(z) + Math.exp(Math.cos(z)), 0.25);
    }

    // Формула №2 для расчета
    public double calculate2(double x, double y, double z) {
        // Вычисляет значение по другой формуле
        return Math.pow(
                Math.cos(Math.exp(x)) + Math.log(Math.pow(1 + y, 2)) +
                        Math.sqrt(Math.exp(Math.cos(x)) + Math.pow(Math.sin(Math.PI * z), 2)) +
                        Math.sqrt(1 / x) + Math.cos(Math.pow(y, 2)),
                Math.sin(z)
        );
    }

    // Вспомогательный метод для добавления радио-кнопок для формул
    private void addRadioButton(String buttonName, final int formulaId) {
        JRadioButton button = new JRadioButton(buttonName); // Создаем радио-кнопку
        button.addActionListener(new ActionListener() { // Обработчик события
            public void actionPerformed(ActionEvent ev) {
                lab2.var4.MainFrame.this.formulaId = formulaId; // Устанавливаем ID формулы
                updateFormulaImage(); // Обновляем изображение формулы
            }
        });
        radioButtons.add(button); // Добавляем кнопку в группу
        hboxFormulaType.add(button); // Добавляем кнопку в горизонтальный контейнер
    }

    // Вспомогательный метод для добавления радио-кнопок для переменных памяти
    private void addMemButton(String buttonName, final int memId) {
        JRadioButton button = new JRadioButton(buttonName); // Создаем радио-кнопку
        button.addActionListener(new ActionListener() { // Обработчик события
            public void actionPerformed(ActionEvent e) {
                lab2.var4.MainFrame.this.memId = memId; // Устанавливаем ID переменной памяти
            }
        });
        memoryButtons.add(button); // Добавляем кнопку в группу
        vboxMemoryVariables.add(button); // Добавляем кнопку в вертикальный контейнер
    }

    // Метод для обновления изображения формулы в зависимости от выбранной формулы
    private void updateFormulaImage() {
        try {
            BufferedImage image; // Буфер для изображения
            if (formulaId == 1) { // Для формулы 1
                image = ImageIO.read(new File("src/lab2/var4/F1.png"));
            } else { // Для формулы 2
                image = ImageIO.read(new File("src/lab2/var4/F2.png"));
            }
            formulaLabel.setIcon(new ImageIcon(image)); // Устанавливаем изображение в метку
        } catch (IOException e) { // Обработка ошибок при загрузке изображения
            formulaLabel.setText("Ошибка загрузки изображения");
        }
    }

    // Метод для обновления значений переменных памяти в текстовых полях
    private void updateVarButton() {
        textVar1.setText(Double.toString(memVariables[0])); // Обновляем переменную 1
        textVar2.setText(Double.toString(memVariables[1])); // Обновляем переменную 2
        textVar3.setText(Double.toString(memVariables[2])); // Обновляем переменную 3
    }

    // Конструктор класса
    public MainFrame() {
        super("Вычисление формулы"); // Устанавливаем заголовок окна
        setSize(WIDTH, HEIGHT); // Устанавливаем размеры окна
        Toolkit kit = Toolkit.getDefaultToolkit(); // Получаем информацию о системе
        // Центрируем окно на экране
        setLocation((kit.getScreenSize().width - WIDTH) / 2,
                (kit.getScreenSize().height - HEIGHT) / 2);

        // Добавляем радио-кнопки для выбора формул
        hboxFormulaType.add(Box.createHorizontalGlue());
        addRadioButton("Формула 1", 1);
        addRadioButton("Формула 2", 2);
        radioButtons.setSelected(
                radioButtons.getElements().nextElement().getModel(), true);
        hboxFormulaType.add(Box.createHorizontalGlue());
        hboxFormulaType.setBorder(BorderFactory.createLineBorder(Color.YELLOW));

        // Добавляем радио-кнопки для выбора переменных памяти
        addMemButton("Переменная 1", 1);
        addMemButton("Переменная 2", 2);
        addMemButton("Переменная 3", 3);
        memoryButtons.setSelected(memoryButtons.getElements().nextElement().getModel(), true);
        vboxMemoryVariables.add(Box.createHorizontalGlue());

        // Создаем текстовые поля для отображения значений переменных памяти
        Box vboxMemText = Box.createVerticalBox();
        textVar1 = new JTextField("0", columnCount);
        textVar1.setMaximumSize(textVar1.getPreferredSize());
        vboxMemText.add(textVar1);
        vboxMemText.add(Box.createVerticalStrut(3));
        textVar2 = new JTextField("0", columnCount);
        textVar2.setMaximumSize(textVar2.getPreferredSize());
        vboxMemText.add(textVar2);
        vboxMemText.add(Box.createVerticalStrut(3));
        textVar3 = new JTextField("0", columnCount);
        textVar3.setMaximumSize(textVar3.getPreferredSize());
        vboxMemText.add(textVar3);
        updateVarButton();

        // Создаем область для отображения изображения формулы
        Box hboxFormulaImage = Box.createHorizontalBox();
        hboxFormulaImage.add(Box.createHorizontalGlue());
        formulaLabel = new JLabel();
        updateFormulaImage();
        hboxFormulaImage.add(formulaLabel);
        hboxFormulaImage.add(Box.createHorizontalGlue());

        // Создаем текстовые поля для ввода значений X, Y, Z
        JLabel labelForX = new JLabel("X:");
        textFieldX = new JTextField("0", 10);
        textFieldX.setMaximumSize(textFieldX.getPreferredSize());
        JLabel labelForY = new JLabel("Y:");
        textFieldY = new JTextField("0", 10);
        textFieldY.setMaximumSize(textFieldY.getPreferredSize());
        JLabel labelForZ = new JLabel("Z:");
        textFieldZ = new JTextField("0", 10);
        textFieldZ.setMaximumSize(textFieldZ.getPreferredSize());
        Box hboxVariables = Box.createHorizontalBox();
        hboxVariables.setBorder(BorderFactory.createLineBorder(Color.RED));
        hboxVariables.add(Box.createHorizontalGlue());
        hboxVariables.add(labelForX);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldX);
        hboxVariables.add(Box.createHorizontalStrut(100));
        hboxVariables.add(labelForY);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldY);
        hboxVariables.add(Box.createHorizontalStrut(100));
        hboxVariables.add(labelForZ);
        hboxVariables.add(Box.createHorizontalStrut(10));
        hboxVariables.add(textFieldZ);
        hboxVariables.add(Box.createHorizontalGlue());

        // Создаем область для отображения результата
        JLabel labelForResult = new JLabel("Результат:");
        textFieldResult = new JTextField("0", columnCount);
        textFieldResult.setMaximumSize(
                textFieldResult.getPreferredSize());
        Box hboxResult = Box.createHorizontalBox();
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.add(labelForResult);
        hboxResult.add(Box.createHorizontalStrut(10));
        hboxResult.add(textFieldResult);
        hboxResult.add(Box.createHorizontalGlue());
        hboxResult.setBorder(BorderFactory.createLineBorder(Color.BLUE));

        // Создаем кнопки управления
        JButton buttonCalc = new JButton("Вычислить");
        buttonCalc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                try {
                    // Считываем значения X, Y, Z из текстовых полей
                    double x = Double.parseDouble(textFieldX.getText());
                    double y = Double.parseDouble(textFieldY.getText());
                    double z = Double.parseDouble(textFieldZ.getText());
                    // Выбираем формулу для расчета
                    if (formulaId == 1)
                        result = calculate1(x, y, z);
                    else
                        result = calculate2(x, y, z);
                    // Проверяем на корректность результата
                    if (Double.isInfinite(result) || Double.isNaN(result))
                        JOptionPane.showMessageDialog(lab2.var4.MainFrame.this,
                                "Неразрешимая ошибка вычисления при имеющихся данных", "Неверные данные",
                                JOptionPane.WARNING_MESSAGE);
                    else textFieldResult.setText(Double.toString(result));
                } catch (NumberFormatException ex) {
                    // Обработка ошибок ввода
                    JOptionPane.showMessageDialog(lab2.var4.MainFrame.this,
                            "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Кнопка "Очистить поля"
        JButton buttonReset = new JButton("Очистить поля");
        buttonReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ev) {
                textFieldX.setText("0");
                textFieldY.setText("0");
                textFieldZ.setText("0");
                textFieldResult.setText("0");
            }
        });

        // Кнопка "M+" для сохранения результата в память
        JButton buttonMPlus = new JButton("M+");
        buttonMPlus.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memVariables[memId - 1] += result; // Добавляем результат в выбранную переменную памяти
                updateVarButton(); // Обновляем текстовые поля памяти
            }
        });

        // Кнопка "MC" для очистки памяти
        JButton buttonMC = new JButton("MC");
        buttonMC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                memVariables[memId - 1] = 0; // Очищаем выбранную переменную памяти
                updateVarButton(); // Обновляем текстовые поля памяти
            }
        });

        // Добавляем кнопки в горизонтальный контейнер
        Box hboxButtons = Box.createHorizontalBox();
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonCalc);
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.add(buttonReset);
        hboxButtons.add(Box.createHorizontalGlue());
        hboxButtons.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        // Создаем общий контейнер для размещения всех элементов
        Box contentBox = Box.createVerticalBox();
        Box varBox = Box.createHorizontalBox(); // Контейнер для переменных памяти
        Box MBox = Box.createHorizontalBox(); // Контейнер для кнопок памяти

        varBox.add(Box.createHorizontalGlue());
        varBox.add(vboxMemText);
        varBox.add(vboxMemoryVariables);
        varBox.add(Box.createHorizontalGlue());

        MBox.add(buttonMPlus);
        MBox.add(Box.createHorizontalStrut(400));
        MBox.add(buttonMC);

        // Добавляем все элементы в главный контейнер
        contentBox.add(Box.createVerticalGlue());
        contentBox.add(hboxFormulaType);
        contentBox.add(hboxFormulaImage);
        contentBox.add(hboxVariables);
        contentBox.add(hboxResult);
        contentBox.add(varBox);
        contentBox.add(MBox);
        contentBox.add(hboxButtons);
        contentBox.add(Box.createVerticalGlue());

        // Добавляем контейнер в окно
        getContentPane().add(contentBox, BorderLayout.CENTER);
    }

    // Главный метод запуска приложения
    public static void main(String[] args) {
        lab2.var4.MainFrame frame = new lab2.var4.MainFrame(); // Создаем экземпляр окна
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Устанавливаем действие при закрытии окна
        frame.setVisible(true); // Делаем окно видимым
    }
}