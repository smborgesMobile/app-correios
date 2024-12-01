package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginUseCaseImplTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var loginUseCaseImpl: LoginUseCaseImpl

    @Before
    fun setUp() {
        firebaseAuth = mockk()
        loginUseCaseImpl = LoginUseCaseImpl(firebaseAuth)
    }

    @Test
    fun `test successful login`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val mockAuthResult: Task<AuthResult> = mockk()

        coEvery { mockAuthResult.isSuccessful } returns true
        coEvery {
            firebaseAuth.signInWithEmailAndPassword(
                email,
                password
            )
        } returns mockAuthResult

        loginUseCaseImpl.login(password, email).collect { result ->
            assertTrue(result)
        }

        coVerify { firebaseAuth.signInWithEmailAndPassword(email, password) }
    }

    @Test
    fun `test failed login`() = runTest {
        val email = "test@example.com"
        val password = "password123"
        val taskCompletionSource = TaskCompletionSource<AuthResult>()

        taskCompletionSource.setException(Exception("Invalid credentials"))
        coEvery {
            firebaseAuth.signInWithEmailAndPassword(
                email,
                password
            )
        } returns taskCompletionSource.task

        loginUseCaseImpl.login(password, email).collect { result ->
            assertFalse(result)
        }

        coVerify { firebaseAuth.signInWithEmailAndPassword(email, password) }
    }
}
