package com.developersementsov.cocktailbar.di

import com.developersementsov.di.ApplicationScope
import com.developersementsov.di.DefaultDispatcher
import com.developersementsov.di.IoDispatcher
import com.developersementsov.di.MainDispatcher
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

/**
 * -----------------------------------------------------------------------------
 * |    Dispatcher      |       Description      |            Uses             |
 * |--------------------|------------------------|-----------------------------|
 * | Dispatcher.Main    | Main thread on Android | Calling suspend functions   |
 * |                    |                        | Call UI functions           |
 * |                    |                        | Update LiveData/Flow             |
 * |--------------------|------------------------|-----------------------------|
 * | Dispatcher.IO      | Disk and network IO    | Database                    |
 * |                    |                        | File IO                     |
 * |                    |                        | Networking                  |
 * |--------------------|------------------------|-----------------------------|
 * | Dispatcher.Default | CPU intensive work     | Sorting or other algorithms |
 * |                    |                        | Parsing JSON                |
 * |                    |                        | DiffUtils                   |
 * |____________________|________________________|_____________________________|
 */
@Module
class CoroutineModule {

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @ApplicationScope
    @Singleton
    @Provides
    fun provideCoroutineMainScope(
        @MainDispatcher mainDispatcher: CoroutineDispatcher
    ): CoroutineScope {
        return CoroutineScope(SupervisorJob() + mainDispatcher)
    }
}
