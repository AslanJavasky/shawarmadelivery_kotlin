INSERT INTO users VALUES
(99999999999999,'Aslan Javasky', 'Aslan@com','$2a$10$Lo4UiZysZZG0Giglk2zlWOAPMEmB67qkBCGqIiCB7uYFeBPCFeCIi','@Aslan_Javasky','89999999999','г.Черкесск, ул.Ленина, д.1')
ON CONFLICT(id) DO NOTHING;

INSERT INTO menu_items(id, name, menu_section, price) VALUES
(1, 'Гиро в лаваше L', 'MAIN_MENU', 240.00),
(2, 'Гиро в лаваше XL', 'MAIN_MENU', 290.00),
(3, 'Гиро в лепешке', 'MAIN_MENU', 240.00),
(4, 'Гиро в пите', 'MAIN_MENU', 240.00),
(5, 'Шаурма L', 'MAIN_MENU', 240.00),
(6, 'Шаурма XL', 'MAIN_MENU', 290.00),
(7, 'Люля кебаб на углях в лаваше', 'MAIN_MENU', 330.00),
(8, 'Люля на углях в лепешке', 'MAIN_MENU', 330.00),
(9, 'Чизбургер куринный', 'MAIN_MENU', 230.00),
(10, 'Хот-Дог', 'MAIN_MENU', 150.00),
(11, 'Блэкбургер', 'MAIN_MENU', 230.00),
(12, 'Фри L', 'ZAKUSKI', 120.00),
(13, 'Фри XL', 'ZAKUSKI', 150.00),
(14, 'По-деревенски', 'ZAKUSKI', 150.00),
(15, 'Наггетсы', 'ZAKUSKI', 150.00),
(16, 'Французский Хот-Дог', 'ZAKUSKI', 150.00),
(17, 'Халапеньо', 'DOBAVKI', 40.00),
(18, 'Сыр', 'DOBAVKI', 40.00),
(19, 'Фирменный от Шефа', 'SAUCE', 40.00),
(20, 'Томатный', 'SAUCE', 40.00),
(21, 'Барбекю', 'SAUCE', 40.00),
(22, 'Сырный', 'SAUCE', 40.00),
(23, 'Чесночный', 'SAUCE', 40.00),
(22, 'Кисло-Сладкий', 'SAUCE', 40.00)
ON CONFLICT(id) DO NOTHING;