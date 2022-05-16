package me.podlesnykh.graduationproject.network

/**
 * Обертка над NewsApi для удобного представления данных
 *
 * @param data полезная нагрузка ответа
 * @param exception исключение, брошенное в результате ошибки
 */
sealed class NewsApiResponse<T>(
    data: T? = null,
    exception: Exception? = null
) {
    /**
     * Модель для успешного ответа сервера
     */
    data class Success<T>(val data: T) :
        NewsApiResponse<T>(data = data, exception = null)

    /**
     * Модель для ошибки
     */
    data class Error<T>(val exception: Exception) :
        NewsApiResponse<T>(data = null, exception = exception)
}
