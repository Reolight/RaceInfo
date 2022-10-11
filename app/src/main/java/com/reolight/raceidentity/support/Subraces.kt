package com.reolight.raceidentity.support

import android.content.Context
import android.util.Range
import com.reolight.raceidentity.R
import com.reolight.raceidentity.support.enums.HairColor

//NB: ВСE ПЕРЕЧИСЛЕНИЯ НАЧИНАЮТСЯ С 0!!1
//Перечисления -- это низкий, средний, высокий. Узкий, широкий и проч. ГЛАЗА ТОЧНО ТАК ЖЕ! (БЫЛО 1 - 8, СТАЛО 0 - 7!!!)
//Кроме высоты лба, всё идёт как в файле (высота лба там дана качественным описанием: высокий, средний, низкий. Вот оно идёт в порядке:
//Низкий - 0, средний - 1, высокий - 2
//РАЗРЕЗ = ФОРМА ГЛАЗ!!! (EYE SHAPE НА АНГЛЕ)

class Subraces(context: Context) {

    companion object Factory {

        private var instance: Subraces? = null
        fun GetInstance(context: Context?) : Subraces{
            if (instance == null){
                instance = Subraces(context!!)
            }

            return instance!!
        }

        fun GetSubrace(name: String): Subrace? {
            instance?.let {
                return it.subraces.find { it.Name == name }
            }

            return null
        }
    }

