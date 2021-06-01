package uz.suhrob.zakovat.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import uz.suhrob.zakovat.data.firestore.FirestoreDataSource
import uz.suhrob.zakovat.data.firestore.FirestoreDataSourceImpl
import uz.suhrob.zakovat.data.pref.AppPrefs
import uz.suhrob.zakovat.data.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @ExperimentalCoroutinesApi
    @Singleton
    @Provides
    fun provideFirestoreDataSource(): FirestoreDataSource = FirestoreDataSourceImpl()

    @Singleton
    @Provides
    fun provideAppPref(@ApplicationContext context: Context): AppPrefs = AppPrefs(context)

    @Singleton
    @Provides
    fun provideRepository(dataSource: FirestoreDataSource, pref: AppPrefs): Repository =
        Repository(dataSource, pref)
}