package androidlead.weatherappui.ui.auth

import androidlead.weatherappui.ui.data.local.dao.UserDao
import androidlead.weatherappui.ui.data.local.entity.UserEntity
import java.security.MessageDigest
import java.security.SecureRandom
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun signUp(username: String, email: String, password: String): Result<Long> {
        if (userDao.findByUsername(username) != null) return Result.failure(IllegalStateException("Username exists"))
        if (userDao.findByEmail(email) != null) return Result.failure(IllegalStateException("Email exists"))

        val salt = generateSalt()
        val hash = hashPassword(password, salt)
        val id = userDao.insert(UserEntity(username = username, email = email, passwordHash = hash, salt = salt))
        return Result.success(id)
    }

    suspend fun login(username: String, password: String): Result<Unit> {
        val user = userDao.findByUsername(username) ?: return Result.failure(IllegalArgumentException("User not found"))
        val hash = hashPassword(password, user.salt)
        return if (hash == user.passwordHash) Result.success(Unit)
        else Result.failure(IllegalArgumentException("Wrong password"))
    }

    private fun generateSalt(): String {
        val bytes = ByteArray(16)
        SecureRandom().nextBytes(bytes)
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun hashPassword(password: String, saltHex: String): String {
        val md = MessageDigest.getInstance("SHA-256")
        val salt = saltHex.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        md.update(salt)
        val digest = md.digest(password.toByteArray())
        return digest.joinToString("") { "%02x".format(it) }
    }
}