    var subraces: List<Subrace> = listOf()
    init {
        subraces = listOf(
            //ЗАПОЛНЯТЬ СЮДА
            //ШАБЛОН: ИМЯ, ЦЕФ ИНД, ЛИЦ И, НОС И, ФОРМА НОСА, ВЫСОТА ЛБА, ФОРМА ЛБА, ВЫПУКЛОСТЬ ЗАТЫЛКА, ФОРМА ГЛАЗ, ШИРИНА СКУЛ, Ш ЧЕЛЮСТИ, ЦВЕТ КОЖИ, Ц ГЛАЗ, Ц ВОЛОС, ФОРМА ВОЛОС
            Subrace(
                context.getString(R.string.scandidoNordid),
                0,
                2,
                listOf(0),
                listOf(1),
                listOf(1, 2),
                listOf(1),
                listOf(2),
                listOf(1),
                listOf(0),
                listOf(1, 2),
                listOf(0),
                (0..7).toList(),
                listOf(Range(HairColor.A.ordinal, HairColor.M.ordinal)),
                listOf(1)
            ),

            Subrace(
                context.getString(R.string.east_nordidi),
                0,
                2,
                listOf(0),
                listOf(1, 2),
                listOf(2),
                listOf(1),
                listOf(2),
                listOf(1),
                listOf(0),
                listOf(0),
                listOf(0),
                (0..7).toList(),
                listOf(Range(HairColor.A.ordinal, HairColor.M.ordinal)),
                listOf(0, 1)
            ),

            Subrace(
                context.getString(R.string.celtic_nordid),
                1,
                2,
                listOf(0),
                listOf(1, 2),
                listOf(2),
                listOf(1),
                listOf(2),
                listOf(1),
                listOf(0),
                listOf(0),
                listOf(0),
                (8..10).toList(),
                listOf(Range(HairColor.A.ordinal, HairColor.W.ordinal)),
                listOf(0)
            ),

            Subrace(
                context.getString(R.string.sub_nordid),
                0,
                2,
                listOf(0),
                listOf(1),
                listOf(1, 2),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(0),
                (0..10).toList(),
                listOf(Range(HairColor.H.ordinal, HairColor.W.ordinal)),
                listOf(0)
            ),

            Subrace(
                context.getString(R.string.trender),
                1,
                1,
                listOf(1),
                listOf(1, 2),
                listOf(2),
                listOf(1),
                listOf(2),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(0),
                (0..4).toList(),
                listOf(Range(HairColor.E.ordinal, HairColor.W.ordinal)),
                listOf(0, 1)
            ),

            Subrace(
                context.getString(R.string.falid),
                1,
                0,
                listOf(2),
                listOf(0, 1),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(2),
                listOf(2),
                listOf(0),
                (0..7).toList(),
                listOf(Range(HairColor.A.ordinal, HairColor.M.ordinal)),
                listOf(0)
            ),

            Subrace(
                context.getString(R.string.borrebi),
                2,
                0,
                listOf(2),
                listOf(0, 1),
                listOf(2),
                listOf(0),
                listOf(1),
                listOf(1),
                listOf(2),
                listOf(2),
                listOf(0),
                (0..5).toList(),
                listOf(Range(HairColor.A.ordinal, HairColor.G.ordinal)),
                listOf(0)
            ),

            Subrace(
                context.getString(R.string.bryunn),
                2,
                0,
                listOf(2),
                listOf(0, 1),
                listOf(2),
                listOf(2),
                listOf(1),
                listOf(1),
                listOf(2),
                listOf(2),
                listOf(0),
                (0..4).toList(),
                listOf(
                    Range(HairColor.C.ordinal, HairColor.S.ordinal),
                    Range(HairColor.i.ordinal, HairColor.vi.ordinal)
                ),
                listOf(0, 1)
            ),

            Subrace(
                context.getString(R.string.western_baltid),
                2,
                0,
                listOf(1),
                listOf(1),
                listOf(2),
                listOf(0),
                listOf(1),
                listOf(1),
                listOf(1, 2),
                listOf(2),
                listOf(0),
                (0..7).toList(),
                listOf(Range(HairColor.A.ordinal, HairColor.W.ordinal)),
                listOf(0)
            ),

            Subrace(
                context.getString(R.string.baltid),
                2,
                0,
                listOf(1),
                listOf(0, 1, 3),
                listOf(2),
                listOf(0, 2),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(1, 2),
                listOf(0),
                (0..7).toList(),
                listOf(Range(HairColor.A.ordinal, HairColor.W.ordinal)),
                listOf(0)
            ),

            Subrace(
                context.getString(R.string.eastern_baltid),
                2,
                0,
                listOf(1, 2),
                listOf(0),
                listOf(1),
                listOf(0, 2),
                listOf(0),
                listOf(1, 2),
                listOf(2),
                listOf(2),
                listOf(0),
                (0..7).toList(),
                listOf(Range(HairColor.A.ordinal, HairColor.M.ordinal)),
                listOf(0)
            ),

            Subrace(
                context.getString(R.string.alpinid),
                2,
                0,
                listOf(1),
                listOf(0, 1),
                listOf(1, 2),
                listOf(0, 1),
                listOf(0),
                listOf(1, 2),
                listOf(1, 2),
                listOf(2),
                listOf(1),
                (15..17).toList(),
                listOf(Range(HairColor.O.ordinal, HairColor.S.ordinal)),
                listOf(0)
            ),

            Subrace(
                context.getString(R.string.mountid),
                2,
                0,
                listOf(1),
                listOf(0, 1),
                listOf(1, 2),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(1, 2),
                listOf(1),
                listOf(0, 1, 2),
                (8..11).toList() + (15..17).toList(),
                listOf(Range(HairColor.H.ordinal, HairColor.S.ordinal)),
                listOf(0, 1)
            ),

            Subrace(
                context.getString(R.string.mediterranid),
                0,
                0,
                listOf(0),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(2),
                listOf(1),
                listOf(0),
                listOf(0),
                listOf(2),
                (15..17).toList(),
                listOf(Range(HairColor.O.ordinal, HairColor.Y.ordinal)),
                listOf(2)
            ),

            Subrace(
                context.getString(R.string.atlantid),
                0,
                2,
                listOf(0),
                listOf(1),
                listOf(1, 2),
                listOf(0, 1),
                listOf(2),
                listOf(1),
                listOf(0),
                listOf(0),
                listOf(0, 1, 2),
                (8..10).toList(),
                listOf(Range(HairColor.M.ordinal, HairColor.W.ordinal)),
                listOf(0)
            ),

            Subrace(
                context.getString(R.string.northen_atlantid),
                0,
                2,
                listOf(0),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(2),
                listOf(1),
                listOf(0),
                listOf(0),
                listOf(0),
                (0..4).toList() + (8..10).toList(),
                listOf(Range(HairColor.T.ordinal, HairColor.W.ordinal)),
                listOf(0, 1, 2)
            ),

            Subrace(
                context.getString(R.string.pontid),
                0,
                2,
                listOf(0),
                listOf(1, 3),
                listOf(1, 2),
                listOf(1),
                listOf(2),
                listOf(1),
                listOf(0),
                listOf(0),
                listOf(1),
                (15..17).toList(),
                listOf(Range(HairColor.M.ordinal, HairColor.W.ordinal)),
                listOf(0)
            ),

            Subrace(
                context.getString(R.string.northen_pontid),
                1,
                2,
                listOf(0),
                listOf(1, 3),
                listOf(1, 2),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(0),
                listOf(0),
                (0..10).toList(),
                listOf(Range(HairColor.H.ordinal, HairColor.S.ordinal)),
                listOf(0, 1)
            ),

            Subrace(
                context.getString(R.string.dinarid),
                2,
                2,
                listOf(0),
                listOf(2),
                listOf(1),
                listOf(1),
                listOf(0, 1),
                listOf(1, 2),
                listOf(0),
                listOf(1),
                listOf(2),
                (15..17).toList(),
                listOf(Range(HairColor.O.ordinal, HairColor.Y.ordinal)),
                listOf(2)
            ),

            Subrace(
                context.getString(R.string.norik),
                2,
                2,
                listOf(0),
                listOf(1, 2),
                listOf(1, 2),
                listOf(1),
                listOf(0, 1),
                listOf(1),
                listOf(0),
                listOf(0, 1),
                listOf(0),
                (0..4).toList() + (8..15).toList(),
                listOf(Range(HairColor.E.ordinal, HairColor.O.ordinal)),
                listOf(0, 1, 2)
            ),

            Subrace(
                context.getString(R.string.armenid),
                2,
                0,
                listOf(0),
                listOf(2),
                listOf(1),
                listOf(1),
                listOf(0),
                listOf(0),
                listOf(0),
                listOf(2),
                listOf(2),
                (15..19).toList(),
                listOf(Range(HairColor.X.ordinal, HairColor.Y.ordinal)),
                listOf(2)
            ),

            Subrace(
                context.getString(R.string.caspid),
                1,
                1,
                listOf(1),
                listOf(1, 2),
                listOf(1),
                listOf(1),
                listOf(2),
                listOf(1, 2),
                listOf(0),
                listOf(1),
                listOf(2),
                (18..19).toList(),
                listOf(Range(HairColor.O.ordinal, HairColor.Y.ordinal)),
                listOf(2)
            ),

            Subrace(
                context.getString(R.string.caucasid),
                2,
                1,
                listOf(0),
                listOf(1, 2),
                listOf(0, 1),
                listOf(1),
                listOf(1),
                listOf(1),
                listOf(2),
                listOf(2),
                listOf(0, 1, 2),
                (6..7).toList() + (15..17).toList(),
                listOf(
                    Range(HairColor.O.ordinal, HairColor.Y.ordinal),
                    Range(HairColor.i.ordinal, HairColor.vi.ordinal)
                ),
                listOf(0)
            ),

            Subrace(
                context.getString(R.string.iranid),
                1,
                2,
                listOf(0),
                listOf(2),
                listOf(1, 2),
                listOf(1),
                listOf(2),
                listOf(1),
                listOf(0),
                listOf(0),
                listOf(2),
                (15..19).toList(),
                listOf(Range(HairColor.X.ordinal, HairColor.Y.ordinal)),
                listOf(2)
            ),

            Subrace(
                context.getString(R.string.arabid),
                0,
                1,
                listOf(0),
                listOf(1, 2),
                listOf(1, 2),
                listOf(1),
                listOf(2),
                listOf(1),
                listOf(0),
                listOf(0),
                listOf(2),
                (17..18).toList(),
                listOf(Range(HairColor.X.ordinal, HairColor.Y.ordinal)),
                listOf(2)
            ),

            Subrace(
                context.getString(R.string.indid),
                0,
                1,
                listOf(1),
                listOf(1),
                listOf(2),
                listOf(0),
                listOf(2),
                listOf(1),
                listOf(1),
                listOf(0),
                listOf(2),
                (6..7).toList() + (15..17).toList(),
                listOf(Range(HairColor.T.ordinal, HairColor.Y.ordinal)),
                listOf(0)
            )
        )
    }

