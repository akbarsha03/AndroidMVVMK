package com.examz.testapp.di.utils

object BaseConfig {

    val currentEnvironment = Environment.DEV

    enum class Environment {
        PROD,
        DEV
    }
}

object NetworkConfig {
    fun getBaseUrl(environment: BaseConfig.Environment): String {
        return when (environment) {
            BaseConfig.Environment.PROD -> TODO()
            BaseConfig.Environment.DEV -> "https://api.thecatapi.com/"
        }
    }
}