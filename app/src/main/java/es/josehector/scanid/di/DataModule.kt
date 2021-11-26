package es.josehector.scanid.di

import android.content.Context
import com.rocket.android.core.data.network.di.CoreDataNetworkProvider
import com.rocket.core.crashreporting.logger.CrashLogger
import com.rocket.core.data.network.commons.di.createApiClient
import com.rocket.core.data.network.commons.di.createOkHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import es.josehector.scanid.data.network.datasource.MobbScanNetworkDatasource
import es.josehector.scanid.data.network.service.MobbScanService
import es.josehector.scanid.data.repository.MobbScanDataRepository
import es.josehector.scanid.domain.repository.MobbScanRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import javax.inject.Singleton

/**
 * Hilt module to declare app's dependencies
 */

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    fun provideIOCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Singleton
    fun provideCoreDataNetworkProvider(@ApplicationContext context: Context): CoreDataNetworkProvider {
        return CoreDataNetworkProvider.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideCrashLogger(coreDataNetworkProvider: CoreDataNetworkProvider): CrashLogger {
        return coreDataNetworkProvider.crashLogger
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(
        coreDataNetworkProvider: CoreDataNetworkProvider,
    ): OkHttpClient {

        return createOkHttpClient(
            connectionInterceptor = coreDataNetworkProvider.connectionInterceptor,
            loggingInterceptor = coreDataNetworkProvider.httpLoggingInterceptor,
            headerInterceptor = coreDataNetworkProvider.headerInterceptor
        )
    }

    @Provides
    @Singleton
    fun provideMobbScanService(
        okHttpClient: OkHttpClient
    ): MobbScanService {
        return createApiClient(
            okHttpClient = okHttpClient,
            baseUrl = "https://demo.mobbeel.com/"   // TODO: get out to any config file
        )
    }

    @Provides
    @Singleton
    fun provideMobbScanNetworkDatasource(
        mobbScanService: MobbScanService,
        crashLogger: CrashLogger
    ): MobbScanNetworkDatasource {
        return MobbScanNetworkDatasource(
            mobbScanService = mobbScanService,
            crashLogger = crashLogger
        )
    }

    @Provides
    @Singleton
    fun provideMobbScanRespository(
        mobbScanNetworkDatasource: MobbScanNetworkDatasource
    ): MobbScanRepository {
        return MobbScanDataRepository(
            mobbScanNetworkDatasource = mobbScanNetworkDatasource
        )
    }
}