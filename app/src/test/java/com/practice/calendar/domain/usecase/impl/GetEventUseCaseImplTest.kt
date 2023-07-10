package com.practice.calendar.domain.usecase.impl

import com.practice.calendar.domain.entity.EventInfo
import com.practice.calendar.domain.repository.EventRepository
import com.practice.calendar.domain.usecase.CreateEventUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetEventUseCaseImplTest {

    @MockK
    lateinit var repository: EventRepository

    private lateinit var useCase: CreateEventUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = CreateEventUseCaseImpl(repository)
    }

    @Test
    fun whenGetEventUseCaseFails() {
//        arrange
        val expectedId = 2L
        val eventInfo = mockk<EventInfo> {
            every { id } returns 2
            every { dateStart } returns 100
            every { dateFinish } returns 200
            every { name } returns "sample"
            every { description } returns "sample description"
        }
        coEvery {
            repository.createEvent(eventInfo)
        } returns eventInfo.id
//        act
        runTest {
//            assert
            val result = useCase.invoke(eventInfo)
            assertEquals(expectedId, result)
        }
    }
}
