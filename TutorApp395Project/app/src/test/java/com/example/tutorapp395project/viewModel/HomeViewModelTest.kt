package com.example.tutorapp395project.viewModel

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel()
    }

    @Test
    fun changeViewState_updatesViewState() {
        val newState = "setting"

        homeViewModel.changeViewState(newState)

        assertEquals(newState, homeViewModel.viewState.value)
    }

    @Test
    fun changeViewState_updatesViewStateToSchedule() {
        val newState = "schedule"

        homeViewModel.changeViewState(newState)

        assertEquals(newState, homeViewModel.viewState.value)
    }

    @Test
    fun changeViewState_updatesViewStateToProfile() {
        val newState = "profile"

        homeViewModel.changeViewState(newState)

        assertEquals(newState, homeViewModel.viewState.value)
    }

    @Test
    fun changeViewState_updatesViewStateToSetting() {
        val newState = "setting"

        homeViewModel.changeViewState(newState)

        assertEquals(newState, homeViewModel.viewState.value)
    }
}