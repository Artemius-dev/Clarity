package org.artemzhuravlov.clarity.navigation.contacts

import com.arkivanov.decompose.ComponentContext
import kotlin.coroutines.CoroutineContext

class DefaultContactsScreenComponent(
    componentContext: ComponentContext,
    mainContext: CoroutineContext,
    private val ioContext: CoroutineContext,
): ComponentContext by componentContext, IContactsScreenComponent {

    class Factory(): IContactsScreenComponent.Factory {
        override fun invoke(
            componentContext: ComponentContext,
            mainContext: CoroutineContext,
            ioContext: CoroutineContext,
        ): IContactsScreenComponent = DefaultContactsScreenComponent(
            componentContext = componentContext,
            mainContext = mainContext,
            ioContext = ioContext,
        )
    }
}