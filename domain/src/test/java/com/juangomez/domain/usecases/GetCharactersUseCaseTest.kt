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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetCharacterUseCaseTest {

    private lateinit var getCharacterUseCase: GetCharacterUseCase

    @MockK
    private lateinit var characterRepository: CharacterRepository

    @BeforeEach
    fun setup() = MockKAnnotations.init(this)

    @Test
    fun `should call to character repository when executes use case`() {
        val characterIds = listOf(1, 183)
        val characters = mockk<List<Character>>()
        getCharacterUseCase = GetCharacterUseCase(characterRepository)

        coEvery { characterRepository.getCharactersById(characterIds) } returns Either.Right(characters)
        runBlocking { getCharacterUseCase.invoke(this, GetCharacterUseCase.Params(characterIds)) }
        coVerify(exactly = 1) { characterRepository.getCharactersById(characterIds) }
    }
}