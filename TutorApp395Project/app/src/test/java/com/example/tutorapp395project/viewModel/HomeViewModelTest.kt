package com.example.tutorapp395project.viewModel

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class HomeViewModelTest {
    // Initialize the HomeViewModel for testing
    private lateinit var homeViewModel: HomeViewModel

    /*
     * Purpose: Set up the HomeViewModel for testing
     */
    @Before
    fun setUp() {
        homeViewModel = HomeViewModel()
    }

    /*
     * Purpose: Test that changeViewState updates the viewState
     */
    @Test
    fun changeViewState_updatesViewState() {
        val newState = "setting"

        homeViewModel.changeViewState(newState)

        assertEquals(newState, homeViewModel.viewState.value)
    }

    /*
     * Purpose: Test that changeViewState updates the viewState to schedule
     */
    @Test
    fun changeViewState_updatesViewStateToSchedule() {
        val newState = "schedule"

        homeViewModel.changeViewState(newState)

        assertEquals(newState, homeViewModel.viewState.value)
    }

    /*
     * Purpose: Test that changeViewState updates the viewState to profile
     */
    @Test
    fun changeViewState_updatesViewStateToProfile() {
        val newState = "profile"

        homeViewModel.changeViewState(newState)

        assertEquals(newState, homeViewModel.viewState.value)
    }

    /*
     * Purpose: Test that changeViewState updates the viewState to setting
     */
    @Test
    fun changeViewState_updatesViewStateToSetting() {
        val newState = "setting"

        homeViewModel.changeViewState(newState)

        assertEquals(newState, homeViewModel.viewState.value)
    }
}