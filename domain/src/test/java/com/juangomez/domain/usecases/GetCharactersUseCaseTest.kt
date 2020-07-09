package com.juangomez.domain.usecases

import com.juangomez.common.Either
import com.juangomez.domain.models.Character
import com.juangomez.domain.repositories.CharacterRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCharactersUseCaseTest {

    private lateinit var getCharactersUseCase: GetCharactersUseCase

    @MockK
    private lateinit var characterRepository: CharacterRepository

    @Before
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to character repository when executes use case`() {
        val characterIds = listOf(1, 183)
        val characters = mockk<List<Character>>()
        getCharactersUseCase = GetCharactersUseCase(characterRepository)

        coEvery { characterRepository.getCharactersById(characterIds) } returns Either.Right(characters)
        runBlocking { getCharactersUseCase.invoke(this, GetCharactersUseCase.Params(characterIds)) }
        coVerify(exactly = 1) { characterRepository.getCharactersById(characterIds) }
    }
}