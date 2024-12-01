package br.com.smdevelopment.rastreamentocorreios.domain.usecase.impl

import br.com.smdevelopment.rastreamentocorreios.data.entities.resource.Resource
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.fail


@ExperimentalCoroutinesApi
class CreateUserUseCaseImplTest {

    private lateinit var auth: FirebaseAuth
    private lateinit var createUserUseCase: CreateUserUseCaseImpl

    @Before
    fun setUp() {
        auth = mockk()
        createUserUseCase = CreateUserUseCaseImpl(auth)
    }

    @Test
    fun `test create user success`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        val mockAuthResult: Task<AuthResult> = mockk()
        coEvery { auth.createUserWithEmailAndPassword(email, password) } returns mockAuthResult
        coEvery { mockAuthResult.isSuccessful } returns true

        // When & Then
        createUserUseCase.createUserAndPassword(email, password).collect { resource ->
            when (resource) {
                is Resource.Success -> {
                    assertTrue(resource.data == true)
                }

                is Resource.Error -> {
                    fail("Expected success, but got error: ${resource.message}")
                }

                else -> {
                    fail("Unexpected state")
                }
            }
        }

        coVerify { auth.createUserWithEmailAndPassword(email, password) }
    }

    @Test
    fun `test create user failure`() = runTest {
        // Given
        val email = "test@example.com"
        val password = "password123"
        coEvery {
            auth.createUserWithEmailAndPassword(
                email,
                password
            )
        } throws Exception("Network error")

        // When & Then
        createUserUseCase.createUserAndPassword(email, password).collect { resource ->
            when (resource) {
                is Resource.Error -> {
                    assertEquals("Failed to create user: Network error", resource.message)
                }

                is Resource.Success -> {
                    fail("Expected error, but got success")
                }

                else -> {
                    fail("Unexpected state")
                }
            }
        }

        coVerify { auth.createUserWithEmailAndPassword(email, password) }
    }
}
