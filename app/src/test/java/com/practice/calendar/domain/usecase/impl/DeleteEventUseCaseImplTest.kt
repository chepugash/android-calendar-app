package com.practice.calendar.domain.usecase.impl

import com.practice.calendar.domain.repository.EventRepository
import com.practice.calendar.domain.usecase.DeleteEventUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DeleteEventUseCaseImplTest {

    @MockK
    lateinit var repository: EventRepository

    private lateinit var useCase: DeleteEventUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = DeleteEventUseCaseImpl(repository)
    }

    @Test
    fun whenDeleteEventUseCase() {
//        arrange
        val id = 2L
        val expectedResult = Unit
        coEvery {
            repository.deleteEvent(id)
        } returns Unit
//        act
        runTest {
//            assert
            val result: Unit = useCase.invoke(id)
            assertEquals(expectedResult, result)
        }

    }
}