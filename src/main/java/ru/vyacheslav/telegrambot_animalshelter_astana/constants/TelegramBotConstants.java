package ru.vyacheslav.telegrambot_animalshelter_astana.constants;

public class TelegramBotConstants {
    //*************************************--COMMAND1--************************

    public static final String START = "/start";
    public static final String START_MENU_DOG = "/menu_Dog";
    public static final String START_MENU_CAT = "/menu_Cat";
    public static final String HOW_TO_ADOPT_DOG = "/how_to_adopt_a_Dog";
    public static final String REPORT = "/report";
    public static final String CALL = "/call";
    public static final String CONTACT = "/contact";
    public static final String REPEAT = "/repeat";
    //*************************************--COMMAND2--************************
    //for dogs
    public static final String ACQUAINTANCE_DOG = "/acquaintance_Dog";
    public static final String DOCUMENTS_DOG = "/documents_Dog";
    public static final String TRANSPORTATION_DOG = "/transportation_Dog";
    public static final String ADVICE_LITTLE_DOG = "/advice_little_Dog";
    public static final String ADVICE_BIG_DOG = "/advice_big_Dog";
    public static final String ADVICE_LIMITED_DOG = "/advices_limited_Dog";
    public static final String ADVICES_CYNOLOGIST = "/advices_cynologist";
    public static final String TESTED_CYNOLOGIST = "/tested_cynologists";
    public static final String CAUSE = "/cause";

    //for cats

    public static final String ACQUAINTANCE_CAT = "/acquaintance_Dog";
    public static final String DOCUMENTS_CAT = "/documents_Dog";
    public static final String TRANSPORTATION_CAT = "/transportation_Dog";
    public static final String ADVICE_LITTLE_CAT = "/advice_little_Dog";
    public static final String ADVICE_BIG_CAT = "/advice_big_Dog";
    public static final String ADVICE_LIMITED_CAT = "/advices_limited_Dog";


    //*************************************--Menu-1---*************************
    public static final String GREETING_MSG = " выберите приют:" +
            "\n" + START_MENU_DOG + " - приют для собак" +
            "\n" + START_MENU_CAT + " - приют для кошек";

    public static final String REPORT_FORM = "Для отправки отчета сделай reply на это сообщение:\n" +
            "\n* Фото питомца.\n" +
            "* Рацион питомца.\n" +
            "* Общее самочувствие и привыкание к новому месту.\n" +
            "* Изменение в поведении: отказ от старых привычек, приобретение новых.\n" +
            "\nОтчет нужно присылать каждый день, ограничений в сутках по времени сдачи отчета нет." +
            "\n" + START;

    public static final String NO_MORE_REPORTS = "Вам больше не нужно присылать отчеты!";

    // У меня в боте меню красиво выглядит благодаря такой невыравненности здесь.
    public static final String LIST_MENU_DOG =
            "/info_shelterDog   -  Узнать информацию о приюте для собак\n" +
                    HOW_TO_ADOPT_DOG + "    - Как взять собаку из приюта\n" +
                    REPORT + " - Прислать отчет о питомце\n" +
                    CALL + "      - Позвать волонтера\n" +
                    CONTACT + " - Ваши контакты\n" +
                    "\n" + START;

    public static final String LIST_MENU_CAT =
            "/info_shelterCat   -  Узнать информацию о приюте для кошек\n" +
                    HOW_TO_ADOPT_DOG + "   - Как взять кошку из приюта\n" +
                    REPORT + " - Прислать отчет о питомце\n" +
                    CALL + "      - Позвать волонтера\n" +
                    CONTACT + " - Ваши контакты\n" +
                    "\n" + START;


    public static final String CONTACT_TEXT = "\"Для отправки данных сделай reply на это сообщение:\n" +
            "Имя: Ваше имя;\n" +
            "Телефон: +70001234567;\n" +
            "Email: email@email.com;\n" +
            "Адрес: Ваш адрес\n" +
            "\n" + START;


