package online.viestudio.service

import org.koin.core.component.KoinComponent

interface LiveService : KoinComponent {

    val isEnabled: Boolean

    suspend fun enable()

    suspend fun disable()
}