package com.daniel.housetasker.di

import android.content.Context
import androidx.room.Room
import com.daniel.housetasker.data.database.HouseTaskerDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val APP_DATABASE_NAME = "app_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, HouseTaskerDatabase::class.java, APP_DATABASE_NAME).build()


    @Singleton
    @Provides
    fun provideTaskDao(db:HouseTaskerDatabase) = db.getTaskDao()

    @Singleton
    @Provides
    fun provideCategoryDao(db:HouseTaskerDatabase) = db.getCategoryDao()

    @Singleton
    @Provides
    fun provideMemberDao(db:HouseTaskerDatabase) = db.getMemberDao()

    @Singleton
    @Provides
    fun provideAssignmentDao(db:HouseTaskerDatabase) = db.getAssignmentDao()
}