    //*************************************--Menu-2---*************************
    public static final String LIST_MENU_CONSULTATION_DOG = " выберите интересующий вас пункт:\n" +
            ACQUAINTANCE_DOG + " - Правила знакомства с питомцем\n" +
            DOCUMENTS_DOG + " - Список необходимых документов\n" +
            TRANSPORTATION_DOG + " - Рекомендации по перевозке\n" +
            ADVICE_LITTLE_DOG + " - Обустройство дома малышу\n" +
            ADVICE_BIG_DOG + " - Обустройство дома взрослому питомцу\n" +
            ADVICE_LIMITED_DOG + " - Обустройство дома питомцу с О.В.\n" +
            ADVICES_CYNOLOGIST + " - Советы кинолога\n" +
            TESTED_CYNOLOGIST + " - Проверенные кинологи\n" +
            CAUSE + " - Список причин для отказа\n" +
            CALL + " - Позвать волонтера\n" +
            "\n" + START;


    public static final String LIST_MENU_CONSULTATION_CAT = " выберите интересующий вас пункт:\n" +
            ACQUAINTANCE_CAT + " - Правила знакомства с питомцем\n" +
            DOCUMENTS_CAT + " - Список необходимых документов\n" +
            TRANSPORTATION_CAT + " - Рекомендации по перевозке\n" +
            ADVICE_LITTLE_CAT + " - Обустройство дома малышу\n" +
            ADVICE_BIG_CAT + " - Обустройство дома взрослому питомцу\n" +
            ADVICE_LIMITED_CAT + " - Обустройство дома питомцу с О.В.\n" +
            CAUSE + "  - Список причин для отказа\n" +
            CALL + " - Позвать волонтера\n" +
            "\n" + START;


    public static final String DEFAULT_TEXT = "Извините, но такой команды у нашего бота нет!\n/start";
    //*************************************---texts 1---*************************


    public static final String INFO_TEXT_DOG = " Приют для собак реализован в рамках государственно-частного партнерства," +
            "Находиться приют в районе спецЦОНа (автоЦОН).В новом питомнике постоянно содержаться около четырехсот собак." +
            "В нем есть специально оборудованная территория для выгула и тренировок собак, вет.лечебница. В питомнике можно увидеть" +
            " щенков и взрослых собак различных пород, дрессировочные площадки, карантинный отсек и блок стерилизации животных.\n/menu_Dog\n" +
            "\n" + START;

    public static final String INFO_TEXT_CAT = " Приют для кошек реализован в рамках государственно-частного партнерства," +
            "Находиться приют в районе рынка Восточный.В новом питомнике для кошек постоянно содержаться около  ста кошек." +
            "В нем есть специально оборудованная территория для выгула и игр  кошек, вет.лечебница. В питомнике можно увидеть" +
            " множество разных пород кошек, карантинный отсек и блок стерилизации животных.\n/menu_Cat\n" +
            "\n" + START;

    public static final String HOW_TEXT_DOG =
            "Как забрать собаку.\n" +
                    "Как правило, все расходы по транспортировке и содержанию питомца в приюте ложатся на нового хозяина. Обсудите " +
                    "сумму с куратором заранее. Подготовьтесь" +
                    " к приезду питомца домой: купите для него лежанку для сна, игрушки и корм, объясните детям, как нужно вести себя" +
                    " с собакой. Лучше приехать за ней на своей машине или нанять такси. В общественном транспорте будет некомфортно ехать " +
                    "ни вам, ни собаке, а слишком шумная обстановка может напугать животное.\n" +
                    "   Требования к заявителю\n" +
                    "Необходимо знать, что собак из приютов не отдают всем желающим. Вам придется доказать, что питомец попадёт в хорошие руки," +
                    " что ему будет обеспечено качественное содержание и уход, и что все члены вашей семьи единодушно согласны принять в дом" +
                    "нового жильца.\n" +
                    "    Перед тем, как подавать заявку, подумайте, сможете ли вы хотя бы в первые несколько недель освободить свой рабочий " +
                    "график. Переезд в новый дом будет серьёзным стрессом для собаки, поэтому надо некоторое время побыть с ней рядом. Первые дни " +
                    "в новой обстановке — самые важные для налаживания доверительных отношений между хозяином и питомцем.\n/consultation_Dog\n/menu_Dog\n" +
                    "\n" + START;

