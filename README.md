# CineSwipe
Android-приложение для выбора фильма на вечер: свайпы карточек, оценка просмотренного шкалой и персональные подборки. Kotlin, KMP, Jetpack Compose, MVVM, TMDB API.


## Технологии

| Область | Решение |
|---|---|
| Язык и платформа | Kotlin, Kotlin Multiplatform (Android рабочий, iOS-таргеты объявлены «на вырост») |
| UI | Jetpack Compose, Navigation Compose с типобезопасными маршрутами, Material 3 |
| Сеть | Ktor Client, kotlinx.serialization |
| База данных | Room (KMP) — с этапа 3 |
| DI | Koin |
| Асинхронность | Coroutines, Flow |
| Логирование | Napier |
| Тесты | kotlin.test, Ktor MockEngine, kotlinx-coroutines-test, Turbine |

conflict