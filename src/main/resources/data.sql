INSERT INTO users VALUES
(1,'Aslan Javasky', 'Aslan@com','$2a$10$Lo4UiZysZZG0Giglk2zlWOAPMEmB67qkBCGqIiCB7uYFeBPCFeCIi','@Aslan_Javasky','89999999999','г.Черкесск, ул.Ленина, д.1')
ON CONFLICT(id) DO NOTHING;

INSERT INTO menu_items VALUES
(1, 'Гиро в лаваше L', 'MAIN_MENU', 240.00),
(2, 'Гиро в лаваше XL', 'MAIN_MENU', 290.00),
(3, 'Гиро в лепешке', 'MAIN_MENU', 240.00),
(4, 'Гиро в пите', 'MAIN_MENU', 240.00),
(5, 'Люля кебаб на углях в лаваше', 'MAIN_MENU', 330.00),
(6, 'Люля на углях в лепешке', 'MAIN_MENU', 330.00),
(7, 'Чизбургер куринный', 'MAIN_MENU', 230.00),
(8, 'Хот-Дог', 'MAIN_MENU', 150.00),
(9, 'Блэкбургер', 'MAIN_MENU', 230.00),
(10, 'Фри L', 'ZAKUSKI', 120.00),
(11, 'Фри XL', 'ZAKUSKI', 150.00),
(12, 'По-деревенски', 'ZAKUSKI', 150.00),
(13, 'Наггетсы', 'ZAKUSKI', 150.00),
(14, 'Французский Хот-Дог', 'ZAKUSKI', 150.00),
(15, 'Халапеньо', 'DOBAVKI', 40.00),
(16, 'Сыр', 'DOBAVKI', 40.00),
(17, 'Фирменный от Шефа', 'SAUCE', 40.00),
(18, 'Томатный', 'SAUCE', 40.00),
(19, 'Барбекю', 'SAUCE', 40.00),
(20, 'Сырный', 'SAUCE', 40.00),
(21, 'Чесночный', 'SAUCE', 40.00),
(22, 'Кисло-Сладкий', 'SAUCE', 40.00),
(23, 'Шаурма L', 'MAIN_MENU', 240.00),
(24, 'Шаурма XL', 'MAIN_MENU', 290.00)
ON CONFLICT(id) DO NOTHING;