    public static final String HOW_TEXT_CAT =
            "Как забрать кошку.\n" +
                    "Как правило, все расходы по транспортировке и содержанию питомца в приюте ложатся на нового хозяина. Обсудите " +
                    "сумму с куратором заранее. Подготовьтесь" +
                    " к приезду питомца домой: купите для него лежанку для сна, игрушки и корм, объясните детям, как нужно вести себя" +
                    " с собакой. Лучше приехать за ней на своей машине или нанять такси. В общественном транспорте будет некомфортно ехать " +
                    "ни вам, ни собаке, а слишком шумная обстановка может напугать животное.\n" +
                    "   Требования к заявителю\n" +
                    "Необходимо знать, что собак из приютов не отдают всем желающим. Вам придется доказать, что питомец попадёт в хорошие руки," +
                    " что ему будет обеспечено качественное содержание и уход, и что все члены вашей семьи единодушно согласны принять в дом" +
                    "нового жильца.\n" +
                    "    Перед тем, как подавать заявку, подумайте, сможете ли вы хотя бы в первые несколько недель освободить свой рабочий " +
                    "график. Переезд в новый дом будет серьёзным стрессом для собаки, поэтому надо некоторое время побыть с ней рядом. Первые дни " +
                    "в новой обстановке — самые важные для налаживания доверительных отношений между хозяином и питомцем.\n/consultation_Cat\n/menu_Cat\n" +
                    "\n" + START;

    public static final String CALL_TEXT = "Для вызова волонтера нашего приюта нужно оставить заявку по телефону 8-800-800-80-80 (звонок бесплатный)." +
            " Далее с заявителем свяжется волонтёр штаба «Мы вместе» для уточнения деталей.\n" +
            "Удостовериться, что позвонил настоящий волонтёр, можно, сравнив номер заявки, который сообщил оператор горячей линии," +
            " с номером, который назовёт доброволец. Помощь оказывается бесплатно.\n" +
            "\n" + START;


//*************************************---texts 2---*************************


    public static final String ACQUAINTANCE_TEXT_DOG =
            "Наконец-то скоро наступит тот долгожданный момент, когда у вас в доме появится новый питомец.\n" +
                    "Сразу возникает вопрос “А как же правильно знакомить его с вашей семьей и другим животным?”. \n" +
                    "Главная цель, чтобы новое животное чувствовало себя желанным, но при этом не вызвало недовольство и агрессию у других членов семьи.\n" +
                    "Однозначно, что вам нужно потратить определенное время и, конечно же, проявить некоторую долю терпения.\n" +
                    "Давайте попробуем разобраться, как лучше всего действовать представляя нового питомца семье.\n" +
                    "* Когда вы принесете питомца домой, поместите его в безопасную комнату, чтобы он немного привык к дому.\n" +
                    "* Если вы хотите, чтобы ваш новый питомец чувствовал себя как дома, дарите ему достаточно внимания и любви.\n" +
                    "* Позволяйте животному вас исследовать. Лягте на пол, чтобы уменьшить свой рост — это сделает вас менее “пугающим”.\n" +
                    "* Встаньте рядом и позвольте ему вас понюхать, прогуляться вокруг вас, задеть, потереться или даже запрыгнуть." +
                    " Следует помнить, что первоначальное знакомство с вами поможет ему в дальнейшем найти общий язык и с другими членами семьи.\n" +
                    "* Приготовьте угощения. Когда животное к вам приблизится — предложите угощения. Достаточно будет их бросить рядом на пол или дать на вытянутой руке.\n" +
                    "* Играйте как можно больше. Не важно какую игру вы выберете. Это может быть простая болтающаяся игрушка, шелестящая мышка или лазерная указка." +
                    " Любые игры однозначно помогут уменьшить стресс у вашего питомца.\n/consultation_Dog\n/menu_Dog\n" +
                    "\n" + START;


