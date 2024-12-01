package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import com.google.firebase.auth.FirebaseAuth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ChangePasswordUseCaseImplTest {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var changePasswordUseCase: ChangePasswordUseCaseImpl

    @Before
    fun setUp() {
        firebaseAuth = mockk()
        changePasswordUseCase = ChangePasswordUseCaseImpl(firebaseAuth)
    }

    @Test
    fun `test successful password reset`() = runTest {
        // Given
        val email = "test@example.com"
        val task = mockk<com.google.android.gms.tasks.Task<Void>>()
        coEvery { firebaseAuth.sendPasswordResetEmail(email) } returns task
        coEvery { task.isSuccessful } returns true

        // When & Then
        changePasswordUseCase.changePassword(email).collect { result ->
            assertTrue(result) // Should be true on success
        }

        // Verify that sendPasswordResetEmail was called with the correct email
        coVerify { firebaseAuth.sendPasswordResetEmail(email) }
    }

    @Test
    fun `test failed password reset`() = runTest {
        // Given
        val email = "test@example.com"
        val task = mockk<com.google.android.gms.tasks.Task<Void>>()
        coEvery { firebaseAuth.sendPasswordResetEmail(email) } returns task
        coEvery { task.isSuccessful } returns false // Simulating failure

        // When & Then
        changePasswordUseCase.changePassword(email).collect { result ->
            assertFalse(result) // Should be false on failure
        }

        // Verify that sendPasswordResetEmail was called with the correct email
        coVerify { firebaseAuth.sendPasswordResetEmail(email) }
    }

    @Test
    fun `test exception during password reset`() = runTest {
        // Given
        val email = "test@example.com"
        coEvery { firebaseAuth.sendPasswordResetEmail(email) } throws Exception("Network error")

        // When & Then
        changePasswordUseCase.changePassword(email).collect { result ->
            assertFalse(result) // Should be false when an exception is thrown
        }

        // Verify that sendPasswordResetEmail was called with the correct email
        coVerify { firebaseAuth.sendPasswordResetEmail(email) }
    }
}
