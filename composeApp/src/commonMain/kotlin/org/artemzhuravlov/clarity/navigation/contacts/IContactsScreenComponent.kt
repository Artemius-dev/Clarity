package org.artemzhuravlov.clarity.navigation.contacts

import com.arkivanov.decompose.ComponentContext
import kotlin.coroutines.CoroutineContext

interface IContactsScreenComponent {
    fun interface Factory {
        operator fun invoke( // 2
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext
        ): IContactsScreenComponent
    }
}