    public static final String ACQUAINTANCE_TEXT_CAT =
            "Наконец-то скоро наступит тот долгожданный момент, когда у вас в доме появится новый питомец.\n" +
                    "Сразу возникает вопрос “А как же правильно знакомить его с вашей семьей и другим животным?”. \n" +
                    "Главная цель, чтобы новое животное чувствовало себя желанным, но при этом не вызвало недовольство и агрессию у других членов семьи.\n" +
                    "Однозначно, что вам нужно потратить определенное время и, конечно же, проявить некоторую долю терпения.\n" +
                    "Давайте попробуем разобраться, как лучше всего действовать представляя нового питомца семье.\n" +
                    "* Когда вы принесете питомца домой, поместите его в безопасную комнату, чтобы он немного привык к дому.\n" +
                    "* Если вы хотите, чтобы ваш новый питомец чувствовал себя как дома, дарите ему достаточно внимания и любви.\n" +
                    "* Позволяйте животному вас исследовать. Лягте на пол, чтобы уменьшить свой рост — это сделает вас менее “пугающим”.\n" +
                    "* Встаньте рядом и позвольте ему вас понюхать, прогуляться вокруг вас, задеть, потереться или даже запрыгнуть." +
                    " Следует помнить, что первоначальное знакомство с вами поможет ему в дальнейшем найти общий язык и с другими членами семьи.\n" +
                    "* Приготовьте угощения. Когда животное к вам приблизится — предложите угощения. Достаточно будет их бросить рядом на пол или дать на вытянутой руке.\n" +
                    "* Играйте как можно больше. Не важно какую игру вы выберете. Это может быть простая болтающаяся игрушка, шелестящая мышка или лазерная указка." +
                    " Любые игры однозначно помогут уменьшить стресс у вашего питомца.\n/consultation_Cat\n/menu_Cat\n" +
                    "\n" + START;


    public static final String DOCUMENTS_TEXT_DOG = "Для оформления собаки от вас потребуются следующие документы:\n" +
            "* Паспорт\n" +
            "* Справка с места работы\n" +
            "* Характеристика\n" +
            "\n/consultation_Dog\n/menu_Dog\n" +
            "\n" + START;
    public static final String DOCUMENTS_TEXT_CAT = "Для оформления кошки от вас потребуются следующие документы:\n" +
            "* Паспорт\n" +
            "* Справка с места работы\n" +
            "* Характеристика\n" +
            "\n/consultation_Cat\n/menu_Cat\n" +
            "\n" + START;

    public static final String TRANSPORTATION_TEXT_DOG = "Если вы везете собаку в машине, обязательно необходимо следить за тем, чтобы " +
            "собака не перегрелась или наоборот не переохладилась из-за открытого окна или интенсивно работающего кондиционера. Если путь " +
            "длительный, необходимо регулярно делать остановки в подходящих для этого спокойных местах, чтобы собака могла размять лапы, попить " +
            "воды (которую тоже обязательно брать с собой специально для животного), сходить в туалет. В пригородных поездах и на электричках для" +
            "собаки тоже нужно купить отдельный билет — это бумажный документ с отметками «живность» или «багаж на руках», который продаётся в кассе. " +
            "Стоимость зависит от города, длины маршрута и поезда. В классах 2А, 2Б, 2Д, 2К, 2У, 2Л, 2Н есть условие, что количество пассажиров и собак " +
            "не должно превышать количество мест в купе. Например, если вы едете вдвоём с собакой, нужно купить два места.Есть исключение: в вагонах " +
            "всех типов можно бесплатно провозить собак-поводырей, при этом никакие перевозочные и ветеринарные документы не требуются. Главное, чтобы у " +
            "пассажира были документы о подтверждении инвалидности, собака была в ошейнике и наморднике и сидела у ног пассажира.\n/consultation_Dog\n/menu_Dog\n" +
            "\n" + START;