    //ПРИМЕЧАНИЯ:
    //Обрати внимание: помещая строку (делай без context.getString(R.string.<Name>), это я потом сам сделаю),
    // пиши в "двойных кавычках. Типа "Скандидо-нордид"
    // (он уже написан выше. Так выглядит строка, которая ушла в ресурсы и может быть переведена на др языки.
    // Range (ИМЕННО ТАК, ЭТО НЕ ПАСКАЛЬ, ТУТ CASE SENSITIVE ВСЁ) - это интервал, пишешь: Range(минимум, максимум).
    // Такое есть с тремя первыми Индексами и Цветом волос
    //--------------------------------------------------------------------------------------------
    //ПО ПОВОДУ ВОЛОС! ПОСКОЛЬКУ ЗДЕСЬ ЕСТЬ РАЗРЫВЫ, ТО ТУТ СПИСОК ИНТЕРВАЛОВ Range:
    //      listOf(Range(min, max), Range(min, max))
    //  Ну и всё сделано как таблица A - Y, и i - vi. Обрати внимание: I и i -- два разных цвета!
    //--------------------------------------------------------------------------------------------
    //Обрати внимание, как объявляется цает глаз: (0..7).toList() -- это список из интервала
    //Если у тебя там глаза 1-8, 11-15, тогда образ действий похож на тот, что с волосами:
    //      (0..7).toList() + (10..14).toList() -- Вначале появляется listOf            <--- ПОПРАВКА
    //        ТУТ НЕ RANGE!      ТУТ ТОЖЕ!
    //                    ПЛЮС!
    //
    //Если там не интервал, а просто один цвет и интервал [e.g. 2..5, 8], то:
    //                                                      (1..4).toList() + listOf(7))
    //
    //          ПОПРАВКА: КОРЕННОЙ listOf НЕ НУЖЕН! ПРОСТО СКЛАДЫВАЕШЬ ДВА СПИСКА, ЧТОБЫ ПОЛУЧИТЬ ОДИН
    //--------------------------------------------------------------------------------------------
    //Тут везде listOf, если заметишь. Потому что все параметры могут иметь несколько допустимых значений.
    //Поэтому везде listOf и пишешь
    //--------------------------------------------------------------------------------------------
    //полная запись выглядит: Subrace(здесь параметры.....................),
    //              Начинаешь с Subrace(                заканчиваешь так, если есть ещё, что вводить
    //                         Subrace(здесь параметры2........))
    //                                     в самом конце должны быть 2 скобки!
    //                                 Одна закрывает Subrace, вторая listOf в самом верху
    //
    //Я сделал всё так, чтобы было в порядке таблицы. Так что проблем быть не должно.
    // Главное отнимай от цифр -1 (в проге перечисления и массивы начинаются с 0!)
}