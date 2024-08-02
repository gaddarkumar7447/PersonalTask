package utils

sealed class ResultStatus<T>(var data : T ?= null,var messsage : String?= null) {
    class Loading<T> : ResultStatus<T>()
    class Success<T>(data: T?) : ResultStatus<T>(data = data)
    class Error<T>(message: String?) : ResultStatus<T>(messsage = message)
}