    public static final String TRANSPORTATION_TEXT_CAT = "В случае с перевозкой кошек желательно иметь подходящую по размеру переноску с " +
            "жесткими стенками и мягкой подстилкой внутри. Перевозить кошку на руках или без переноски крайне не рекомендуется, поскольку " +
            "она может помешать водителю вплоть до создания аварийной ситуации. К тому же, большинство кошек в закрытой переноске чувствуют " +
            "себя гораздо спокойнее.Перевозка животных в поездах возможна только в специально отведенных для этого вагонах. На них имеется " +
            "соответствующие значки. Билет хозяин животного приобретает для себя, но в специальный вагон, где разрешен провоз животных." +
            "Кошки относятся к мелким или комнатным животным, поэтому их можно брать в вагон с собой. Важное условие – это заранее оповестить " +
            "проводника о намерении ехать с котом. Для этого достаточно при посадке показать животное проводнику. Небольшие расстояния в поезде " +
            "можно преодолевать без дополнительных контейнеров, держа кота на поводке и под постоянным присмотром.\n/consultation_Cat\n/menu_Cat\n" +
            "\n" + START;

    public static final String ADVICE_LITTLE_TEXT_DOG = "Появление в дома щенка – это большая радость и не менее большая ответственность. " +
            "Малыш испортит вам мебель, ковер, в также нацелится на книги и журналы, которые только сможет найти. Конечно, все это происходит " +
            "не из-за плохого характера, просто щенок еще не умеет себя правильно вести в новом жилище. Чтобы привыкание прошло легче и для " +
            "малыша, и для его хозяина, к появлению нового члена семьи нужно подготовиться.  Щенок как маленький ребенок, ему все интересно и " +
            "необходимо попробовать на вкус. Провода на полу обязательно привлекут его внимание. Чтобы не допустить опасных ситуаций, поднимите " +
            "все провода повыше, спрячьте под ковры или в отдельные короба.Не застилайте пол скользящими тканями, лучше использовать покрытия " +
            "типа ковролина.Домашние растения. Убирайте их повыше. Если щенок обратит внимание на горшок, вам придётся собирать землю и песок по " +
            "всему дому. Ставьте все растения как можно выше – на подоконники, шкафы, столы. Некоторые цветы могут быть опасны для собак, " +
            "и от таких вообще лучше избавиться.\n/consultation_Dog\n/menu_Dog\n" +
            "\n" + START;

    public static final String ADVICE_LITTLE_TEXT_CAT = "" +
            "Ведь маленький котенок – всё равно, что маленький ребенок. Ему свойственно познавать окружающий мир любым удобным для него способом," +
            " пусть даже в ваши планы и не входили результаты этого познания. Маленькие неприятности можно с легкостью предупредить, " +
            "убрав все хрупкие предметы туда, где их не найдёт котёнок. Следует чем-нибудь закрыть узкие щели между мебелью и стеной," +
            " откуда малыша трудно будет достать. Заигравшись, котёнок может проглотить мелкие предметы (скрепки, пуговки)." +
            " Их лучше убрать подальше.Котята имеют привычку жевать и возить по полу всё, что попадает в поле их зрения и покажется интересным." +
            " Поэтому, чтобы уберечь провода электроприборов, лучше их спрятать под ковры или за мебель.\n/consultation_Cat\n/menu_Cat\n" +
            "\n" + START;


    public static final String ADVICE_BIG_TEXT_DOG =
            "Собака становится членом семьи, младшим ребенком на долгие 10-15 лет (а может и больше). Поэтому вам стоит серьезно позаботиться о ее собственном уголке.\n" +
                    "Вашему четвероногому другу обязательно нужно свое место, где он сможет отдохнуть, подремать или просто побыть в одиночестве.\n" +
                    "Выбирая такое место, неплохо бы согласовать его с собакой. Потому что иначе пес найдет себе местечко по душе, не спросив вашего мнения.\n" +
                    "Любая собака должна иметь в доме свои места, два из которых обязательны: место кормления и место для отдыха. Третье — туалет," +
                    " создается либо по желанию хозяев, либо в экстренных случаях (болезнь, появление щенков).\n/consultation_Dog\n/menu_Dog\n" +
                    "\n" + START;


