package com.aitor.samplemarket.domain.common

sealed class Resource<out F, out S> {
    object Loading : Resource<Nothing, Nothing>()
    data class Success<S>(val data: S) : Resource<Nothing, S>()
    data class Failure<F>(val error: F) : Resource<F, Nothing>()
}

/** Extension function to fold a Resource.
 *  Usual usage should be:
 *  - Show a progress bar when isLoading.
 *  - Hide it when isNotLoading.
 *  - Show the data if the scenario is the happy path.
 *  - Show the error if something went wrong.
 *
 * val resource: Resource<String, List<String>> = Resource.Success(listOf("Adrian, Aitor, Javi, Pedro"))
 * resource.fold(
 *     isLoading = { progressBar.visible() },
 *     isNotLoading = { progressBar.gone() },
 *     isSuccess = { devs: List<String> -> adapter.submitList(devs) },
 *     isFailure = { message: String -> snackBarError(message) }
 * )
 */
inline fun <reified F, reified S> Resource<F, S>.fold(
    noinline isLoading: (() -> Unit)? = null,
    noinline isNotLoading: (() -> Unit)? = null,
    isSuccess: (S) -> Unit,
    isFailure: (F) -> Unit
) {
    if (this is Resource.Loading) isLoading?.invoke() else isNotLoading?.invoke()

    when (this) {
        is Resource.Success -> isSuccess(data)
        is Resource.Failure -> isFailure(error)
    }
}

/**
 * Transform any object to Success.
 * @param S is the object type to be wrapped in a Success Resource.
 * Example:
 * val numberList: List<Int> = listOf(1, 2, 3)
 * val resource: Resource<SomeError, List<Int>> = numberList.asSuccess()
 */
inline fun <reified S> S.asSuccess(): Resource.Success<S> = Resource.Success(this)

/**
 * Transform any object to Failure.
 * @param F is the object type to be wrapped in a Failure Resource.
 * Example:
 * val exception: SomeException = Exception()
 * val resource: Resource<String, SomeSuccess> = exception.localizedMessage.asFailure()
 */
inline fun <reified F> F.asFailure(): Resource.Failure<F> = Resource.Failure(this)

/**
 * Map any Resource to another Resource.
 * @param S is the original success resource type.
 * @param F is the original failure resource type.
 * @param S2 the new success resource type.
 * @param F2 the new failure resource type.
 * @param mapSuccess lambda to map [S] to [S2].
 * @param mapError lambda to map [F] to [F2].
 * Example:
 * val resourceA: Resource<String, String> = Resource.Success("1")
 * val resourceB: Resource<String, Int> = resourceA.map(
 *     data = { stringNumber: String -> stringNumber.toInt() },
 *     error = { error: String -> "Ooops $error" }
 * )
 */
inline fun <reified F, reified F2, reified S, reified S2> Resource<F, S>.map(
    mapError: (F) -> F2,
    mapSuccess: (S) -> S2
): Resource<F2, S2> = when (this) {
    is Resource.Loading -> Resource.Loading
    is Resource.Success -> Resource.Success(mapSuccess(this.data))
    is Resource.Failure -> Resource.Failure(mapError(this.error))
}

inline fun <reified F, reified S, reified S2> Resource<F, S>.mapSuccess(
    map: (S) -> S2
): Resource<F, S2> = when (this) {
    is Resource.Loading -> Resource.Loading
    is Resource.Success -> map(data).asSuccess()
    is Resource.Failure -> error.asFailure()
}
inline fun <reified F, reified F2, reified S> Resource<F, S>.mapFailure(
    map: (F) -> F2
): Resource<F2, S> = when (this) {
    is Resource.Loading -> Resource.Loading
    is Resource.Success -> data.asSuccess()
    is Resource.Failure -> map(error).asFailure()
}

inline val <F, S>Resource<F, S>.isLoading: Boolean get() = this is Resource.Loading
inline val <F, S>Resource<F, S>.isSuccess: Boolean get() = this is Resource.Success<S>
inline val <F, S>Resource<F, S>.isFailure: Boolean get() = this is Resource.Failure<F>

inline fun <F, S> Resource<F, S>.ifSuccess(block: (S) -> Unit) {
    if (this is Resource.Success) block(this.data)
}
