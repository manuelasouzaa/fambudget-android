package br.com.manuelasouzaa.fambudget.core.network.client

import android.util.Log
import br.com.manuelasouzaa.fambudget.R
import br.com.manuelasouzaa.fambudget.core.network.resource.Resource
import br.com.manuelasouzaa.fambudget.core.network.resource.UiText
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseHttpClient(
    private val dispatcher: CoroutineDispatcher = IO
) {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> Response<T>
    ): Resource<T> {
        return withContext(dispatcher) {
            try {
                val response = apiCall()

                Log.d(
                    "BaseHttpClient",
                    "URL: ${response.raw().request.url} | Code: ${response.code()} | Body: ${response.body()}"
                )

                if (response.isSuccessful) {
                    val body = response.body()

                    body?.let { Resource.Success(it) } ?: Resource.Error(
                        statusCode = response.code(),
                        message = response.message()
                    )
                } else {
                    Resource.Error(
                        statusCode = response.code(),
                        message = response.errorBody()?.string() ?: response.message()
                    )
                }
            } catch (e: UnknownHostException) {
                e.printStackTrace()
                Resource.Error(uiMessage = UiText.Resource(R.string.error_no_internet))
            } catch (e: SocketTimeoutException) {
                e.printStackTrace()
                Resource.Error(uiMessage = UiText.Resource(R.string.error_connection_timeout))
            } catch (e: Throwable) {
                e.printStackTrace()
                Resource.Error(uiMessage = UiText.Resource(R.string.error_unknown))
            }
        }
    }
}