    public static final String ADVICE_BIG_TEXT_CAT =
            "Ваш дом — это тихая гавань для вашей кошки. Как и любому члену семьи, ей нужна здоровая среда обитания, которая позволит ей расти, " +
                    "играть и, что самое важное, процветать. Создание благоприятной среды для пожилой любимицы может поспособствовать повышению ее" +
                    " активности и умственной стимуляции, а также уменьшить риск возможных проблем с поведением. Как можно обустроить место для кошки" +
                    " в доме или комнате? Читайте наши советы.\n Обеспечьте кошке наличие необходимого (вертикального) пространства. Это даст ей больше возможностей" +
                    " для перемещения и лазания в целом, а также станет идеальным местом для размещения таких аксессуаров, как, например, кошачье дерево," +
                    " которое предоставит вашей пожилой кошке много мест, где можно спрятаться, полежать или посидеть. \n" +
                    "Внесите в свой список когтеточку. Когтеточки позволяют кошке выпустить пар. К тому же они продлят жизнь вашей мебели! Убедитесь, что когтеточка" +
                    " вашей пожилой кошки устойчива и сделана из материалов, не вредных для животных, например из дерева, сизалевых канатов или грубой ткани. Поместите " +
                    "ее рядом с окном, спальной зоной любимицы или другим местом, которое она любит и где может позволить себе быть кошкой.\n/consultation_Cat\n/menu_Cat\n" +
                    "\n" + START;


    public static final String ADVICE_LIMITED_TEXT_DOG = "Бывают ситуации, когда из-за врожденных особенностей, болезни или травмы, собака становится инвалидом. Может показаться, " +
            "что жизнь такого питомца будет полна страданий, но это заблуждение. Если собака не испытывает болей, а хозяин готов ухаживать и помогать собаке адаптироваться к новой " +
            "жизни, то она как правило и не замечет неудобств, связанных с особенностями ее здоровья. К таким животным нужен особенный подход. Глухую собаку легко напугать, особенно " +
            "если вы внезапно появляетесь перед ней, так как она не слышит ваших шагов заранее. Важно уметь сообщать собаке о том, что вы хотите сделать. Для этого нужно привлекать " +
            "ее внимание без использования звука, например, с помощью вибрации или света. Постучите ногой или рукой по полу, если хотите позвать любимца или предупредить о вашем " +
            "приближении. Для слепого питомца очень важно обеспечить безопасную среду: в первую очередь внимательно осмотрите квартиру, уберите все торчащие предметы, которые могут " +
            "поранить собаку, огородите лестницы, закройте люки и ямы во дворе частного дома и уберите все провода. Чтобы собака легко ориентировалась дома, вы можете использовать узкие " +
            "ковровые дорожки, по которым питомец будет ходить к своему месту, к миске, к дверям. Убедитесь, что, когда вы уходите из дома, собака остается в безопасном " +
            "пространстве.\n/consultation_Dog\n/menu_Dog\n" +
            "\n" + START;


    public static final String ADVICE_LIMITED_TEXT_CAT = "Очень часто бывает так, что слепой кот ничем, кроме внешности, не отличается от кота с нормальным зрением. " +
            "Примеров множество. Хозяева таких кошек говорят, что они в пылу игры могут, конечно, упасть с дивана или стукнуться о дверь, например. Но и тогда их азарт " +
            "и энергия не угасают. Главное – кот знает, что его любят. А задевание мебели или столкновение с предметом они воспринимают как мимолетное недоразумение, " +
            "которое забывается тем быстрее, чем моложе и игривее кот. В случаях когда ваш питомец с ограниченными возможностями, нужно поначалу быть рядом с таким " +
            "«особенным» животным. Может быть, показать ему, где что лежит, купить наполнитель с запахом, чтобы кот быстрее его находил, ходить по новым местам вместе." +
            "Также у глухой кошки нет чувства страха, поэтому ее не испугает ни дрель, ни пылесос. Если она хоть немного видит, можно приучить ее к жестам («нельзя» —" +
            "потрясти пальцем, «иди ко мне» — поманить рукой, и так далее). Ну и, конечно, глухую кошку категорически нельзя отпускать на улицу одну, поскольку там машины, которых она не услышит." +
            "Кричать и обижать такую кошку — также под запретом. Она замкнется в себе и перестанет вообще контактировать с человеком. Поэтому нужно объяснить детям, что значит быть глухим и слепым, " +
            "научить ребенка ставить себя на место другого. Став другом и помощником такого «особенного» кота, ребенок на всю жизнь запомнит, что такое любовь, забота " +
            "и сострадание, и перенесет это в свою будущую семью. Ведь именно с животных начинается наше знакомство с миром и знакомство с жизнью, — знакомство с будущим собой.\n/consultation_Cat\n/menu_Cat\n" +
            "\n" + START;


