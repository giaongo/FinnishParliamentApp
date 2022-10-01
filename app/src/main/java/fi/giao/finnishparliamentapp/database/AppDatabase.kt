package fi.giao.finnishparliamentapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ParliamentMember::class, MemberReview::class, MemberFavorite::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract val memberDao: MemberDao
    abstract val reviewDao: ReviewDao
    abstract val  favoriteDao: FavoriteDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "parliament_member_database.db"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}