    public static final String REJECTION_REASONS =
            "1 Большое количество животных дома\n" +
                    "2 Нестабильные отношения в семье\n" +
                    "3 Наличие маленьких детей\n" +
                    "4 Съемное жилье\n" +
                    "5 Животное в подарок или для работы\n" +
                    "\n" + START;


    public static String CONTACT_DATA_PATTERN = "(Имя:)(\\s)(\\w+);\n(Телефон:)(\\s)\\+([0-9]{11});\n(Email:)(\\s)([\\w\\-\\.]+@([\\w-]+\\.)+[\\w-]{2,4});\n(Адрес:)(\\s)(\\w+)";


    public static final String ADVICES_TEXT_CYNOLOGIST =
            "Ненахова Ксения (Лика) Викторовна — Председатель Тверской Федерации Спортивно-Прикладного Собаководства. Тренер, инструктор, спортсмен. \n" +
                    "Кандидат в мастера спорта по спортивно-прикладному собаководству (СПС)\n" +
                    "Воспитание питомца – это общее психо-физическое формирование личности. Привитие собаке норм и правил поведения в повседневной жизни и взаимодействия с окружающей средой, людьми и животными. " +
                    "Дрессировка – это обучение конкретным навыкам или определенным нормативам или направлениям. воспитание обязательно абсолютно для всех без исключения, а дрессировка — для большинства собак. " +
                    "Некоторым породам собак для допуска в разведение (получение потомства) необходимо иметь сертификаты по дрессировке по конкретной службе или нормативу. Это зависит от пород собак. Охотничьим – одно, " +
                    "служебным – другое, пастушьим – третье и т.д. Также существует большая группа беспородных собак или метисов. И они могут иметь в себе кровь, как комнатно-декоративных маленьких собачек, так и огромных " +
                    "служебных псов. И всех их ЛУЧШЕ и ПРОЩЕ всего начинать учить с самого юного возраста\n/consultation_Dog\n/menu_Dog\n" +
                    "\n" + START;


    public static final String TESTED_TEXT_CYNOLOGIST = "Кинологи в Астане:\n" +
            "Светлана Чернобаева - Работает дистанционно\n" +
            "* Кинология  5000–10000 ₽\n" +
            "* Коррекция поведения собаки 5000–10000\n" +
            "* Управляемая городская собака 5000–10000\n" +
            "О себе\n" +
            "Я работаю кинологом 6 лет, очень люблю собак. Особенность моей работы в том что, я подхожу к каждому клиенту" +
            " индивидуально. Я всегда стремлюсь получить результат от своей работы, всегда готова к любым ситуациям и никогда не останавливаюсь на достигнутом.\n" +
            "\nВиктория Титова - Район Алматинский, Выезд к клиенту, Астана\n" +
            "* Дрессировка щенка  3500–4000 ₽\n" +
            "* Дрессировка охотничьих собак 3500–4000 ₽\n" +
            "* Коррекция поведения собаки 3500–4000 ₽\n" +
            "О себе\n" +
            "Свидетельство об обучении на кинологических курсах по программе \"Собаковод-любитель\" 2021–2022 гг.\n/consultation_Dog\n/menu_Dog\n" +
            "\n" + START;

}
